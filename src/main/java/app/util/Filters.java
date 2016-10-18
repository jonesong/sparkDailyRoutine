package app.util;

import spark.*;

public class Filters {

	private static String webDevPath = "/spark-0.0.1-SNAPSHOT/";

    // If a user manually manipulates paths and forgets to add
    // a trailing slash, redirect the user to the correct path
    public static Filter addTrailingSlashes = (Request request, Response response) -> {
        if (!request.pathInfo().endsWith("/")) {
            response.redirect(request.pathInfo() + "/");
        }
    };
    
    public static Filter addWebDevPath = (Request request, Response response) -> {
    	response.redirect(webDevPath + request.pathInfo());
    };

    // Enable GZIP for all responses
    public static Filter addGzipHeader = (Request request, Response response) -> {
        response.header("Content-Encoding", "gzip");
    };

}
