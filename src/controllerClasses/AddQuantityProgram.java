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
import mainClasses.Program;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddQuantityProgram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField quantityField;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Program> ProgramTableView;

    @FXML
    private TableColumn<Program, Long> idColumn;

    @FXML
    private TableColumn<Program, String> genreColumn;

    @FXML
    private TableColumn<Program, Double> priceColumn;

    @FXML
    private TableColumn<Program, Integer> soldColumn;

    @FXML
    private TableColumn<Program, Integer> quantityColumn;

    @FXML
    private TableColumn<Program, String> nameColumn;

    @FXML
    private TableColumn<Program, String> OSColumn;

    @FXML
    private TableColumn<Program, String> descriptionColumn;
    ObservableList<Program> prog = FXCollections.observableArrayList();

    @FXML
    void addQuantity(MouseEvent event) throws IOException, ClassNotFoundException {
        Program program = ProgramTableView.getSelectionModel().getSelectedItem();
        if (program == null){
            JOptionPane.showMessageDialog(null, "Не выбрана программа!");
        }
        else if (quantityField.getText().equals("") || !quantityField.getText().matches("[0-9]+")){
            JOptionPane.showMessageDialog(null, "Неправильное количество!");
        }
        else {
            int quantity = Integer.parseInt(quantityField.getText());
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_PROGRAM_BY_ID", program.getId().intValue());
            oos.writeObject(request);
            Request request1 = (Request) ois.readObject();
            ArrayList<Program> programs = request1.getPrograms();

            int new_quantity = programs.get(0).getQuantity() + quantity;
            Request request2 = new Request("ADD_QUANTITY_PROGRAM", program.getId().intValue(), new_quantity);
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
            Request request = new Request("GET_PROGRAM");
            oos.writeObject(request);
            Request request1 = (Request)ois.readObject();
            ArrayList<Program> list = request1.getPrograms();
            prog.addAll(list);

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        OSColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ProgramTableView.setItems(prog);


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
