package app.project;

import java.util.HashMap;
import java.util.Map;

import app.login.LoginController;
import app.module.ModuleController;
import app.task.TaskEntity;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import static app.util.RequestUtil.*;

public class ProjectController {

	public static Route serveProjectPage = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ModuleController.ensureUserModule(request, response, "project");
		request.session().removeAttribute("currentProjectId");
		return ViewUtil.render(request, runProjectAll(request), Path.Template.PROJECTS_ALL);
	};
    
	public static Route handleNewProject = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		Map<String, Object> model = new HashMap<>();
		return runCommonProject(request, response, model, Path.Template.PROJECT_NEW, true);
	};
    
	public static Route handleProjectPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ProjectEntity project = new ProjectEntity(0, getSessionCurrentId(request), getQueryName(request),
				getQueryNote(request));
		project.save();
		Map<String, Object> model = new HashMap<>();
		model.put("project", project);
		return ViewUtil.render(request, model, Path.Template.PROJECT_SUCCESS);
	};
    
	public static Route editOneProject = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		return runCommonProject(request, response, runProjectEdit(request), Path.Template.PROJECT_ONE, false);
	};
	
	public static Route editProjectPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ProjectEntity project = new ProjectEntity(Integer.parseInt(getQueryProjectId(request)), 0, //zero bec userId is not needed here
				getQueryName(request), getQueryNote(request));
		project.update();
		Map<String, Object> model = new HashMap<>();
		model.put("updated", true);
		model.put("project", project);
		model.put("tasks", TaskEntity.all(Integer.parseInt(getSessionCurrentProjectId(request))));
		return ViewUtil.render(request, model, Path.Template.PROJECT_ONE);
	};
    
	public static Route fetchOneProject = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		return runCommonProject(request, response, runProjectEdit(request), Path.Template.PROJECT_VIEW, false);
	};
    
	public static Route deleteOneProject = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		ProjectEntity.byProject(getQueryParamsId(request), getSessionCurrentId(request).toString()).delete();
		return runCommonProject(request, response, runProjectAll(request), Path.Template.PROJECTS_ALL, false);
	};

	private static Object runCommonProject(Request request, Response response, Map map, String path, boolean isNew) throws Exception {
		if (ModuleController.ensureUserModuleBoolean(request, response, "project")) {
			if (isNew) {
				return ViewUtil.render(request, map, path);
			} else if (ensureUserProject(request, response)) {
				return ViewUtil.render(request, map, path);
			}
		}
		return ViewUtil.notAcceptable.handle(request, response);
	}
	
	private static Map<String, Object> runProjectEdit(Request request) throws Exception {
		request.session().attribute("currentProjectId", getQueryParamsId(request));
		Map<String, Object> model = new HashMap<>();
		model.put("project",
				ProjectEntity.byProject(getQueryParamsId(request), getSessionCurrentId(request).toString()));
		model.put("tasks", TaskEntity.all(Integer.parseInt(getSessionCurrentProjectId(request))));
		return model;
	}
	
	private static Map<String, Object> runProjectAll(Request request) throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("projects", ProjectEntity.all(getSessionCurrentId(request)));
		return model;
	}
	
	private static boolean ensureUserProject(Request request, Response response) throws Exception {
		Integer tmpUserId = ProjectEntity.getUserIdbyProjectId(getQueryParamsId(request));
		if (tmpUserId == getSessionCurrentId(request)) {
			return true;
		}
		return false;
	}
	
}
