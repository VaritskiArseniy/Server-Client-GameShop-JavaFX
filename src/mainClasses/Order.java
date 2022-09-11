package mainClasses;

import java.io.Serializable;



public class Order implements Serializable {
    private Long id;
    private Long idUser;
    private int idCourier;
    private int quantityItems;
    private double sum;
    private String address;
    private String status;

    public Order(Long id, Long idUser, int idCourier, int quantityItems, double sum, String address, String status) {
        this.id = id;
        this.idUser = idUser;
        this.idCourier = idCourier;
        this.quantityItems = quantityItems;
        this.sum = sum;
        this.address = address;
        this.status = status;
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

    public int getIdCourier() {
        return idCourier;
    }

    public void setIdCourier(int idCourier) {
        this.idCourier = idCourier;
    }

    public int getQuantityItems() {
        return quantityItems;
    }

    public void setQuantityItems(int quantityItems) {
        this.quantityItems = quantityItems;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", idUser=" + idUser +
                ", idCourier=" + idCourier +
                ", quantityItems=" + quantityItems +
                ", sum=" + sum +
                ", address='" + address + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
