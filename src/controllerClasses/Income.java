package controllerClasses;

import ServerAndSocket.Request;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mainClasses.HistoryPurchase;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Income {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button diagramBtn;

    @FXML
    private Text nameUserText;

    @FXML
    private Text basketCountText;

    @FXML
    private TextArea gamesTextArea;

    @FXML
    private TextArea programTextArea;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
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

        gamesTextArea.setText(Double.toString(incomeGames));
        programTextArea.setText(Double.toString(incomeProgram));

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

        diagramBtn.setOnAction(actionEvent -> {
            diagramBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();

            loader.setLocation(getClass().getResource("/fxmlFiles/Sample.fxml"));



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
