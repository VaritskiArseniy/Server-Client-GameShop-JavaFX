package controllerClasses;

import ServerAndSocket.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpPage {

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
    void initialize() throws IOException {
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        registrationBtn.setOnAction(actionEvent -> {
            int c = 0;
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
                JOptionPane.showMessageDialog(null, "Вы не выбрали телефонный номер!");
                c++;
            }

            try {
                Request request = new Request("CHECK_LOGIN", loginField.getText());
                oos.writeObject(request);
                Request request1 = (Request) ois.readObject();
                boolean isExist = request1.isaBoolean();
                if (isExist){
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
                Request request = new Request("ADD_USER", new User(null, loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), phoneNumField.getText(), 0, ""));
                try {
                    oos.writeObject(request);
                    Request request1 = (Request)ois.readObject();
                    System.out.println(request1);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "УСПЕШНО!");
                registrationBtn.getScene().getWindow().hide();
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
            }
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
