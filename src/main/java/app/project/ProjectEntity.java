package app.project;

import java.util.List;

import org.sql2o.Connection;
import app.common.DB;
import lombok.Data;

@Data // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class ProjectEntity {

	private int id;
	private int user_id;
	private String name;
	private String note;
	private char deleted ='1';
	
	public ProjectEntity(int userId, String name, String note){
		this.user_id = userId;
		this.name = name;
		this.note = note;
	}
	
	public ProjectEntity(String id, String name, String note){
		this.id = Integer.parseInt(id);
		this.name = name;
		this.note = note;
	}
	
	/**
	 * 
	 * @param user_id
	 * @return list by user id
	 */
	public static List<ProjectEntity> all(int user_id) {
		String sql = "SELECT id, user_id, name, note FROM projects where deleted='1' and user_id=:user_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).addParameter("user_id", user_id).executeAndFetch(ProjectEntity.class);
		}
	}
	
	public static ProjectEntity byProject(String id, String user_id) {
		String sql = "SELECT id, user_id, name, note FROM projects where deleted='1' "
				+ "and id=:id and user_id=:user_id ORDER BY name ASC";
		try (Connection con = DB.sql2o.open()) {
			ProjectEntity project = con.createQuery(sql).addParameter("id", id).addParameter("user_id", user_id).executeAndFetchFirst(ProjectEntity.class);
//			System.out.println(id + user_id);
			return project;
		}
	}
	
	public void save() {
		String sql = "INSERT INTO projects(user_id, name, note) "
				+ "VALUES (:user_id, :name, :note)";
		try (Connection con = DB.sql2o.open()) {
			ProjectEntity project = new ProjectEntity(getUser_id(),getName(),getNote());
			con.createQuery(sql).bind(project).executeUpdate();
			project = null;
		}
	}
	
	public void update() {
		String sql = "UPDATE projects SET name = :name, note =:note WHERE id = :id";
		try (Connection con = DB.sql2o.open()) {
			con.createQuery(sql).addParameter("name", this.name).addParameter("note", this.note).addParameter("id", this.id).executeUpdate();
//			System.out.println(id + name + note);
		}
	}
	
	//change the deleted flag into 0
	public void delete() {
		String sql = "UPDATE projects SET deleted = 0 WHERE id = :id";
		try (Connection con = DB.sql2o.open()) {
			con.createQuery(sql).addParameter("id", this.id).executeUpdate();
		}
	}
	
}
