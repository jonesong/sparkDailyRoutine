package app.util;

import lombok.*;

public class Path {
	
	private static String webDevPath = "";

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
    	@Getter public static final String NOT_ACCEPTABLE = "/error/";
        @Getter public static final String INDEX = "/";
        @Getter public static final String LOGIN = webDevPath + "/login/";
        @Getter public static final String LOGOUT = webDevPath + "/logout/";
        @Getter public static final String PROJECTS = webDevPath + "/project/";
        @Getter public static final String NEW_PROJECT = webDevPath + "/project/new/";
        @Getter public static final String SUCCESS_PROJECT = webDevPath + "/project/success/";
        @Getter public static final String EDIT_PROJECT = webDevPath + "/project/edit/:id/";
        @Getter public static final String DELETE_PROJECT = webDevPath + "/project/del/:id/";
        @Getter public static final String ONE_PROJECT = webDevPath + "/project/:id/";
        @Getter public static final String TASKS = webDevPath + "/task/";
        @Getter public static final String NEW_TASK = webDevPath + "/task/new/";
        @Getter public static final String SUCCESS_TASK = webDevPath + "/task/success/";
        @Getter public static final String DELETE_TASK = webDevPath + "/task/del/:id/";
        @Getter public static final String EDIT_TASK = webDevPath + "/task/edit/:id/";
        @Getter public static final String VIEW_TASK = webDevPath + "/task/:id/";
        @Getter public static final String TODOS = webDevPath + "/todo/";
        @Getter public static final String USERS = webDevPath + "/user/";
        @Getter public static final String NEW_USER = webDevPath + "/user/new/";
        @Getter public static final String SUCCESS_USER = webDevPath + "/user/success/";
        @Getter public static final String EDIT_USER = webDevPath + "/user/edit/:id/";
        @Getter public static final String DELETE_USER = webDevPath + "/user/del/:id/";
        @Getter public static final String MODULES = webDevPath + "/module/";
        @Getter public static final String NEW_MODULE = webDevPath + "/module/new/";
        @Getter public static final String SUCCESS_MODULE = webDevPath + "/module/success/";
        @Getter public static final String EDIT_MODULE = webDevPath + "/module/edit/:id/";
        @Getter public static final String DELETE_MODULE = webDevPath + "/module/del/:id/";
        
        
    }

    public static class Template {
        public final static String INDEX = "/index/index.vtl";
        public final static String LOGIN = "/login/login.vm";
        public final static String PROJECTS_ALL = "/project/all.vm";
        public final static String PROJECT_NEW = "/project/new.vm";
        public final static String PROJECT_SUCCESS = "/project/success.vm";
        public static final String PROJECT_ONE = "/project/one.vm";
        public static final String PROJECT_VIEW = "/project/view.vm";
        public final static String TASK_NEW = "/task/new.vm";
        public final static String TASK_SUCCESS = "/task/success.vm";
        public final static String TASK_ONE = "/task/one.vm";
        public final static String TASK_VIEW = "/task/view.vm";
        public final static String USERS_ALL = "/user/all.vm";
        public final static String USER_NEW = "/user/new.vm";
        public final static String USER_SUCCESS = "/user/success.vm";
        public static final String USER_ONE = "/user/one.vm";
        public final static String MODULES_ALL = "/module/all.vm";
        public final static String MODULE_NEW = "/module/new.vm";
        public final static String MODULE_SUCCESS = "/module/success.vm";
        
        public static final String NOT_FOUND = "/velocity/notFound.vm";
        public static final String NOT_ACCEPTABLE = "/velocity/notAcceptable.vm";
    }

}
