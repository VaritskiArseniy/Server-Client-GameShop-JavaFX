package mainClasses;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder( builderMethodName = "StaffBuilder")

public class Staff extends Person implements Serializable {
    private String phoneNum;
    private String position;
    private double salary;



    @Override
    public String toString() {
        return "Staff{" +
                "phoneNum='" + phoneNum + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                '}';
    }

    public Staff(Long id, String login, String password, String firstName, String lastName, String phoneNum, String position, double salary) {
        super(id, login, password, firstName, lastName);
        this.phoneNum = phoneNum;
        this.position = position;
        this.salary = salary;
    }
    public Staff(String login, String password) {

    }


}
