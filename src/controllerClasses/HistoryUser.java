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
import mainClasses.Games;
import mainClasses.Program;
import mainClasses.HistoryPurchase;
import mainClasses.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HistoryUser {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Text nameUserText;

    @FXML
    private Text basketCountText;

    @FXML
    private TextArea historyTextArea;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Controller controller = new Controller();
        String login = controller.getLogName();
        System.out.println(login);
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request = new Request("GET_USERS_BY_LOGIN", login);
        oos.writeObject(request);
        Request request1 = (Request) ois.readObject();
        ArrayList<User> list = request1.getUsers();

        Request request2 = new Request("GET_HISTORY_BY_ID", list.get(0).getId().intValue());
        oos.writeObject(request2);
        Request request3 = (Request) ois.readObject();
        ArrayList<HistoryPurchase> historyPurchases = request3.getHistoryPurchases();
        System.out.println(historyPurchases.toString());

        Request request4 = new Request("GET_GAMES");
        oos.writeObject(request4);
        Request request5 = (Request) ois.readObject();
        ArrayList<Games> games = request5.getGames();

        Request request6 = new Request("GET_PROGRAM");
        oos.writeObject(request6);
        Request request7 = (Request)ois.readObject();
        ArrayList<Program> programs = request7.getPrograms();

        for (HistoryPurchase hp : historyPurchases){
            if (hp.getIsProgram() == 1){
                for (Games cs : games){
                    if (hp.getIdGood().equals(cs.getId())){
                        System.out.println("Games history");
                        historyTextArea.appendText(hp.getDate() + " " + list.get(0).getFirstName() + " " + list.get(0).getLastName() + " купил " + hp.getQuantity() + " " + cs.getGenre() + "с названием " + cs.getName() + " суммой в " + hp.getSum() + " рублей.\n");
                    }
                }
            }
            else if (hp.getIsProgram() == 0){
                for (Program fb : programs){
                    if (hp.getIdGood().equals(fb.getId())){
                        System.out.println("Programs history");
                        historyTextArea.appendText(hp.getDate() + " " + list.get(0).getFirstName() + " " + list.get(0).getLastName() + " купил " + hp.getQuantity() + " " + fb.getGenre() + " с названием " + fb.getName() + " суммой в " + hp.getSum()  + " рублей.\n");
                    }
                }
            }
        }


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
