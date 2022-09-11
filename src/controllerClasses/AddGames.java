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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Games;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGames {

    private ObservableList<String> genreChoices = FXCollections.observableArrayList("Ужасы", "Приключение", "Квест", "Стратегии", "Шутеры", "ММО РПГ", "Вестерн", "Рогалик", "РПГ", "Симулятор", "Слэшер");
    String genre = "";

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private ChoiceBox<String> genreChoiceBox;

    @FXML
    private Button backBtn;

    @FXML
    private Button addGamesBtn;

    @FXML
    void initialize() {
        genreChoiceBox.setItems(genreChoices);

        addGamesBtn.setOnAction(actionEvent -> {
            int c = 0;
            if(genreChoiceBox.getValue() != null) {
                genre = genreChoiceBox.getValue();
            }
            else {
                genre = "";
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали категорию!");
            }
            if (nameField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали в название!");
            }
            else if (priceField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали в цену!");
            }
            else if (quantityField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали в количество!");
            }
            else if (descriptionField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "вы не выбрали в описание!");
            }

            if (c==0){
                try {
                    Socket socket = new Socket("localhost", 11112);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Request request = new Request("ADD_GAMES", new Games(null, genre, Double.parseDouble(priceField.getText()), 0, Integer.parseInt(quantityField.getText()), 1, nameField.getText(), descriptionField.getText()));
                    oos.writeObject(request);
                    Request request1 = (Request)ois.readObject();
                    System.out.println(request1);
                    JOptionPane.showMessageDialog(null, "УСПЕШНО!");

                    addGamesBtn.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/fxmlFiles/addProduct.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });

        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/addProduct.fxml"));

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
