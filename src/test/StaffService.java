package test;
import mainClasses.Staff;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;


public class StaffService {

    private List<Staff> staff;

    public StaffService(List<Staff> staff) {
        this.staff = staff;
    }

    public List<Staff> getStaff() {
        return staff;
    }

    public List<Staff> createNewStaff(String login, String password ) throws Exception {
        validateStaff(login, password);
        Staff staffs = new Staff(login, password);
        staff.add(staffs);
        return staff;
    }

    public void removeStaff(String login) {
        staff = staff.stream().filter(it -> login.equals(login)).collect(Collectors.toList());
    }

    public boolean isPassword(Staff staffs, String password) throws CustomFieldException {
        if (isNull(staffs) || isNull(staffs.getPassword())) {
            throw new CustomFieldException("login or password is null");
        }
        if (isNull(password)) {
            throw new CustomFieldException("Compare password must not be null");
        }
        return password == staffs.getPassword() && password.equals(staffs.getPassword()) ;
    }

    private void validateStaff(String login, String password) throws Exception {
        if (isNull(login) || login.isBlank()) {
            throw new CustomFieldException("Login could not be empty or null");
        }
        if (isNull(password)) {
            throw new CustomFieldException("Password not be null");
        }
    }
}


class CustomFieldException2 extends Exception {
    private String message;

    public CustomFieldException2(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}