package app.task;

import java.util.HashMap;
import java.util.Map;

import app.login.LoginController;
import app.module.ModuleController;
import app.project.ProjectEntity;
import app.task.TaskEntity;
import app.todo.TodoEntity;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;
import static app.util.RequestUtil.*;

public class TaskController {

	public static Route handleNewTask = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ModuleController.ensureUserModuleBoolean(request, response, "project")) { // task page is under "project" module
			Map<String, Object> model = new HashMap<>();
			model.put("currentProjectId", getSessionCurrentProjectId(request));
			return ViewUtil.render(request, model, Path.Template.TASK_NEW);
		} 
		return ViewUtil.notAcceptable.handle(request, response);
	};

	public static Route handleNewPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ModuleController.ensureUserModuleBoolean(request, response, "project")) {
			TaskEntity task = new TaskEntity(0, Integer.parseInt(getSessionCurrentProjectId(request)),
					getQueryName(request), getQueryNote(request), getQueryDueDate(request), "");
			task.save();
			Map<String, Object> model = new HashMap<>();
			model.put("currentProjectId", getSessionCurrentProjectId(request));
			model.put("task", task);
			return ViewUtil.render(request, model, Path.Template.TASK_SUCCESS);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};

	public static Route editOneTask = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureProjectTask(request, response)) {
			Map<String, Object> model = new HashMap<>();
			model.put("currentProjectId", getSessionCurrentProjectId(request));
			model.put("task", TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)));
			model.put("todos", TodoEntity.all(Integer.parseInt(getQueryParamsId(request))));
			return ViewUtil.render(request, model, Path.Template.TASK_ONE);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};

	public static Route handleTaskPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		TaskEntity task = new TaskEntity(Integer.parseInt(getQueryTaskId(request)), Integer.parseInt(getSessionCurrentProjectId(request)), 
				getQueryName(request), getQueryNote(request), getQueryDueDate(request), getQueryDone(request));
		task.update();
		Map<String, Object> model = new HashMap<>();
		model.put("currentProjectId", getSessionCurrentProjectId(request));
		model.put("task", task);
		model.put("updated", true);
		model.put("todos", TodoEntity.all(Integer.parseInt(getQueryTaskId(request))));
		return ViewUtil.render(request, model, Path.Template.TASK_ONE);
	};

	public static Route deleteOneTask = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (ensureProjectTask(request, response)) {
			TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)).delete();
			Map<String, Object> model = new HashMap<>();
			model.put("currentProjectId", getSessionCurrentProjectId(request));
			model.put("project", ProjectEntity.byProject(getSessionCurrentProjectId(request),
					getSessionCurrentId(request).toString()));
			model.put("tasks", TaskEntity.all(Integer.parseInt(getSessionCurrentProjectId(request))));
			return ViewUtil.render(request, model, Path.Template.PROJECT_VIEW);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};

	public static boolean ensureProjectTask(Request request, Response response) throws Exception {
		String tmpProjectId = TaskEntity.getProjectIdbyTaskId(getQueryParamsId(request));
		if (getSessionCurrentProjectId(request) == null){
			//preventing error pages when user manually input /edit , /success , etc
			return false;
		}
		if (tmpProjectId.equals(getSessionCurrentProjectId(request))) {
			return true;
		}
		return false;
	}
}
