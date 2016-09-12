package app.user;

import java.util.List;
import org.sql2o.*;

import app.common.DB;

public class UserDao {
	
	public static List<UserEntity> getAllUsers() {
		String sql = "SELECT * FROM users";
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql)
					.throwOnMappingFailure(false)
					.executeAndFetch(UserEntity.class);
		}
	}
	
	public static UserEntity find(int id) {
		try (Connection con = DB.sql2o.open()) {
			String sql = "SELECT * FROM users where id=:id";
			UserEntity user = con.createQuery(sql).addParameter("id", id)
					.throwOnMappingFailure(false)
					.executeAndFetchFirst(UserEntity.class);
			return user;
		}
	}
	
	public void save() {
		try (Connection con = DB.sql2o.open()) {
			UserEntity user = new UserEntity();
			String sql = "INSERT INTO users(username, password, first_name, last_name,"
					+ "department_name, level_name) "
					+ "VALUES (:username, :password, :first_name, :last_name, :"
					+ "department_name, :level_name)";
			con.createQuery(sql).bind(user).executeUpdate();
			user = null;
		}
	}
	
	public void update() {
		try (Connection con = DB.sql2o.open()) {
			UserEntity user = new UserEntity();
			String sql = "UPDATE users SET username = :username, password = :password,"
					+ "first_name =:first_name, last_name =:last_name,"
					+ "department_name =:department_name, level_name =:level_name WHERE id = :id";
			con.createQuery(sql).bind(user).executeUpdate();
			user = null;
		}
	}
}
