package app.module;

import java.util.List;
import app.common.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper=false)
@Value //All fields are private and final. (https://projectlombok.org/features/Value.html)
public class ModuleEntity extends AbstractEntity{

	int id;
	int user_id;
	String name;
	String deleted = "1";
	
	public static List<ModuleEntity> all(int user_id) throws Exception {
		String sql = "SELECT id, user_id, name FROM modules where deleted = 1 and user_id = :str1 ORDER BY name ASC";
		String[] values = { String.valueOf(user_id) };
		return queryListMultiParameter(sql, ModuleEntity.class, values);
	}
	
	public static Integer getUserIdbyModuleId(int id) throws Exception {
		String sql = "SELECT user_id FROM modules where deleted = 1 and id = :str1";
		String[] values = { String.valueOf(id) };
		return queryIntegerMultiParameter(sql, values);
	}
	
	public static List allNames(int user_id) throws Exception {
		String sql = "SELECT name FROM modules where deleted = 1 and user_id = :str1 ORDER BY name ASC";
		String[] values = { String.valueOf(user_id) };
		return queryListNoParameter(sql, values);
	}
	
	public static String[] userPage(Object user_id) throws Exception{
		String[] blank = {"0"};
		if (user_id != null) {
			List db = allNames((int) user_id);
			String[] listA = new String[db.size()];
			for (int x = 0; x < db.size(); x++) {
				listA[x] = db.get(x).toString().toLowerCase();
			}
			return listA;
		}
		return blank;
	}
	
	public static ModuleEntity findId(String id) throws Exception {
		String sql = "SELECT * FROM modules where deleted = 1 and id = :str1";
		String[] values = { id };
		return (ModuleEntity) queryClassMultiParameter(sql, ModuleEntity.class, values);
	}
	
	public void save() {
		String sql = "INSERT INTO modules(user_id, name) "
				+ "VALUES (:user_id, :name)";
		recordUpdate(sql, getInstanceUpdate());
	}
	
	//change the deleted flag into 0
		public void delete() {
			String sql = "UPDATE modules SET deleted = 0 WHERE id = :id";
			recordUpdate(sql, getInstanceUpdate());
		}
	
	private ModuleEntity getInstanceUpdate(){
		ModuleEntity module = new ModuleEntity(getId(), getUser_id(), getName());
		return module;
	}
}
