package controllerClasses;


import ServerAndSocket.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainClasses.Basket;
import mainClasses.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button showProductsBtn;

    @FXML
    private Button historyBtn;

    @FXML
    private Button showBasketsBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Text nameUserText;

    @FXML
    private Text basketCountText;
    @FXML
    private Button showOrdersBtn;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
        System.out.println(login);
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request = new Request("GET_USERS_BY_LOGIN", login);
        oos.writeObject(request);
        Request request1 = (Request) ois.readObject();
        ArrayList<User> list = request1.getUsers();
        nameUserText.setText(list.get(0).getFirstName() + " " + list.get(0).getLastName());
        System.out.println(list.get(0).getFirstName());

        Request request2 = new Request("GET_BASKET_BY_ID", list.get(0).getId().intValue());
        oos.writeObject(request2);
        Request request3 = (Request) ois.readObject();
        ArrayList<Basket> baskets = request3.getBaskets();

        basketCountText.setText(Integer.toString(baskets.size()));
        showProductsBtn.setOnAction(actionEvent -> {
            showProductsBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/showProductsUser.fxml"));

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

        showOrdersBtn.setOnAction(actionEvent -> {
            showOrdersBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/showOrders.fxml"));

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

        showBasketsBtn.setOnAction(actionEvent -> {
            showBasketsBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/showBasket.fxml"));

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

        historyBtn.setOnAction(actionEvent -> {
            historyBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/historyUser.fxml"));

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

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/MainPage.fxml"));

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
