package controllerClasses;

import ServerAndSocket.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainClasses.Order;
import mainClasses.Staff;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowOrdersOfCourier {

    private static int idCourierr;

    public int getIdCourierr() {
        return idCourierr;
    }

    public void setIdCourierr(int idCourierr) {
        ShowOrdersOfCourier.idCourierr = idCourierr;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Text nameUserText;

    @FXML
    private Text basketCountText;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, Long> idColumn;

    @FXML
    private TableColumn<Order, Long> idUserColumn;

    @FXML
    private TableColumn<Order, Long> idCourierColumn;

    @FXML
    private TableColumn<Order, Integer> quantityColumn;

    @FXML
    private TableColumn<Order, Double> sumColumn;

    @FXML
    private TableColumn<Order, String> adressColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private Button deliveredBtn;

    @FXML
    void deliver(MouseEvent event) throws IOException {
        Order order = orderTable.getSelectionModel().getSelectedItem();
        if (order == null){
            JOptionPane.showMessageDialog(null, "Не выбран заказ!");
        }
        else if (order.getStatus().equals("В ожидании принятия заказа курьером")){
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("UPDATE_ORDER_STATUS", order.getId().intValue(), "В дороге");
            oos.writeObject(request);

            JOptionPane.showMessageDialog(null, "Успешно принял заказ!");

            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/courierPanel.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else if (order.getStatus().equals("В дороге")){
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("UPDATE_ORDER_STATUS", order.getId().intValue(), "Доставленный");
            oos.writeObject(request);
            JOptionPane.showMessageDialog(null, "Успешная доставка заказа!");
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/courierPanel.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else if (order.getStatus().equals("Доставленный")){
            JOptionPane.showMessageDialog(null, "Вы уже доставили этот заказ!");
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/courierPanel.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    ObservableList<Order> ordr = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        try {
            Controller controller = new Controller();
            String login = controller.getLogName();
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_STAFF_BY_LOGIN", login);
            oos.writeObject(request);
            Request request1 = (Request) ois.readObject();
            ArrayList<Staff> list = request1.getStaffs();
            setIdCourierr(list.get(0).getId().intValue());
            Request request2 = new Request("GET_ORDERS_BY_ID_COURIER", getIdCourierr());
            oos.writeObject(request2);
            Request request3 = (Request)ois.readObject();
            ArrayList<Order> orders = request3.getOrders();
            ordr.addAll(orders);
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            idUserColumn.setCellValueFactory(new PropertyValueFactory<>("idUser"));
            idCourierColumn.setCellValueFactory(new PropertyValueFactory<>("idCourier"));
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityItems"));
            sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));
            adressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

            orderTable.setItems(ordr);



            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/courierPanel.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
