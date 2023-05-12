package org.svalero.reactive.controler;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.svalero.reactive.task.UserTask;
import org.svalero.reactive.util.ZipFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserController {

    @FXML
    private ListView<String> resultsListView;
    @FXML
    private Button btExport;
    @FXML
    private Label lbStatus;

    private ObservableList<String> results;
    private UserTask userTask;

    private String userDni;
    private Executor executor;
    private int contador;

    public UserController(String userDni) {
        this.userDni = userDni;
        this.results = FXCollections.observableArrayList();
        this.executor = Executors.newFixedThreadPool(3);
        this.contador = 0;
    }

    @FXML
    public void initialize() {
        this.results.clear();
        this.resultsListView.setItems(this.results);
        this.userTask = new UserTask(userDni, this.results);
        this.userTask.messageProperty().addListener((observableValue, oldValue, newValue) -> this.lbStatus.setText(newValue));
        new Thread(userTask).start();
    }

    @FXML
    public void exportCSV(ActionEvent event) {
        String outputFileName = System.getProperty("user.dir") + System.getProperty("file.separator")
                + contador + "_user_" + userDni + ".csv ";
        contador++;

        File outputFile = new File(outputFileName);

        CompletableFuture<Void> myFuture = CompletableFuture.runAsync(() -> {
            System.out.println("INIT user_export THREAD WORKER" + contador + "_user_"
                    + userDni + ".csv " + Thread.currentThread().getName());
            try {
                FileWriter writer = new FileWriter(outputFile);
                CSVWriter csvWriter = new CSVWriter(writer);
                List<String[]> data = new ArrayList<>();
                for (String users : this.results) {
                /*
                Descomentar para ralentizar la exportación
                    try {
                        Thread.sleep(600);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                 */
                    data.add(new String[]{users});
                }
                csvWriter.writeAll(data);
                csvWriter.close();
                ZipFile.createZipFile(outputFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("END user_export THREAD WORKER" + contador + "_user_"
                    + userDni + ".csv " + Thread.currentThread().getName());
        }, executor);
    }
}
