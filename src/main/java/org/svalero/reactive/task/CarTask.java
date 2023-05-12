package org.svalero.reactive.task;

import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.svalero.reactive.model.Coche;
import org.svalero.reactive.service.CarsApiService;

public class CarTask extends Task<Integer> {

    private ObservableList<String> results;
    private int counter;

    public CarTask(ObservableList<String> results) {
        this.results = results;
        this.counter = 0;
    }

    @Override
    protected Integer call() throws Exception {
        CarsApiService carsApiService = new CarsApiService();

        Consumer<Coche> cocheConsumer = (coche) -> {
            this.counter++;
            Thread.sleep(250);
            updateMessage(String.valueOf(this.counter) + " coches descargados");
            Platform.runLater(() -> this.results.add(coche.toString()));
        };
        carsApiService.getCars().subscribe(cocheConsumer);

        return null;
    }

}
