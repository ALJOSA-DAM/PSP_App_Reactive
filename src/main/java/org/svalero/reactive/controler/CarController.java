package org.svalero.reactive.controler;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.svalero.reactive.task.CarTask;
import org.svalero.reactive.util.ZipFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CarController {

    @FXML
    private ListView<String> resultsListView;
    @FXML
    private Button btExport;
    @FXML
    private Label lbStatus;

    private ObservableList<String> results;
    private CarTask carTask;

    private Executor executor;
    private int contador;

    public CarController() {
        this.results = FXCollections.observableArrayList();
        this.executor = Executors.newFixedThreadPool(3);
        this.contador = 0;
    }

    @FXML
    public void initialize() {
        this.results.clear();
        this.resultsListView.setItems(this.results);
        this.carTask = new CarTask(this.results);
        this.carTask.messageProperty().addListener((observableValue, oldValue, newValue) -> this.lbStatus.setText(newValue));
        new Thread(carTask).start();
    }

    @FXML
    public void exportCSV(ActionEvent event) {
        String outputFileName = System.getProperty("user.dir") + System.getProperty("file.separator")
                + contador + "_cars_list.csv";
        contador++;

        File outputFile = new File(outputFileName);

        CompletableFuture<Void> myFuture = CompletableFuture.runAsync(() -> {
            System.out.println("INIT car_list_export THREAD WORKER"
                    + contador + "_cars_list.csv " + Thread.currentThread().getName());
            try {
                FileWriter writer = new FileWriter(outputFile);
                CSVWriter csvWriter = new CSVWriter(writer);
                List<String[]> data = new ArrayList<>();
                for (String cars : this.results) {
                /*
                Descomentar para ralentizar la exportación
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                 */
                    data.add(new String[]{cars});
                }
                csvWriter.writeAll(data);
                csvWriter.close();
                ZipFile.createZipFile(outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("END car_list_export THREAD WORKER"
                    + contador + "_cars_list.csv " + Thread.currentThread().getName());
        }, executor);
    }
}
