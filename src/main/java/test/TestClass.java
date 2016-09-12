package test;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

import app.index.IndexController;
import app.login.LoginController;
import app.project.ProjectController;
import app.task.TaskController;
import app.user.UserEntity;
import app.util.Filters;
import app.util.Path;
import app.util.ViewUtil;

public class TestClass {
	
	// Declare dependencies
    public static UserEntity user;
	
	public static void main(String[] args) {
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
        get(Path.Web.NEW_PROJECT,   	ProjectController.handleNewPage);
        post(Path.Web.SUCCESS_PROJECT,	ProjectController.handleNewPost);
        get(Path.Web.ONE_PROJECT,		ProjectController.fetchOneProject);
        post(Path.Web.PROJECTS,			ProjectController.handleProjectPost);
        get(Path.Web.NEW_TASK,   		TaskController.handleNewTask);
        post(Path.Web.SUCCESS_TASK,		TaskController.handleNewPost);
        get(Path.Web.ONE_TASK,			TaskController.fetchOneTask);
        post(Path.Web.TASKS,			TaskController.handleTaskPost);
        
        get("*",                     ViewUtil.notFound);

        //Set up after-filters (called after each get/post)
        after("*",                   Filters.addGzipHeader);
	}
}
