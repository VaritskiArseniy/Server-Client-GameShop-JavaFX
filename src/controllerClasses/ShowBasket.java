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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainClasses.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class ShowBasket {

    private static int quantity;
    private static double sum;

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        ShowBasket.sum = sum;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        ShowBasket.quantity = quantity;
    }

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private TableView<Basket> gamesTable;

    @FXML
    private TableColumn<Basket, Long> idColumn;

    @FXML
    private TableColumn<Basket, String> genreColumn;

    @FXML
    private TableColumn<Basket, Double> priceColumn;

    @FXML
    private TableColumn<Basket, Integer> quantityColumn;

    @FXML
    private TableColumn<Basket, String> modelColumn;


    @FXML
    private TableColumn<Basket, ?> asd;

    @FXML
    private TableColumn<Basket, Integer> isprogramColumn;

    @FXML
    private TableView<?> programTableView;

    @FXML
    private TableColumn<?, ?> idProgramColumn;

    @FXML
    private TableColumn<?, ?> genreProgramColumn;

    @FXML
    private TableColumn<?, ?> priceProgramColumn;

    @FXML
    private TableColumn<?, ?> quantityProgramColumn;

    @FXML
    private TableColumn<?, ?> modelProgramColumn;

    @FXML
    private TableColumn<?, ?> isProgramColumn;

    @FXML
    private Button buyBtn;

    @FXML
    private Text priceText;

    @FXML
    void deleteItem(MouseEvent event) throws IOException {
        String s = gamesTable.getSelectionModel().getSelectedItems().toString();
        StringBuffer sbf = new StringBuffer(s);
        sbf.delete(0, 1);
        sbf.delete(5, 6);
        String ss = sbf.toString();
        String[] data = ss.split(" ");
//        System.out.println(Arrays.toString(data));
        int isProgram = Integer.parseInt(data[0]);
        int idGood = Integer.parseInt(data[1]);
        int idUser = Integer.parseInt(data[2]);

        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request8 = new Request("DELETE_FROM_CHOSEN_BASKET", idUser, idGood, isProgram);
        oos.writeObject(request8);

        JOptionPane.showMessageDialog(null, "Удален из корзины!", "Successful", JOptionPane.INFORMATION_MESSAGE);


        backBtn.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxmlFiles/userPanel.fxml"));

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

    ObservableList<Basket> bask = FXCollections.observableArrayList();
    public ObservableList<Basket> getBaskets(int idUser){
        try {

            Socket socket = new Socket("localhost", 11112);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


            Request request2 = new Request("GET_BASKET_BY_ID", idUser);
            oos.writeObject(request2);
            Request request3 = (Request)ois.readObject();
            ArrayList<Basket> list = request3.getBaskets();
            System.out.println(list.toString());
            bask.addAll(list);

            oos.close();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bask;
    }

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request = new Request("GET_USERS_BY_LOGIN", login);
        oos.writeObject(request);
        Request request1 = (Request) ois.readObject();
        ArrayList<User> list = request1.getUsers();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
        isprogramColumn.setCellValueFactory(new PropertyValueFactory<>("isProgram"));
        gamesTable.setItems(getBaskets(list.get(0).getId().intValue()));

        Request request2 = new Request("GET_BASKET_BY_ID", list.get(0).getId().intValue());
        oos.writeObject(request2);
        Request request3 = (Request)ois.readObject();
        ArrayList<Basket> baskets = request3.getBaskets();
        double money = 0;

        for (Basket basket : baskets){
            money += (basket.getQuantity() * basket.getPrice());
        }
        priceText.setText(Double.toString(money) + "Рублей");

        buyBtn.setOnAction(actionEvent -> {
            try {
                Request request4 = new Request("GET_GAMES");
                oos.writeObject(request4);
                Request request5 = (Request)ois.readObject();
                ArrayList<Games> games = request5.getGames();

                Request request6 = new Request("GET_PROGRAM");
                oos.writeObject(request6);
                Request request7 = (Request)ois.readObject();
                ArrayList<Program> programs = request7.getPrograms();

                int totalQuantity = 0;
                double sum = 0;
                for (Basket basket : baskets){
                    if (basket.getIsProgram() == 1){
                        for (Games games1 : games){
                            if (basket.getIdGood().equals(games1.getId())){
                                Request request8 = new Request("BUY_GAMES", games1.getSold() + basket.getQuantity(), games1.getQuantity() - basket.getQuantity(), games1.getId().intValue());
                                oos.writeObject(request8);
                                System.out.println("Успешно");
                                totalQuantity += basket.getQuantity();
                                Date date = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                String currDate = formatter.format(date);
                                sum += games1.getPrice() * basket.getQuantity();
                                HistoryPurchase historyPurchase = new HistoryPurchase(null, list.get(0).getId(), games1.getId(), 1, games1.getGenre(), games1.getName(), basket.getQuantity(), games1.getPrice(), games1.getPrice() * basket.getQuantity(), currDate);
                                System.out.println(historyPurchase.toString());
                                Request request9 = new Request("ADD_TO_HISTORY", historyPurchase);
                                oos.writeObject(request9);
                            }
                        }
                    }
                    else if (basket.getIsProgram() == 0){
                        for (Program program : programs){
                            if (basket.getIdGood().equals(program.getId())){
                                Request request8 = new Request("BUY_PROGRAM", program.getSold() + basket.getQuantity(), program.getQuantity() - basket.getQuantity(), program.getId().intValue());
                                oos.writeObject(request8);
                                System.out.println("Успешно");
                                totalQuantity += basket.getQuantity();
                                Date date = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                                String currDate = formatter.format(date);
                                sum += program.getPrice() * basket.getQuantity();
                                HistoryPurchase historyPurchase = new HistoryPurchase(null, list.get(0).getId(), program.getId(), 0, program.getGenre(), program.getName(), basket.getQuantity(), program.getPrice(), program.getPrice() * basket.getQuantity(), currDate);
                                System.out.println(historyPurchase.toString());
                                Request request9 = new Request("ADD_TO_HISTORY", historyPurchase);
                                oos.writeObject(request9);
                            }
                        }
                    }
                }

                setQuantity(totalQuantity);
                setSum(sum);
                Request request8 = new Request("DELETE_FROM_BASKET", list.get(0).getId().intValue());
                oos.writeObject(request8);

                buyBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxmlFiles/informationInput.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                JOptionPane.showMessageDialog(null, "Successful purchase!", "Successful", JOptionPane.INFORMATION_MESSAGE);


        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/userPanel.fxml"));

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
