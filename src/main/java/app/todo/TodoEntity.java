package app.todo;

import java.util.List;

import org.sql2o.Connection;
import app.common.DB;
import lombok.Data;

@Data // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class TodoEntity {

	private int id;
	private int task_id;
	private String note;
	private String date_started;
	private String time_total;
	private String time_start;
	private String time_end;
	private char deleted ='1';
	
	public TodoEntity(int taskId, String note, String dateStarted, String timeTotal, String timeStart, String timeEnd){
		this.task_id = taskId;
		this.note = note;
		this.date_started = dateStarted;
		this.time_total = timeTotal;
		this.time_start = timeStart;
		this.time_end = timeEnd;
	}
	
	public TodoEntity(String id, String note, String dateStarted, String timeTotal, String timeStart, String timeEnd){
		this.id = Integer.parseInt(id);
		this.note = note;
		this.date_started = dateStarted;
		this.time_total = timeTotal;
		this.time_start = timeStart;
		this.time_end = timeEnd;
	}
	
	public static List<TodoEntity> all(int task_id) {
		String sql = "SELECT id, task_id, note, date_started,"
				+ " time_total, time_start, time_end FROM todos where deleted='1' and task_id=:task_id order by date_started, time_start";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).addParameter("task_id", task_id).executeAndFetch(TodoEntity.class);
		}
	}
	
	public void save() {
		String sql = "INSERT INTO todos(task_id, note, date_started, time_total, time_start, time_end) "
				+ "VALUES (:task_id, :note, :date_started, :time_total, :time_start, :time_end)";
		try (Connection con = DB.sql2o.open()) {
			TodoEntity todo = new TodoEntity(getTask_id(),getNote(),getDate_started(), getTime_total(), getTime_start(), getTime_end());
			con.createQuery(sql).bind(todo).executeUpdate();
			todo = null;
		}
	}

}
