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
import mainClasses.Staff;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class DeleteStaff {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Staff> staffsTable;

    @FXML
    private TableColumn<Staff, Long> idColumn;

    @FXML
    private TableColumn<Staff, String> loginColumn;

    @FXML
    private TableColumn<Staff, String> passwordColumn;

    @FXML
    private TableColumn<Staff, String> nameColumn;

    @FXML
    private TableColumn<Staff, String> surnameColumn;

    @FXML
    private TableColumn<Staff, String> phoneColumn;

    @FXML
    private TableColumn<Staff, String> positionColumn;

    @FXML
    private TableColumn<Staff, Double> salaryColumn;

    @FXML
    void displaySelected(MouseEvent event) throws IOException {
        Staff staff = staffsTable.getSelectionModel().getSelectedItem();
        if (staff == null){
            JOptionPane.showMessageDialog(null, "Не выбран сотрудник!");
        }
        else {
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("DELETE_STAFF", staff.getLogin());
            oos.writeObject(request);
            JOptionPane.showMessageDialog(null, "Удален " + staff.getLogin() + "!");

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
    ObservableList<Staff> stff = FXCollections.observableArrayList();
    public ObservableList<Staff> getStaffs(){
        try {
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_STAFF");
            oos.writeObject(request);
            Request request1 = (Request)ois.readObject();
            ArrayList<Staff> list = request1.getStaffs();
            stff.addAll(list);

            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return stff;
    }

    @FXML
    void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<>("position"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        staffsTable.setItems(getStaffs());

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
