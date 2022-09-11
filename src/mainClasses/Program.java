package mainClasses;

import lombok.*;

import java.io.Serializable;


@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor

@Builder( builderMethodName = "ProgramBuilder")

public class Program extends Good implements Serializable {
    private String name;
    private String description;
    private String OS;

    public Program(Long id, String genre, double price, int sold, int quantity, int isActive, String name, String description, String OS) {
        super(id, genre, price, sold, quantity, isActive);
        this.name = name;
        this.description = description;
        this.OS = OS;
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
