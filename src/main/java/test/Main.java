package test;

import static spark.Spark.get;

import java.util.HashMap;
import java.util.Map;

import spark.ModelAndView;
import spark.servlet.SparkApplication;
import spark.template.velocity.VelocityTemplateEngine;

public class Main implements SparkApplication{
	public void init() {
//	public static void main(String[] args) {
		String layout = "templates/layout.vtl";

		get("/", (request, response) -> {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("template", "test/home.vtl");
			return new ModelAndView(model, layout);
		}, new VelocityTemplateEngine());

		get("/detector", (request, response) -> {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("template", "test/detector.vtl");

			String year = request.queryParams("year");
			Integer integerYear = Integer.parseInt(year);
			Boolean isLeapYear = isLeapYear(integerYear);

			model.put("isLeapYear", isLeapYear);
			model.put("year", request.queryParams("year"));
			return new ModelAndView(model, layout);
		}, new VelocityTemplateEngine());
	}

	public static Boolean isLeapYear(Integer year) {
		if (year % 400 == 0) {
			return true;
		} else if (year % 100 == 0) {
			return false;
		} else {
			return year % 4 == 0;
		}
	}
}