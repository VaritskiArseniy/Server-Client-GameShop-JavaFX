package controllerClasses;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserAdminPanel {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button showProductsBtn;

    @FXML
    private Button addStaffBtn;

    @FXML
    private Button deleteStaffBtn;

    @FXML
    private Button historyBtn;

    @FXML
    private Button deleteProductBtn;

    @FXML
    private Button addQuantityBtn;

    @FXML
    private Button incomeBtn;

    @FXML
    private Button backBtn;

    @FXML
    void initialize() {

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

    public Button getDeleteProductBtn() {
        return deleteProductBtn;
    }

    public void setDeleteProductBtn(Button deleteProductBtn) {
        this.deleteProductBtn = deleteProductBtn;
    }
}
