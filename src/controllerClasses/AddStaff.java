package controllerClasses;

import ServerAndSocket.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Staff;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddStaff {
    private ObservableList<String> positionChoices = FXCollections.observableArrayList("Директор", "Главный бухгалтер", "Курьер");
    String position = "";
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField surnameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button backBtn;

    @FXML
    private TextField phoneNumField;

    @FXML
    private Button registrationBtn;

    @FXML
    private TextField salaryField;

    @FXML
    private ChoiceBox<String> positionChoiceBox;

    @FXML
    void initialize() throws IOException {
        positionChoiceBox.setItems(positionChoices);
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        registrationBtn.setOnAction(actionEvent -> {
            int c = 0;
            if(positionChoiceBox.getValue() != null) {
                position = positionChoiceBox.getValue();
            }
            else {
                position = "";
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали должность!");
            }
            if (loginField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали логин!");
                c++;
            }
            else if (passwordField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали пароль!");
                c++;
            }
            else if (nameField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали имя!");
                c++;
            }
            else if (surnameField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали фамилию!");
                c++;
            }
            else if (phoneNumField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали номер телефона!");
                c++;
            }
            else if (salaryField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Вы не выбрали зарплату!");
                c++;
            }

            try {

                Request request = new Request("CHECK_LOGIN", loginField.getText());
                oos.writeObject(request);
                Request request1 = (Request) ois.readObject();
                boolean isExistUser = request1.isaBoolean();

                Request request2 = new Request("CHECK_LOGIN_STAFF", loginField.getText());
                oos.writeObject(request2);
                Request request3 = (Request) ois.readObject();
                boolean isExistStaff = request3.isaBoolean();
                System.out.println(isExistUser);
                if (isExistUser == true || isExistStaff == true){
                    JOptionPane.showMessageDialog(null, "Этот логин уже существует!");
                    c++;
                }
                else if (loginField.getText().equals("admin")){
                    JOptionPane.showMessageDialog(null, "Этот логин уже существует!");
                    c++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            if (c == 0){
                Request request2 = new Request("ADD_STAFF", new Staff(null, loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumField.getText(), position, Double.parseDouble(salaryField.getText())));
                try {
                    oos.writeObject(request2);
                    Request request3 = (Request)ois.readObject();
                    System.out.println(request3);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "УСПЕШНО!");
                registrationBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/adminPanel.fxml"));

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
        });

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/adminPanel.fxml"));

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
