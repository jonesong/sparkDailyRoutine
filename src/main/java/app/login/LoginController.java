package app.login;

import app.module.ModuleEntity;
import app.user.*;
import app.util.*;
import spark.*;
import java.util.*;
import static app.util.RequestUtil.*;
//import static main.App.user;
//import static test.TestClass.user;

public class LoginController {

	public static Route serveLoginPage = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		model.put("loggedOut", removeSessionAttrLoggedOut(request));
		model.put("loginRedirect", removeSessionAttrLoginRedirect(request));
		return ViewUtil.render(request, model, Path.Template.LOGIN);
	};

	public static Route handleLoginPost = (Request request, Response response) -> {
		Map<String, Object> model = new HashMap<>();
		if (!UserController.authenticate(getQueryUsername(request), getQueryPassword(request))) {
			model.put("authenticationFailed", true);
			return ViewUtil.render(request, model, Path.Template.LOGIN);
		}
		model.put("authenticationSucceeded", true);
		request.session().attribute("currentUser", getQueryUsername(request));
		request.session().attribute("currentId", UserEntity.find(getQueryUsername(request)).getId());
		request.session().attribute("userPages", ModuleEntity.userPage(getSessionCurrentId(request)));
		if (getQueryLoginRedirect(request) != null) {
			response.redirect(getQueryLoginRedirect(request));
		}
		return ViewUtil.render(request, model, Path.Template.LOGIN);
	};

	public static Route handleLogoutPost = (Request request, Response response) -> {
		request.session().removeAttribute("currentUser");
		request.session().removeAttribute("id");
		request.session().removeAttribute("currentProjectId");
		request.session().removeAttribute("currentTaskId");
		request.session().attribute("loggedOut", true);
		response.redirect(Path.Web.LOGIN);
		return null;
	};

	// The origin of the request (request.pathInfo()) is saved in the session so
	// the user can be redirected back after login
	public static void ensureUserIsLoggedIn(Request request, Response response) {
		if (request.session().attribute("currentUser") == null) {
			request.session().attribute("loginRedirect", request.pathInfo());
			response.redirect(Path.Web.LOGIN);
		}
	};

}
