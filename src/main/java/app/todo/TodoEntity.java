package app.todo;

import java.util.List;

import app.common.AbstractEntity;
import lombok.Value;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Value // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class TodoEntity extends AbstractEntity {

	int id;
	int task_id;
	String note;
	String date_started;
	String time_total;
	String time_start;
	String time_end;
	String deleted = "1";
	
	public static List<TodoEntity> all(int task_id) throws Exception {
		String sql = "SELECT id, task_id, note, date_started,"
				+ " time_total, time_start, time_end FROM todos where deleted = 1 and task_id =:str1 order by date_started, time_total";
		String[] values = { String.valueOf(task_id) };
		return queryListMultiParameter(sql, TodoEntity.class, values);
	}
	
	public void save() {
		String sql = "INSERT INTO todos(task_id, note, date_started, time_total, time_start, time_end) "
				+ "VALUES (:task_id, :note, :date_started, :time_total, :time_start, :time_end)";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	private TodoEntity getInstanceUpdate() {
		TodoEntity todo = new TodoEntity(getId(), getTask_id(), getNote(), getDate_started(), getTime_total(), getTime_start(), getTime_end());
		return todo;
	}

}
