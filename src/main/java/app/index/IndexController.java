package app.index;

import app.login.LoginController;
import app.user.UserEntity;
import app.util.*;
import spark.*;

import static app.util.RequestUtil.clientAcceptsHtml;

import java.util.*;

public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
//    	LoginController.ensureUserIsLoggedIn(request, response);
//    	if (clientAcceptsHtml(request)) {
    		Map<String, Object> model = new HashMap<>();
            model.put("users", UserEntity.all());
            return ViewUtil.render(request, model, Path.Template.INDEX);
//    	}
//    	return ViewUtil.notAcceptable.handle(request, response);
    };
}
