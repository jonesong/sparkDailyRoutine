package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

import app.index.IndexController;
import app.login.LoginController;
import app.project.ProjectController;
import app.task.TaskController;
import app.todo.TodoController;
import app.user.UserEntity;
import app.util.Filters;
import app.util.Path;
import app.util.ViewUtil;
import spark.servlet.SparkApplication;

public class App implements SparkApplication{
	
	// Declare dependencies
    public static UserEntity user;
    public void init() {
//	public static void main(String[] args) {
		// Instantiate your dependencies
        user = new UserEntity();
		
		// Configure Spark
        port(6789);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();

        // Set up before-filters (called before each get/post)
        before("*",                  Filters.addTrailingSlashes);
        
        // Set up routes
        get(Path.Web.INDEX,          	IndexController.serveIndexPage);
        get(Path.Web.LOGIN,          	LoginController.serveLoginPage);
        post(Path.Web.LOGIN,         	LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,        	LoginController.handleLogoutPost);
        get(Path.Web.PROJECTS,		 	ProjectController.serveProjectPage);
        get(Path.Web.NEW_PROJECT,   	ProjectController.handleNewPost);
        post(Path.Web.SUCCESS_PROJECT,	ProjectController.handleNewProject);
        get(Path.Web.DELETE_PROJECT,	ProjectController.deleteOneProject);
        get(Path.Web.EDIT_PROJECT,		ProjectController.editOneProject);
        get(Path.Web.ONE_PROJECT,		ProjectController.fetchOneProject);
        post(Path.Web.PROJECTS,			ProjectController.editProjectPost);
        get(Path.Web.NEW_TASK,   		TaskController.handleNewTask);
        post(Path.Web.SUCCESS_TASK,		TaskController.handleNewPost);
        get(Path.Web.EDIT_TASK,			TaskController.editOneTask);
        get(Path.Web.DELETE_TASK,		TaskController.deleteOneTask);
        post(Path.Web.TASKS,			TaskController.handleTaskPost);
        get(Path.Web.VIEW_TASK,			TodoController.fetchOneTodo);
        post(Path.Web.VIEW_TASK,		TodoController.handleTodoPost);
        
        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);
	}
}
