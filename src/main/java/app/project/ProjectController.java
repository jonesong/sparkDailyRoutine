package app.project;

import java.util.HashMap;
import java.util.Map;

import app.login.LoginController;
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
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
            model.put("projects", ProjectEntity.all(getSessionCurrentId(request)));
            return ViewUtil.render(request, model, Path.Template.PROJECTS_ALL);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route handleNewPage = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		return ViewUtil.render(request, model, Path.Template.PROJECT_NEW);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route handleNewPost = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		ProjectEntity project = new ProjectEntity(getSessionCurrentId(request), getQueryName(request),getQueryNote(request));
    		project.save();
    		model.put("project", project);
    		return ViewUtil.render(request, model, Path.Template.PROJECT_SUCCESS);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route editOneProject = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		request.session().attribute("currentProjectId", getQueryParamsId(request));
    		model.put("project", ProjectEntity.byProject(getQueryParamsId(request), getSessionCurrentId(request).toString()));
    		model.put("tasks", TaskEntity.all(Integer.parseInt(getQueryParamsId(request))));
            return ViewUtil.render(request, model, Path.Template.PROJECT_ONE);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route editProjectPost = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		ProjectEntity project = new ProjectEntity(getQueryProjectId(request),getQueryName(request),getQueryNote(request));
    		project.update();
    		model.put("project", project);
    		model.put("tasks", TaskEntity.all(Integer.parseInt(getQueryProjectId(request))));
    		model.put("updated", true);
            return ViewUtil.render(request, model, Path.Template.PROJECT_ONE);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route fetchOneProject = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		request.session().attribute("currentProjectId", getQueryParamsId(request));
    		model.put("project", ProjectEntity.byProject(getQueryParamsId(request), getSessionCurrentId(request).toString()));
    		model.put("tasks", TaskEntity.all(Integer.parseInt(getQueryParamsId(request))));
            return ViewUtil.render(request, model, Path.Template.PROJECT_VIEW);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route deleteOneProject = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		ProjectEntity.byProject(getQueryParamsId(request), getSessionCurrentId(request).toString()).delete();
    		model.put("projects", ProjectEntity.all(getSessionCurrentId(request)));
            return ViewUtil.render(request, model, Path.Template.PROJECTS_ALL);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
}
