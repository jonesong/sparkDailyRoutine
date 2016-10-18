package app.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.*;
import org.sql2o.Connection;

public class AbstractEntity {

	protected void recordUpdate(String sql, Object object) {
		try (Connection con = DB.sql2o.open()) {
			con.createQuery(sql).bind(object).executeUpdate();
		}
	} 	
	
	protected static <T> List<T> queryListNoParameter(String sql, Class<T> klazz) {
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).executeAndFetch(klazz);
		}
	}
	
	protected static <T> List<T> queryListMultiParameter(String sql, Class<T> klazz, String[] values) throws Exception {
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).bind(instanceOfMyClass(values)).executeAndFetch(klazz);
		}
	}
	
	protected static List<String> queryListNoParameter(String sql, String[] values) throws Exception{
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).bind(instanceOfMyClass(values)).executeScalarList(String.class);
		}
	}
	
	protected static <T> Object queryClassMultiParameter(String sql, Class<T> klazz, String[] values) throws Exception {
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).bind(instanceOfMyClass(values)).executeAndFetchFirst(klazz);
		}
	}
	
	protected static Integer queryIntegerMultiParameter(String sql, String[] values) throws Exception {
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).bind(instanceOfMyClass(values)).executeAndFetchFirst(Integer.class);
		}
	}
	
	protected static String queryStringMultiParameter(String sql, String[] values) throws Exception {
		try (Connection con = DB.sql2o.open()) {
			return con.createQuery(sql).bind(instanceOfMyClass(values)).executeAndFetchFirst(String.class);
		}
	}
	
	private static Object instanceOfMyClass(String[] values) throws Exception {
		String className = "app.common.MyClasses";
		Class clazz = Class.forName(className);
		Class[] parameters = new Class[values.length];
		Object[] value = new Object[values.length];
		for (int x = 0; x < parameters.length; x++) {
			parameters[x] = String.class;
			value[x] = values[x];
		}
		Constructor constructor = clazz.getConstructor(parameters);
		Object xyz = constructor.newInstance(value);
		return xyz;
	}
	
	protected static <T> Object instanceOfMyClass(Object obj) throws Exception {
		Class<?> objClass = obj.getClass();
		String name, values = null;
	    Field[] fields = objClass.getDeclaredFields();
	    for(Field field : fields) {
	        name = field.getName();
	        field.setAccessible(true);
	        values = field.get(obj).toString();
	        System.out.println(name + ": " + values);
	    }
	    return objClass;
	}
	
	protected static Object instanceOfMethod(Object obj) throws Exception {
		Class<?> objClass = obj.getClass();
		Field[] fields = objClass.getDeclaredFields();
		Object[] values = new Object[fields.length];
		Class[] parameters = new Class[fields.length];
		for (int i = 0; i < fields.length; i++){
	    	fields[i].setAccessible(true);
	    	if (fields[i].getType().getName().equals("int")){
	    		parameters[i] = Integer.class;
	    	} else if (fields[i].getType().getName().equals("java.lang.String")){
	    		parameters[i] = String.class;
	    	}
	    	values[i] = fields[i].get(obj).toString();
	    }
//	    
	    String className = objClass.getName().toString();
		Class clazz = Class.forName(className);
//		Class[] parameters = new Class[values.length];
//		for (int x = 0; x < parameters.length; x++) {
//			parameters[x] = String.class;
//		}
		Constructor constructor = clazz.getConstructor(parameters);
		Object xyz = constructor.newInstance(values);
		return xyz;
	}
	
	public static void main(String[] args) throws Exception {
		Class<?> arrayClass = String[].class;
		System.out.println(arrayClass);
		Class<?> namedClass = Class.forName("[L" + String.class.getName() + ";");
		System.out.println(namedClass);
		System.out.println(arrayClass == namedClass);
		
		String className = "app.common.MyClasses";		
		Map<String, MyClasses> musics = new HashMap<>();
		Class clazz = Class.forName(className);
		Field[] fields = clazz.getDeclaredFields();
		Class[] parameters = new Class[2];
		for (int x = 0; x < parameters.length; x++) {
			parameters[x] = String.class;
		}
		for(Field field : fields) {
	        String name = field.getName();
	        Object value = field.get(clazz).toString();

	        System.out.println(name + ": ");
	    }
		Constructor constructor = clazz.getConstructor(parameters);
		Object[] values = new Object[2];
		for (int y = 0; y < values.length; y++) {
//			musics.put("str" + y + 1, new MyClasses("music" + y));
			values[y] = "one" + y;
		}
		Object o = constructor.newInstance(values);
		System.out.println(o);
	 }
}