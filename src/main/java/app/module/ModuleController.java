package app.module;

import static app.util.RequestUtil.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.department.DepartmentEntity;
import app.login.LoginController;
import app.user.UserEntity;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class ModuleController {

	public static Route serveModulePage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ensureUserModule(request, response, "user"); // module page is under "user" module
		Map<String, Object> model = new HashMap<>();
		model.put("modules", ModuleEntity.all(getSessionCurrentId(request)));
		return ViewUtil.render(request, model, Path.Template.MODULES_ALL);
	};
	
	public static Route handleNewModule = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureUserModuleBoolean(request, response, "user") && getSessionId(request) != null) {
			Map<String, Object> model = new HashMap<>();
			model.put("duplicate", removeSessionAttrDuplicate(request));
			model.put("departments", DepartmentEntity.deparment());
			model.put("user", UserEntity.findId(getSessionId(request)));
			return ViewUtil.render(request, model, Path.Template.MODULE_NEW);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
    
	public static Route handleModulePost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureNoDuplicate(request, response) && !getQueryName(request).equals("")){
			ModuleEntity module = new ModuleEntity(0, Integer.parseInt(getQueryId(request)), getQueryName(request));
			module.save();
			Map<String, Object> model = new HashMap<>();
			model.put("module", module);
			model.put("user", UserEntity.findId(getSessionId(request)));
			return ViewUtil.render(request, model, Path.Template.MODULE_SUCCESS);
		};
		request.session().attribute("duplicate", true);
		response.redirect(Path.Web.NEW_MODULE);
		return null;
	};
	
	public static Route deleteOneModule = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureUserModuleBoolean(request, response, "user") && getSessionId(request) != null) {
			ModuleEntity.findId(getQueryParamsId(request)).delete();
			Map<String, Object> model = new HashMap<>();
			model.put("modules", ModuleEntity.all(Integer.parseInt(getSessionId(request))));
			model.put("user", UserEntity.findId(getSessionId(request)));
			return ViewUtil.render(request, model, Path.Template.USER_ONE);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
	
	public static void ensureUserModule(Request request, Response response, String module) {
		String[] userPages = getSessionUserPages(request);
		int count = 0;
		count = getCount(userPages, module);
		if (count == 0){
			response.redirect(Path.Web.NOT_ACCEPTABLE);
		}
	}
	
	public static boolean ensureUserModuleBoolean(Request request, Response response, String module) {
		String[] userPages = getSessionUserPages(request);
		int count = 0;
		count = getCount(userPages, module);
		if (count == 0){
			return false;
		}
		return true;
	}

	private static int getCount(String[] userPages, String module){
		int count = 0;
		for (int i = 0; i < userPages.length; i++) {
			if (userPages[i].equals(module)){
				count++;
			}
		}
		return count;
	}
	
	private static boolean ensureNoDuplicate(Request request, Response response) throws Exception{
		String tmpName = getQueryName(request);
		List databaseNames = ModuleEntity.allNames(Integer.parseInt(getQueryId(request))); //getSessionCurrentId(request) used this if user log in
		for (int i = 0; i < databaseNames.size(); i++) {
			if (databaseNames.get(i).equals(tmpName)){
				return false;
			}
		}
		return true;
	}
	
}
