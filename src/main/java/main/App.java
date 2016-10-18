package main;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

import app.index.IndexController;
import app.login.LoginController;
import app.module.ModuleController;
import app.project.ProjectController;
import app.task.TaskController;
import app.todo.TodoController;
import app.user.UserController;
import app.user.UserEntity;
import app.util.Filters;
import app.util.Path;
import app.util.ViewUtil;
import spark.servlet.SparkApplication;

public class App implements SparkApplication{
	
	private static String webDevPath = "/spark-0.0.1-SNAPSHOT/";
	// Declare dependencies
//    public static UserEntity user;
    public void init() {
//	public static void main(String[] args) {
		// Instantiate your dependencies
//        user = new UserEntity();
		
		// Configure Spark
//        port(6789);
        staticFiles.location("/public");
        staticFiles.expireTime(600L);
        enableDebugScreen();
        contextPath(webDevPath);
        // Set up before-filters (called before each get/post)
        before("*",                  Filters.addTrailingSlashes);
//        before("*",                  Filters.addWebDevPath);
        
        // Set up routes
        get(Path.Web.INDEX,          	IndexController.serveIndexPage);
        get(Path.Web.LOGIN,          	LoginController.serveLoginPage);
        post(Path.Web.LOGIN,         	LoginController.handleLoginPost);
        post(Path.Web.LOGOUT,        	LoginController.handleLogoutPost);
        get(Path.Web.PROJECTS,		 	ProjectController.serveProjectPage);
        get(Path.Web.NEW_PROJECT,   	ProjectController.handleNewProject);
        post(Path.Web.SUCCESS_PROJECT,	ProjectController.handleProjectPost);
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
        get(Path.Web.USERS,		 		UserController.serveUserPage);
        get(Path.Web.NEW_USER,   		UserController.handleNewUser);
        post(Path.Web.SUCCESS_USER,		UserController.handleNewPost);
        get(Path.Web.EDIT_USER,			UserController.editOneUser);
        post(Path.Web.USERS,			UserController.editUserPost);
        get(Path.Web.DELETE_USER,		UserController.deleteOneUser);
        get(Path.Web.MODULES,		 	ModuleController.serveModulePage);
        get(Path.Web.NEW_MODULE,   		ModuleController.handleNewModule);
        post(Path.Web.SUCCESS_MODULE,	ModuleController.handleModulePost);
        get(Path.Web.DELETE_MODULE,		ModuleController.deleteOneModule);
        
        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);
	}
}
