package mainClasses;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
@ToString


public abstract class Good implements Serializable {
    private Long id;
    private String genre;
    private double price;
    private int sold;
    private int quantity;
    private int isActive;





    @Override
    public abstract String toString();
}
