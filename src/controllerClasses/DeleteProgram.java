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

public class DeleteProgram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Program> programTable;

    @FXML
    private TableColumn<Program, Long> idColumn;

    @FXML
    private TableColumn<Program, String> nameColumn;

    @FXML
    private TableColumn<Program, String> genreColumn;

    @FXML
    private TableColumn<Program, Double> priceColumn;

    @FXML
    private TableColumn<Program, String> OSColumn;

    @FXML
    private TableColumn<Program, Long> soldColumn;

    @FXML
    private TableColumn<Program, Long> quantityColumn;

    @FXML
    private TableColumn<Program, Long> isActiveColumn;

    @FXML
    private TableColumn<Program, String> descriptionColumn;

    @FXML
    void displaySelected(MouseEvent event) throws IOException {
        Program program = programTable.getSelectionModel().getSelectedItem();
        if (program == null){
            JOptionPane.showMessageDialog(null, "Не выбран продукт!");
        }
        else {
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("DELETE_PROGRAM", program.getName());
            oos.writeObject(request);
            JOptionPane.showMessageDialog(null, "Удален " + program.getName() + "!");

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
        }
    }
    ObservableList<Program> prog = FXCollections.observableArrayList();
    public ObservableList<Program> getProg(){
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
        return prog;
    }

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        soldColumn.setCellValueFactory(new PropertyValueFactory<>("sold"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        isActiveColumn.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        OSColumn.setCellValueFactory(new PropertyValueFactory<>("OS"));
        programTable.setItems(getProg());

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
