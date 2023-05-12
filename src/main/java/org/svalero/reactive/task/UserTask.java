package org.svalero.reactive.task;

import io.reactivex.functions.Consumer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.svalero.reactive.model.Usuario;
import org.svalero.reactive.service.CarsApiService;

public class UserTask extends Task<Integer> {

    private String userDni;
    private ObservableList<String> results;
    private int counter;

    public UserTask(String userDni, ObservableList<String> results) {
        this.userDni = userDni;
        this.results = results;
        this.counter = 0;
    }

    @Override
    protected Integer call() throws Exception {
        CarsApiService carsApiService = new CarsApiService();
        Consumer<Usuario> usuarioConsumer = (usuario) -> {
            this.counter++;
            Thread.sleep(250);
            updateMessage(String.valueOf(this.counter) + " usuario descargado");
            Platform.runLater(() -> this.results.add(usuario.toString()));
        };

        carsApiService.getUsers(userDni).subscribe(usuarioConsumer);
        carsApiService.getUsers(userDni).toString();
        System.out.println("Call() 3");

        return null;
    }

}
