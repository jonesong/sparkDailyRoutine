package app.user;

import static app.util.RequestUtil.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.login.LoginController;
import app.module.ModuleController;
import app.module.ModuleEntity;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class UserController {

    // Authenticate the user
    public static boolean authenticate(String username, String password) throws Exception {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        List<UserEntity> list = UserEntity.userAndPass(username);
        if (list.size() == 0 || !list.get(0).getUsername().equals(username) || !list.get(0).getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    public static Route serveUserPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ModuleController.ensureUserModule(request, response, "user"); // module page is under "user" module
		Map<String, Object> model = new HashMap<>();
		model.put("users", UserEntity.all());
		request.session().removeAttribute("id");
		return ViewUtil.render(request, model, Path.Template.USERS_ALL);
	};
	
	public static Route handleNewUser = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ModuleController.ensureUserModuleBoolean(request, response, "user")) {
			Map<String, Object> model = new HashMap<>();
			model.put("duplicate", removeSessionAttrDuplicate(request));
			return ViewUtil.render(request, model, Path.Template.USER_NEW);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
    
	public static Route handleNewPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureNoDuplicate(request, response)) {
			UserEntity user = new UserEntity(0, getQueryUsername(request), getQueryPassword(request),
					getQueryFirstname(request), getQueryLastname(request));
			user.save();
			Map<String, Object> model = new HashMap<>();
			model.put("user", user);
			return ViewUtil.render(request, model, Path.Template.USER_SUCCESS);
		}
		request.session().attribute("duplicate", true);
		response.redirect(Path.Web.NEW_USER);
		return null;
	};
    
	public static Route editOneUser = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ModuleController.ensureUserModuleBoolean(request, response, "user")) {
			Map<String, Object> model = new HashMap<>();
			model.put("user", UserEntity.findId(getQueryParamsId(request)));
			model.put("modules", ModuleEntity.all(Integer.parseInt(getQueryParamsId(request))));
			request.session().attribute("id", getQueryParamsId(request));
			return ViewUtil.render(request, model, Path.Template.USER_ONE);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
	
	public static Route editUserPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		UserEntity user = new UserEntity(0, getQueryUsername(request),getQueryPassword(request),getQueryFirstname(request),getQueryLastname(request));
		user.update();
		Map<String, Object> model = new HashMap<>();
		model.put("user", user);
		model.put("updated", true);
		model.put("modules", ModuleEntity.all(Integer.parseInt(getSessionId(request))));
		return ViewUtil.render(request, model, Path.Template.USER_ONE);
	};
	
	public static Route deleteOneUser = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ModuleController.ensureUserModuleBoolean(request, response, "user")) {
			UserEntity.findId(getQueryParamsId(request)).delete();
			Map<String, Object> model = new HashMap<>();
			model.put("users", UserEntity.all());
			return ViewUtil.render(request, model, Path.Template.USERS_ALL);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
	
	private static boolean ensureNoDuplicate(Request request, Response response) throws Exception{
		if ( UserEntity.find(getQueryUsername(request)) == null) {
			return true;
		}
		return false;
	}
}
