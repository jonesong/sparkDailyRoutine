package app.project;

import java.util.List;

import app.common.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper=false)
@Value //All fields are private and final. (https://projectlombok.org/features/Value.html)
public class ProjectEntity extends AbstractEntity{

	int id;
	int user_id;
	String name;
	String note;
	String deleted = "1";
	
	/**
	 * 
	 * @param user_id
	 * @return list by user id
	 * @throws Exception 
	 */
	public static List<ProjectEntity> all(int user_id) throws Exception {
		String sql = "SELECT * FROM projects where deleted = 1 and user_id = :str1 ORDER BY name ASC";
		String[] values = { String.valueOf(user_id) };
		return queryListMultiParameter(sql, ProjectEntity.class, values);
	}
	
	public static ProjectEntity byProject(String id, String user_id) throws Exception {
		String sql = "SELECT * FROM projects where deleted = 1 "
				+ "and id = :str1 and user_id = :str2 ORDER BY name ASC";
		String[] values = { id, user_id };
		return (ProjectEntity) queryClassMultiParameter(sql, ProjectEntity.class, values);
	}
	
	/**
	 * 
	 * @param id
	 * @return userId in the table used in ProjectController for securing pages of project only by specific user
	 * @throws Exception 
	 */
	public static Integer getUserIdbyProjectId(String id) throws Exception {
		String sql = "SELECT user_id FROM projects where id = :str1"; //remove deleted = 1 bec refractoring ProjectController, function delete first before check ensureUserProject method
		String[] values = { id };
		return queryIntegerMultiParameter(sql, values);
	}
	
	public void save() {
		String sql = "INSERT INTO projects(user_id, name, note) "
				+ "VALUES (:user_id, :name, :note)";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	public void update() {
		String sql = "UPDATE projects SET name = :name, note =:note WHERE id = :id";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	//change the deleted flag into 0
	public void delete() {
		String sql = "UPDATE projects SET deleted = 0 WHERE id = :id";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	private ProjectEntity getInstanceUpdate(){
		ProjectEntity project = new ProjectEntity(getId(), getUser_id(), getName(), getNote());
		return project;
	}
	
}
