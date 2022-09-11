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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainClasses.Games;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddQuantityGames {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TextField quantityField;

    @FXML
    private TableView<Games> gamesTable;

    @FXML
    private TableColumn<Games, Long> idColumn;

    @FXML
    private TableColumn<Games, String> genreColumn;

    @FXML
    private TableColumn<Games, Double> priceColumn;

    @FXML
    private TableColumn<Games, Integer> soldColumn;

    @FXML
    private TableColumn<Games, Integer> quantityColumn;

    @FXML
    private TableColumn<Games, String> nameColumn;

    @FXML
    private TableColumn<Games, String> descriptionColumn;
    ObservableList<Games> gams = FXCollections.observableArrayList();


    @FXML
    void addQuantity(MouseEvent event) throws IOException, ClassNotFoundException {
        Games games = gamesTable.getSelectionModel().getSelectedItem();
        if (games == null){
            JOptionPane.showMessageDialog(null, "Нет выбранной косметики!");
        }
        else if (quantityField.getText().equals("") || !quantityField.getText().matches("[0-9]+")){
            JOptionPane.showMessageDialog(null, "Неправильное количество!");
        }
        else {
            int quantity = Integer.parseInt(quantityField.getText());
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_GAMES_BY_ID", games.getId().intValue());
            oos.writeObject(request);
            Request request1 = (Request) ois.readObject();
            ArrayList<Games> games1 = request1.getGames();

            int new_quantity = games1.get(0).getQuantity() + quantity;
            Request request2 = new Request("ADD_QUANTITY_GAMES", games.getId().intValue(), new_quantity);
            oos.writeObject(request2);
            JOptionPane.showMessageDialog(null, "Успешная операция!");
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/addQuantity.fxml"));

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

    @FXML
    void initialize() {
        try {
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_GAMES");
            oos.writeObject(request);
            Request request1 = (Request)ois.readObject();
            ArrayList<Games> list = request1.getGames();
            for (Games games : list){
                gams.add(games);
            }

            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        gamesTable.setItems(gams);


        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/addQuantity.fxml"));

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
