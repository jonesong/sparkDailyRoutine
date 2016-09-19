package app.util;

import spark.*;

public class RequestUtil {

	public static String getSessionLocale(Request request) {
        return request.session().attribute("locale");
    }
	
	public static Integer getSessionCurrentId(Request request) {
        return request.session().attribute("currentId");
    }
	
	public static String getSessionCurrentProjectId(Request request) {
        return request.session().attribute("currentProjectId");
    }
	
	public static String getSessionCurrentTaskId(Request request) {
        return request.session().attribute("currentTaskId");
    }
	
	public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }
	
	public static String getSessionName(Request request) {
        return request.session().attribute("name");
    }
	
	public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }
	
	public static String removeSessionAttrLoginRedirect(Request request) {
        String loginRedirect = request.session().attribute("loginRedirect");
        request.session().removeAttribute("loginRedirect");
        return loginRedirect;
    }
	
	/**
	 * 
	 * @param request
	 * @return the value in the url e.g. /foo/:id
	 */
	public static String getQueryParamsId(Request request) {
        return request.params("id");
    }
    
	// request.queryParams gets the value in the page of declaring the variable name = :name
	public static String getQueryLocale(Request request) {
        return request.queryParams("locale");
    }
	
	public static String getQueryUsername(Request request) {
        return request.queryParams("username");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }
    
    public static String getQueryLoginRedirect(Request request) {
        return request.queryParams("loginRedirect");
    }
    
    public static String getQueryProjectId(Request request){
    	return request.queryParams("projectId");
    }
    
    public static String getQueryTaskId(Request request){
    	return request.queryParams("taskId");
    }
    
    public static String getQueryName(Request request){
    	return request.queryParams("name");
    }
    
    public static String getQueryNote(Request request){
    	return request.queryParams("note");
    }
    
    public static String getQueryDueDate(Request request){
    	return request.queryParams("due_date");
    }
    
    public static String getQueryDone(Request request){
    	return request.queryParams("done");
    }
    
    public static String getQueryDateStarted(Request request){
    	return request.queryParams("date_started");
    }
    
    public static String getQueryTimeTotal(Request request){
    	return request.queryParams("time_total");
    }
    
    public static String getQueryTimeStart(Request request){
    	return request.queryParams("time_start");
    }
    
    public static String getQueryTimeEnd(Request request){
    	return request.queryParams("time_end");
    }
    
    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }
}
