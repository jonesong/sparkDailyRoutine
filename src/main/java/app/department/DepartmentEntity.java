package app.department;

import java.util.List;

import app.common.AbstractEntity;
import lombok.EqualsAndHashCode;
import lombok.Value;

@EqualsAndHashCode(callSuper=false)
@Value //All fields are private and final. (https://projectlombok.org/features/Value.html)
public class DepartmentEntity extends AbstractEntity{

	int id;
	String name;
	String deleted = "1";
	
	public static List<DepartmentEntity> deparment() throws Exception {
		String sql = "SELECT name FROM departments where deleted = 1 ORDER BY name ASC";
		return queryListNoParameter(sql, DepartmentEntity.class);
	}
}
