package app.todo;

import static app.util.RequestUtil.*;

import java.util.HashMap;
import java.util.Map;

import app.login.LoginController;
import app.task.TaskController;
import app.task.TaskEntity;
import app.todo.TodoEntity;
import app.util.Path;
import app.util.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

public class TodoController {

	public static Route handleTodoPost = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		TodoEntity todo = new TodoEntity(0, Integer.parseInt(getSessionCurrentTaskId(request)), getQueryNote(request),
				getQueryDateStarted(request), getQueryTimeTotal(request), getQueryTimeStart(request),
				getQueryTimeEnd(request));
		todo.save();
		return ViewUtil.render(request, runCommonModels(request), Path.Template.TASK_VIEW);
	};

	public static Route fetchOneTodo = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (TaskController.ensureProjectTask(request, response)) {
			request.session().attribute("currentTaskId", getQueryParamsId(request));
			return ViewUtil.render(request, runCommonModels(request), Path.Template.TASK_VIEW);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};
	
	private static Map<String, Object> runCommonModels(Request request) throws Exception {
		Map<String, Object> model = new HashMap<>();
		model.put("task", TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)));
		model.put("todos", TodoEntity.all(Integer.parseInt(getSessionCurrentTaskId(request))));
		model.put("currentProjectId", getSessionCurrentProjectId(request));
		return model;
	}

}
