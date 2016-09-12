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
	private String note;
	private char deleted ='1';
	
	public TaskEntity(int projectId, String name){
		this.project_id = projectId;
		this.name = name;
	}
	
	public TaskEntity(String id, String name, String note){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.note = note;
	}
	
	public static List<TaskEntity> all(int project_id) {
		String sql = "SELECT id, project_id, name, note FROM tasks where deleted='1' and project_id=:project_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).addParameter("project_id", project_id).executeAndFetch(TaskEntity.class);
		}
	}
	
	public static TaskEntity byTask(String id, String project_id) {
		String sql = "SELECT id, project_id, name, note FROM tasks where deleted='1' "
				+ "and id=:id and project_id=:project_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			TaskEntity task = con.createQuery(sql).addParameter("id", id).addParameter("project_id", project_id).executeAndFetchFirst(TaskEntity.class);
			return task;
		}
	}
	
	public void save() {
		String sql = "INSERT INTO tasks(project_id, name) "
				+ "VALUES (:project_id, :name)";
		try (Connection con = DB.sql2o.open()) {
			TaskEntity task = new TaskEntity(getProject_id(),getName());
			con.createQuery(sql).bind(task).executeUpdate();
			task = null;
		}
	}
	
	public void update() {
		String sql = "UPDATE tasks SET name = :name, note =:note WHERE id = :id";
		try (Connection con = DB.sql2o.open()) {
			con.createQuery(sql).addParameter("name", this.name).addParameter("note", this.note).addParameter("id", this.id).executeUpdate();
		}
	}

}