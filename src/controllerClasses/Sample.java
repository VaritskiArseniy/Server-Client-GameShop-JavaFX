package controllerClasses;

import ServerAndSocket.Request;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import mainClasses.HistoryPurchase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



public class Sample implements Initializable {

    @FXML
    private PieChart piechart;

    @FXML
    private Button backBtn;

    @FXML
    private void handleButton1Action(ActionEvent event) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request2 = new Request("GET_HISTORY");
        oos.writeObject(request2);
        Request request3 = (Request) ois.readObject();
        ArrayList<HistoryPurchase> historyPurchases = request3.getHistoryPurchases();

        double incomeGames = 0;
        double incomeProgram = 0;
        for (HistoryPurchase hp : historyPurchases){
            if (hp.getIsProgram() == 1){
                incomeGames += hp.getSum();
            }
            else {
                incomeProgram += hp.getSum();
            }
        }
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Программы", incomeProgram),
                        new PieChart.Data("Игры", incomeGames));

        piechart.setTitle("Проданные товары");
        piechart.setData(pieChartData);
    }

    @FXML
    private void handleButton2Action(ActionEvent event) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Sunday", 30),
                        new PieChart.Data("Monday", 45),
                        new PieChart.Data("Tuesday", 70),
                        new PieChart.Data("Wednesday", 97),
                        new PieChart.Data("Thursday", 100),
                        new PieChart.Data("Friday", 80),
                        new PieChart.Data("Saturday", 10));

        piechart.setTitle("Weekly Record");
        piechart.setData(pieChartData);
    }

    @FXML
    private void handleButtonClearAction(ActionEvent event) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        piechart.setTitle("");
        piechart.setData(pieChartData);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        backBtn.setOnAction(actionEvent -> {
            backBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxmlFiles/income.fxml"));

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
