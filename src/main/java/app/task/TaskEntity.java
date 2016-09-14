package app.task;

import java.util.List;

import org.sql2o.Connection;
import app.common.DB;
import lombok.Data;

@Data // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class TaskEntity {

	private int id;
	private int project_id;
	private String name;
	private String due_date;
	private String done;
	private String note;
	private char deleted ='1';
	
	public TaskEntity(int projectId, String name, String note, String due_date){
		this.project_id = projectId;
		this.name = name;
		this.note = note;
		this.due_date = due_date;
	}
	
	public TaskEntity(String id, String name, String note, String due_date, String done){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.note = note;
		this.due_date = due_date;
		this.done = correctingDone(done);
	}
	
	/**
	 * 
	 * @param done
	 * @return value to the table. If String done from request (checkbox), is checked then value is on 
	 */
	private String correctingDone(String done){
		if(done == null){
			return "1";
		}
		return "0";
	}
	
	public static List<TaskEntity> all(int project_id) {
		String sql = "SELECT id, project_id, name, due_date, done, note FROM tasks where deleted='1' and project_id=:project_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).addParameter("project_id", project_id).executeAndFetch(TaskEntity.class);
		}
	}
	
	public static TaskEntity byTask(String id, String project_id) {
		String sql = "SELECT id, project_id, name, due_date, done, note FROM tasks where deleted='1' "
				+ "and id=:id and project_id=:project_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			TaskEntity task = con.createQuery(sql).addParameter("id", id).addParameter("project_id", project_id).executeAndFetchFirst(TaskEntity.class);
			return task;
		}
	}
	
	public void save() {
		String sql = "INSERT INTO tasks(project_id, name, note, due_date) "
				+ "VALUES (:project_id, :name, :note, :due_date)";
		try (Connection con = DB.sql2o.open()) {
			TaskEntity task = new TaskEntity(getProject_id(),getName(),getNote(), getDue_date());
			con.createQuery(sql).bind(task).executeUpdate();
			task = null;
		}
	}
	
	public void update() {
		String sql = "UPDATE tasks SET name = :name, note =:note, due_date =:due_date, done =:done WHERE id = :id";
		try (Connection con = DB.sql2o.open()) {
			con.createQuery(sql).addParameter("name", this.name).addParameter("note", this.note)
			.addParameter("due_date", this.due_date).addParameter("done", this.done).addParameter("id", this.id).executeUpdate();
		}
	}
	
	//change the deleted flag into 0
		public void delete() {
			String sql = "UPDATE tasks SET deleted = 0 WHERE id = :id";
			System.out.println(id);
			try (Connection con = DB.sql2o.open()) {
				con.createQuery(sql).addParameter("id", this.id).executeUpdate();
			}
		}

}