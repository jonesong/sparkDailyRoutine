package app.task;

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

public class TaskController {
	
	public static Route handleNewTask = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		return ViewUtil.render(request, model, Path.Template.TASK_NEW);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
    public static Route handleNewPost = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		TaskEntity task = new TaskEntity(Integer.parseInt(getSessionCurrentProjectId(request)), getQueryName(request));
    		task.save();
    		model.put("task", task);
    		return ViewUtil.render(request, model, Path.Template.TASK_SUCCESS);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
	public static Route fetchOneTask = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		model.put("task", TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)));
            return ViewUtil.render(request, model, Path.Template.TASK_ONE);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };

    public static Route handleTaskPost = (Request request, Response response) -> {
    	LoginController.ensureUserIsLoggedIn(request, response);
    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
    		TaskEntity task = new TaskEntity(getQueryTaskId(request),getQueryName(request),getQueryNote(request));
    		task.update();
    		model.put("task", task);
    		model.put("updated", true);
            return ViewUtil.render(request, model, Path.Template.TASK_ONE);
    	}
    	return ViewUtil.notAcceptable.handle(request, response);
    };
    
}
