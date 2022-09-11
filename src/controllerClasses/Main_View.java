package controllerClasses;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class Main_View  extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("main_view.fxml"));

        // Панорамный график
        PieChart pieChart = (PieChart)root.lookup("#pieChart");
        PieChartUtils pieChartUtils = new PieChartUtils();

        BarChart bc = (BarChart)root.lookup("#barChart");
        BarChartUtils barChartUtils = new BarChartUtils(bc);
        barChartUtils.operateBarChart();

        primaryStage.setTitle("Диаграмма");
        primaryStage.setScene(new Scene(root, 797, 484));
        primaryStage.show();
    }
}
