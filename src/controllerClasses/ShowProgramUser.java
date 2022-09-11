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
import mainClasses.Basket;
import mainClasses.Program;
import mainClasses.User;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShowProgramUser {

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

    @FXML
    private TextField quantityTextArea;

    ObservableList<Program> prog = FXCollections.observableArrayList();



    @FXML
    void addToBasket(MouseEvent event) throws IOException, ClassNotFoundException {
        Program program = programTableView.getSelectionModel().getSelectedItem();
        if (program == null){
            JOptionPane.showMessageDialog(null, "не выбран продукт!");
        }
        else if (quantityTextArea.getText().equals("") || !quantityTextArea.getText().matches("[0-9]+")){
            JOptionPane.showMessageDialog(null, "Неверное количество!");
        }
        else if (Integer.parseInt(quantityTextArea.getText()) < program.getQuantity()){
            int quantity = Integer.parseInt(quantityTextArea.getText());
            Controller controller = new Controller();
            String login = controller.getLogName();
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_USERS_BY_LOGIN", login);
            oos.writeObject(request);
            Request request1 = (Request) ois.readObject();
            ArrayList<User> list = request1.getUsers();

            Request request4 = new Request("GET_BASKET_BY_ID", list.get(0).getId().intValue());
            oos.writeObject(request4);
            Request request5 = (Request) ois.readObject();
            ArrayList<Basket> baskets = request5.getBaskets();
            int c = 0;

            for (Basket basket: baskets){
                System.out.println(basket.getIsProgram());
                if (basket.getIdGood().equals(program.getId()) && basket.getIsProgram() == 0){
                    Request request2 = new Request("UPDATE_BASKET", basket.getIdGood().intValue(), basket.getQuantity() + quantity, basket.getIdUser().intValue(), 0);
                    oos.writeObject(request2);
                    c++;
                    JOptionPane.showMessageDialog(null, "Добавлено в корзину!");
                }
            }
            if (c==0){
                Basket basket = new Basket(null, list.get(0).getId(), program.getId(), 0, program.getGenre(), program.getName(), quantity, program.getPrice());
                Request request2 = new Request("ADD_TO_BASKET", basket);
                oos.writeObject(request2);
                JOptionPane.showMessageDialog(null, "Добавлено в корзину!");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Input quantity is more than exists!");
        }
    }
    @FXML
    void initialize() {
        try {
            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Request request = new Request("GET_PROGRAM_AVAILABLE", 1);
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
            loader.setLocation(getClass().getResource("/fxmlFiles/showProductsUser.fxml"));

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
