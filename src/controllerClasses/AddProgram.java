package controllerClasses;

import ServerAndSocket.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mainClasses.Program;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class AddProgram {
    private ObservableList<String> genreChoices = FXCollections.observableArrayList("Текстовый редактор", "Графический редактор", "Электронная таблица", "Базы данных", "Утилиты", "Телекоммуникационная программа", "Специализированные программы");
    String genre = "";
    @FXML
    private void handleWindowsBox(){
        if(windowsCheckBox.isSelected()){
            macOSCheckBox.setSelected(false);
        }
    }

    @FXML
    private void handleMacOSBox(){
        if(macOSCheckBox.isSelected()){
            windowsCheckBox.setSelected(false);
        }
    }

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
    private Button addProgramBtn;

    @FXML
    private CheckBox windowsCheckBox;

    @FXML
    private CheckBox macOSCheckBox;

    @FXML
    void initialize() {
        genreChoiceBox.setItems(genreChoices);
        addProgramBtn.setOnAction(actionEvent -> {
            int c = 0;
            String OS = "MacOS";
            if(genreChoiceBox.getValue() != null) {
                genre = genreChoiceBox.getValue();
            }
            else {
                genre = "";
                c++;
                JOptionPane.showMessageDialog(null, "Вы не выбрали жанр!");
            }
            if (nameField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не вошли в название!");
            }
            else if (priceField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не вошли в цену!");
            }
            else if (quantityField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не вошли в количество!");
            }
            else if (descriptionField.getText().equals("")){
                c++;
                JOptionPane.showMessageDialog(null, "Вы не вошли в описание!");
            }
            if (c == 0){
                if(windowsCheckBox.isSelected()){
                    OS = "Windows";
                }

                try {
                    Socket socket = new Socket("localhost", 11112);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Request request = new Request("ADD_PROGRAM", new Program(null, genre, Double.parseDouble(priceField.getText()), 0, Integer.parseInt(quantityField.getText()), 1, nameField.getText(), descriptionField.getText(), OS));
                    oos.writeObject(request);
                    Request request1 = (Request)ois.readObject();
                    System.out.println(request1);
                    JOptionPane.showMessageDialog(null, "УСПЕШНО!");

                    addProgramBtn.getScene().getWindow().hide();
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
