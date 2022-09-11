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

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HistoryAdmin {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Text nameUserText;

    @FXML
    private Text basketCountText;

    @FXML
    private TextArea historyTextArea;

    @FXML
    void initialize() throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost", 11112);
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Request request = new Request("GET_USERS");
        oos.writeObject(request);
        Request request1 = (Request) ois.readObject();
        ArrayList<User> list = request1.getUsers();

        Request request2 = new Request("GET_HISTORY");
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
            for (User user : list){
                if (hp.getIdUser().equals(user.getId())){
                    if (hp.getIsProgram() == 1){
                        for (Games cs : games){
                            if (hp.getIdGood().equals(cs.getId())){
                                historyTextArea.appendText(hp.getDate() + " " + user.getFirstName() + " " + user.getLastName() + " купил  " + hp.getQuantity() + " копий игр в жанре " + cs.getGenre() + " с названием " + cs.getName() + " суммой в " + hp.getSum() + " рублей.\n");
                            }
                        }
                    }
                    else if (hp.getIsProgram() == 0){
                        for (Program fb : programs){
                            if (hp.getIdGood().equals(fb.getId())){
                                historyTextArea.appendText(hp.getDate() + " " + user.getFirstName() + " " + user.getLastName() + " купил " + hp.getQuantity() + " копий программ по назначению является " + fb.getGenre() + " с названием " + fb.getName() + " суммой в " + hp.getSum()  + " рублей.\n");
                            }
                        }
                    }
                }
            }
        }

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

        saveBtn.setOnAction(actionEvent -> {
            File myFile =new File("History.txt");
            try{
                BufferedWriter writer = new BufferedWriter( new FileWriter(myFile));
                for (HistoryPurchase hp : historyPurchases){
                    for (User user : list){
                        if (hp.getIdUser().equals(user.getId())){
                            if (hp.getIsProgram() == 1){
                                for (Games cs : games){
                                    if (hp.getIdGood().equals(cs.getId())){

                                        writer.write(hp.getDate() + " " + user.getFirstName() + " " + user.getLastName() + " купил  " + hp.getQuantity() + " копий игр в жанре " + cs.getGenre() + " с названием " + cs.getName() + " суммой в " + hp.getSum() + " рублей.\n");
                                    }
                                }
                            }
                            else if (hp.getIsProgram() == 0){
                                for (Program fb : programs){
                                    if (hp.getIdGood().equals(fb.getId())){

                                        writer.write( hp.getDate() + " " + user.getFirstName() + " " + user.getLastName() + " купил " + hp.getQuantity() + " копий программ по назначению является " + fb.getGenre() + " с названием " + fb.getName() + " суммой в " + hp.getSum()  + " рублей.\n");
                                    }
                                }
                            }
                        }
                    }
                }

                writer.flush();
                writer.close();
            }catch (IOException ex){
                ex.printStackTrace();
            }


        });
    }
}
