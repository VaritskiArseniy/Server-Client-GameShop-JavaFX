package mainClasses;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Builder( builderMethodName = "GameBuilder")

public class Games extends Good implements Serializable {
    private String name;
    private String description;

    public Games(Long id, String genre, double price, int sold, int quantity, int isActive, String name, String description) {
        super(id, genre, price, sold, quantity, isActive);
        this.name = name;
        this.description = description;
    }






    @Override
    public String toString() {
        return "ID: " + getId() + "\n" +
                "Genre: " + getGenre() + "\n" +
                "Price: " + getPrice() + "\n" +
                "Sold: " + getSold() + "\n" +
                "Quantity: " + getQuantity() + "\n" +
                "Name: " + name + "\n" +
                "Description: " + description;
    }
}
