package mainClasses;

import java.io.Serializable;

public class HistoryPurchase implements Serializable {
    private Long id;
    private Long idUser;
    private Long idGood;
    private int isProgram;
    private String genre;
    private String model;
    private int quantity;
    private double price;
    private double sum;
    private String date;

    public HistoryPurchase(Long id, Long idUser, Long idGood, int isProgram, String genre, String model, int quantity, double price, double sum, String date) {
        this.id = id;
        this.idUser = idUser;
        this.idGood = idGood;
        this.isProgram = isProgram;
        this.genre = genre;
        this.model = model;
        this.quantity = quantity;
        this.price = price;
        this.sum = sum;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdGood() {
        return idGood;
    }

    public void setIdGood(Long idGood) {
        this.idGood = idGood;
    }

    public int getIsProgram() {
        return isProgram;
    }

    public void setIsProgram(int isProgram) {
        this.isProgram = isProgram;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "HistoryPurchase{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idGood=" + idGood +
                ", isProgram=" + isProgram +
                ", genre='" + genre + '\'' +
                ", model='" + model + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", sum=" + sum +
                ", date='" + date + '\'' +
                '}';
    }
}
