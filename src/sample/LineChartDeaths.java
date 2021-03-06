package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LineChartDeaths  implements Initializable {
    @FXML
    private javafx.scene.chart.LineChart<String,Number> lineChart;
    @FXML
    private ListView<String> listView;
    ArrayList<Country> selectedCountry = new ArrayList<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i <Controller.countries.size() ; i++) {
            listView.getItems().add(Controller.countries.get(i).getCountryName());
        }
        LoadData();
    }
    private void LoadData(){
        lineChart.getData().clear();
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        for (int i = 0; i <selectedCountry.size() ; i++) {

            XYChart.Series<String,Number> series = new XYChart.Series<String, Number>();
            series.getData().clear();
            int totalDeaths=0;
            int lastThirty=0;
            int initial=0;
            totalDeaths= selectedCountry.get(i).getTotalDeaths();

            for (int j = 0; j < Controller.allCountryData.size(); j++) {
                if(Controller.allCountryData.get(j).getCountryName().equals(selectedCountry.get(i).getCountryName())){
                    lastThirty= lastThirty+ Controller.allCountryData.get(j).getNewDeath();
                }
            }
            initial=totalDeaths-lastThirty;
            for (int j = 0; j < Controller.allCountryData.size(); j++) {

                if(Controller.allCountryData.get(j).getCountryName().equals(selectedCountry.get(i).getCountryName())){
                    initial= initial + Controller.allCountryData.get(j).getNewDeath();
                    series.getData().add(new XYChart.Data<String, Number>(Controller.allCountryData.get(j).getDate(),initial));
                }
            }
            series.setName(String.valueOf(selectedCountry.get(i).getCountryName()));
            lineChart.getData().addAll(series);
        }

    }
    public void UpdateCountryName(ActionEvent actionEvent) throws Exception {
        ObservableList<String> countryName;
        countryName=listView.getSelectionModel().getSelectedItems();
        selectedCountry.clear();

        for (String m: countryName){
            for (int i = 0; i <Controller.countries.size() ; i++) {
                if(m.equals(Controller.countries.get(i).getCountryName())){
                    String countryname= Controller.countries.get(i).getCountryName();
                    int totalcase= Controller.countries.get(i).getTotalCase();
                    int newCase= Controller.countries.get(i).getNewCase();
                    int newDeath = Controller.countries.get(i).getNewDeath();
                    int totalDeaths=Controller.countries.get(i).getTotalDeaths();
                    selectedCountry.add(new Country(countryname,totalcase,newCase,newDeath,totalDeaths,1,1, BigDecimal.valueOf(1)));
                }
            }
        }
        LoadData();
    }
    public void GoBackTableView(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("tableview.fxml"));
        Scene scene=new Scene(tableview,1000,700);

        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
    }
    public void goLineChart(ActionEvent actionEvent) throws Exception {
        Parent tableview = FXMLLoader.load(getClass().getResource("lineChart.fxml"));
        Scene scene=new Scene(tableview,2000,700);

        Stage window=(Stage)((javafx.scene.Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
