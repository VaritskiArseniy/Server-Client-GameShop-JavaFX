package mainClasses;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString


public abstract class Person implements Serializable {
    private Long id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;




    @Override
    public abstract String toString();
}
