package app.user;

import java.util.List;

import app.common.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper=false)
@Value //All fields are private and final. (https://projectlombok.org/features/Value.html)
public class UserEntity extends AbstractEntity{

	int id;
	String username;
	String password;
	String first_name;
	String last_name;
	String status = "1";
	String deleted = "1";
	
	/**
	 * 
	 * @return list of all users
	 */
	public static List<UserEntity> all() {
		String sql = "SELECT * FROM users where deleted = 1 ORDER BY first_name ASC";
		return queryListNoParameter(sql, UserEntity.class);
	}
	
	/**
	 * 
	 * @param username
	 * @return username, password used for checking login authenticity
	 * @throws Exception 
	 */
	public static List<UserEntity> userAndPass(String username) throws Exception {
		String sql = "SELECT username, password FROM users where username = :str1 and deleted = 1";
		String[] values = { username };
		return queryListMultiParameter(sql, UserEntity.class, values);
	}
	
	/**
	 * 
	 * @param username
	 * @return id by username used in the LoginController
	 * @throws Exception 
	 */
	public static UserEntity find(String username) throws Exception {
		String sql = "SELECT * FROM users where username = :str1 and deleted = 1";
		String[] values = { username };
		return (UserEntity) queryClassMultiParameter(sql, UserEntity.class, values);
	}
	
	/**
	 * 
	 * @param id
	 * @return used for edit & delete user
	 * @throws Exception 
	 */
	public static UserEntity findId(String id) throws Exception {
		String sql = "SELECT * FROM users where deleted = 1 and id = :str1";
		String[] values = { id };
		return (UserEntity) queryClassMultiParameter(sql, UserEntity.class, values);
	}
	
	public void save() {
		String sql = "INSERT INTO users(username, password, first_name, last_name) "
				+ "VALUES (:username, :password, :first_name, :last_name)";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	public void update() {
		String sql = "UPDATE users SET password = :password, first_name = :first_name, last_name = :last_name WHERE username = :username";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	//change the deleted flag into 0
	public void delete() {
		String sql = "UPDATE users SET deleted = 0 WHERE id = :id";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	private UserEntity getInstanceUpdate() {
		UserEntity user = new UserEntity(getId(), getUsername(), getPassword(), getFirst_name(), getLast_name());
		return user;
	}
	
}
