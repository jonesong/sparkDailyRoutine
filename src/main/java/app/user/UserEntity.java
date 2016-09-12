package app.user;

import java.util.List;

import org.sql2o.Connection;

import app.common.DB;
import lombok.Data;

@Data // All fields are private and final. Getters and setters are generated (https://projectlombok.org/features/Value.html)
public class UserEntity{

	private int id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String department_name;
	private String level_name;
	private char status ='1';
	private char deleted ='1';
	
	/**
	 * with deleted flag equals to 1 to indicate not deleted
	 * @return list of all users
	 */
	public static List<UserEntity> all() {
		String sql = "SELECT id, username, password FROM users where deleted='1' ORDER BY first_name ASC";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(UserEntity.class);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @return username, password used for checking login authenticity
	 */
	public List<UserEntity> find(String username) {
		String sql = "SELECT username, password FROM users where username=:username and deleted='1'";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).addParameter("username", username).executeAndFetch(UserEntity.class);
		}
	}
	
	/**
	 * 
	 * @param username
	 * @return id by username
	 */
	public UserEntity findId(String username) {
		try (Connection con = DB.sql2o.open()) {
			String sql = "SELECT id FROM users where username=:username and deleted='1'";
			UserEntity user = con.createQuery(sql).addParameter("username", username).executeAndFetchFirst(UserEntity.class);
			return user;
		}
	}
	
}
