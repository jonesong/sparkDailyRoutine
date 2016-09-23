package app.user;

import java.util.List;
//import static main.App.user;
import static test.TestClass.user;

public class UserController {

    // Authenticate the user
    public static boolean authenticate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }
        List<UserEntity> list = user.find(username);
        if (list.size() == 0 || !list.get(0).getUsername().equals(username) || !list.get(0).getPassword().equals(password)) {
            return false;
        }
        return true;
    }

}
