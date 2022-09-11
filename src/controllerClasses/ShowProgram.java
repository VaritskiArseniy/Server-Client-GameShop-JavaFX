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
import javafx.stage.Stage;
import mainClasses.Program;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowProgram {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Program> programTableView;

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
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        OSColumn.setCellValueFactory(new PropertyValueFactory<>("OS"));
        programTableView.setItems(prog);


        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/showProducts.fxml"));

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
