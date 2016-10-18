package app.util;

import java.io.IOException;
import spark.servlet.SparkFilter;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ApplicationFilter extends SparkFilter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String requestUrl = ((HttpServletRequest) request).getRequestURI().toString();

		Map<String, String> mimeMapping = new HashMap<>();
		mimeMapping.put("/", "text/html");
		mimeMapping.put(".css", "text/css");
		mimeMapping.put(".js", "text/javascript");
		mimeMapping.put(".html", "text/html");
		mimeMapping.put(".htm", "text/html");

		for (Map.Entry<String, String> entry : mimeMapping.entrySet()) {
			if (requestUrl.endsWith(entry.getKey())) {
				((HttpServletResponse) response).setHeader("Content-Type", entry.getValue());
			}
		}
		super.doFilter(request, response, chain);
	}
}