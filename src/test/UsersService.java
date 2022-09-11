package test;
import mainClasses.User;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


public class UsersService {

    private List<User> user;

    public UsersService(List<User> user) {
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }

    public List<User> createNewUser(String login, String password ) throws Exception {
        validateUser(login, password);
        User users = new User(login, password);
        user.add(users);
        return user;
    }

    public void removeUser(String login) {
        user = user.stream().filter(it -> login.equals(login)).collect(Collectors.toList());
    }

    public boolean isPassword(User users, String password) throws CustomFieldException {
        if (isNull(users) || isNull(users.getPassword())) {
            throw new CustomFieldException("login or password is null");
        }
        if (isNull(password)) {
            throw new CustomFieldException("Compare password must not be null");
        }
        return password == users.getPassword() && password.equals(users.getPassword()) ;
    }

    private void validateUser(String login, String password) throws Exception {
        if (isNull(login) || login.isBlank()) {
            throw new CustomFieldException("Login could not be empty or null");
        }
        if (isNull(password)) {
            throw new CustomFieldException("Password not be null");
        }
    }
}



class CustomFieldException extends Exception {
    private String message;

    public CustomFieldException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}