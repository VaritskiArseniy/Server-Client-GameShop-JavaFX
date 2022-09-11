package controllerClasses;

import ServerAndSocket.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainClasses.Order;
import mainClasses.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InformationInput {

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
    private TextField adressTextField;

    @FXML
    private Button addProgramBtn;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request = new Request("GET_USERS_BY_LOGIN", login);
        oos.writeObject(request);
        Request request1 = (Request) ois.readObject();
        ArrayList<User> list = request1.getUsers();
        ShowBasket showBasket = new ShowBasket();
        int quantity = showBasket.getQuantity();
        double sum = showBasket.getSum();
        addProgramBtn.setOnAction(actionEvent -> {
            if (!adressTextField.getText().equals("")){
                try {
                    Request request2 = new Request("SET_ADDRESS", list.get(0).getId().intValue(), adressTextField.getText());
                    oos.writeObject(request2);

                    Request request3 = new Request("SET_ORDER", new Order(null, list.get(0).getId(), 0, quantity, sum, adressTextField.getText(), "В ожидании принятия курьером"));
                    oos.writeObject(request3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                addProgramBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/userPanel.fxml"));

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
            else {
                JOptionPane.showMessageDialog(null, "Неверный адресс");
            }
        });


    }
}
