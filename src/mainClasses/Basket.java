package mainClasses;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Builder( builderMethodName = "BasketBuilder")

public class Basket implements Serializable {
    private Long id;
    private Long idUser;
    private Long idGood;
    private int isProgram;
    private String genre;
    private String model;
    private int quantity;
    private double price;



    @Override
    public String toString() {
        return isProgram + " " + idGood + " " + idUser;
//                "Basket{" +
//                "id=" + id +
//                ", idUser=" + idUser +
//                ", idGood=" + idGood +

//                ", category='" + category + '\'' +
//                ", model='" + model + '\'' +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                '}';
    }
}
