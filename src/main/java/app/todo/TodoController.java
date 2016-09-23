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
		Map<String, Object> model = new HashMap<>();
		TodoEntity todo = new TodoEntity(Integer.parseInt(getSessionCurrentTaskId(request)), getQueryNote(request),
				getQueryDateStarted(request), getQueryTimeTotal(request), getQueryTimeStart(request),
				getQueryTimeEnd(request));
		todo.save();
		model.put("task", TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)));
		model.put("todos", TodoEntity.all(Integer.parseInt(getSessionCurrentTaskId(request))));
		return ViewUtil.render(request, model, Path.Template.TASK_VIEW);
	};

	public static Route fetchOneTodo = (Request request, Response response) -> {
		LoginController.ensureUserIsLoggedIn(request, response);
		if (TaskController.ensureProjectTask(request, response)) {
			Map<String, Object> model = new HashMap<>();
			request.session().attribute("currentTaskId", getQueryParamsId(request));
			model.put("task", TaskEntity.byTask(getQueryParamsId(request), getSessionCurrentProjectId(request)));
			model.put("todos", TodoEntity.all(Integer.parseInt(getSessionCurrentTaskId(request))));
			return ViewUtil.render(request, model, Path.Template.TASK_VIEW);
		}
		return ViewUtil.notAcceptable.handle(request, response);
	};

}
