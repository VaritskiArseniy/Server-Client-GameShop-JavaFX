package mainClasses;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder( builderMethodName = "UserBuilder")

public class User extends Person implements Serializable {
    private String phoneNum;
    private double balance;
    private String adress;

    public User(Long id, String login, String password, String firstName, String lastName, String phoneNum, double balance, String adress) {
        super(id, login, password, firstName, lastName);
        this.phoneNum = phoneNum;
        this.balance = balance;
        this.adress = adress;
    }

    public User(String login, String password) {

    }


    @Override
    public String toString() {
        return "ID: " + getId() + "\n" +
                "First name: " + getFirstName() + "\n" +
                "Last name: " + getLastName() + "\n" +
                "Tellephone number: " + getPhoneNum() + "\n" +
                "Balance: " + balance  + "\n" +
                "Adress " + getAdress();
    }
}
