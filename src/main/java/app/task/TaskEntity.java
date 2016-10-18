package app.task;

import java.util.List;

import app.common.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class TaskEntity extends AbstractEntity {

	int id;
	int project_id;
	String name;
	String due_date;
	String done;
	String note;
	String deleted = "1";

	public TaskEntity(int id, int projectId, String name, String note, String due_date, String done) {
		this.id = id;
		this.project_id = projectId;
		this.name = name;
		this.note = note;
		this.due_date = due_date;
		this.done = correctingDone(done);
	}

	/**
	 * 
	 * @param done
	 * @return value to the table. If String done from request (checkbox), is
	 *         checked then value is on
	 */
	private String correctingDone(String done) {
		if (done == null || done.equals("1")) {
			return "1";
		}
		return "0";
	}

	public static List<TaskEntity> all(int project_id) throws Exception {
		String sql = "SELECT id, project_id, name, due_date, done, note FROM tasks where deleted = 1 and project_id = :str1 ORDER BY name ASC";
		String[] values = { String.valueOf(project_id) };
		return queryListMultiParameter(sql, TaskEntity.class, values);
	}

	public static TaskEntity byTask(String id, String project_id) throws Exception {
		String sql = "SELECT id, project_id, name, due_date, done, note FROM tasks where deleted = 1 "
				+ "and id= :str1 and project_id = :str2 ORDER BY name ASC";
		String[] values = { id, project_id };
		return (TaskEntity) queryClassMultiParameter(sql, TaskEntity.class, values);
	}

	public static String getProjectIdbyTaskId(String id) throws Exception {
		String sql = "SELECT project_id FROM tasks where deleted = 1 and id = :str1";
		String[] values = { id };
		return queryStringMultiParameter(sql, values);
	}

	public void save() {
		String sql = "INSERT INTO tasks(project_id, name, note, due_date) "
				+ "VALUES (:project_id, :name, :note, :due_date)";
		recordUpdate(sql, getInstanceUpdate());
	}

	public void update() {
		String sql = "UPDATE tasks SET name = :name, note =:note, due_date =:due_date, done =:done WHERE id = :id";
		recordUpdate(sql, getInstanceUpdate());
	}

	// change the deleted flag into 0
	public void delete() {
		String sql = "UPDATE tasks SET deleted = 0 WHERE id = :id";
		recordUpdate(sql, getInstanceUpdate());
	}

	private TaskEntity getInstanceUpdate() {
		TaskEntity task = new TaskEntity(getId(), getProject_id(), getName(), getNote(), getDue_date(), getDone());
		return task;
	}
}