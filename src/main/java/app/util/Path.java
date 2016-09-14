package app.util;

import lombok.*;

public class Path {

    // The @Getter methods are needed in order to access
    // the variables from Velocity Templates
    public static class Web {
        @Getter public static final String INDEX = "/";
        @Getter public static final String LOGIN = "/login/";
        @Getter public static final String LOGOUT = "/logout/";
        @Getter public static final String PROJECTS = "/project/";
        @Getter public static final String NEW_PROJECT = "/project/new/";
        @Getter public static final String SUCCESS_PROJECT = "/project/success/";
        @Getter public static final String EDIT_PROJECT = "/project/edit/:id/";
        @Getter public static final String DELETE_PROJECT = "/project/del/:id/";
        @Getter public static final String ONE_PROJECT = "/project/:id/";
        @Getter public static final String TASKS = "/task/";
        @Getter public static final String NEW_TASK = "/task/new/";
        @Getter public static final String SUCCESS_TASK = "/task/success/";
        @Getter public static final String DELETE_TASK = "/task/del/:id/";
        @Getter public static final String ONE_TASK = "/task/:id/";
        
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
        
        public static final String NOT_FOUND = "/velocity/notFound.vm";
    }

}
