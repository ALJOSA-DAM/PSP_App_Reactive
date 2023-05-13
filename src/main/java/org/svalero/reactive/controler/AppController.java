package org.svalero.reactive.controler;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import org.svalero.reactive.util.R;

import java.io.IOException;

public class AppController {

    @FXML
    private TextField tfDni;
    @FXML
    private Button btSearchByDni;
    @FXML
    private Button btCars;
    @FXML
    private TabPane tpCars;
    @FXML
    private ImageView ivLogo;

    @FXML
    public void initialize() {
        Image image = new Image(R.getImage("lambo_transp.png"));
        ivLogo.setImage(image);
    }

    @FXML
    public void searchByDni(ActionEvent event) {

        String userDni = tfDni.getText().trim();
        this.tfDni.requestFocus();

        if (userDni.equals("")) {
            System.out.println("Introduce un DNI en el recuadro");  // BORRAR XXXXXX
            System.out.println("USER DNI " + userDni + " " + tfDni.getText());  // BORRAR XXXXXX
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Aviso!");
            alert.setContentText("Introduce un DNI en el recuadro");
            alert.show();
        } else {
            System.out.println("USER DNI " + userDni + " " + tfDni.getText());  // BORRAR XXXXXX
            searchByDni(userDni);
        }

    }

    private void searchByDni(String userDni) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("results.fxml"));
        UserController userController = new UserController(userDni);
        loader.setController(userController);

        try {
            VBox userBox = loader.load();
            tpCars.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            tpCars.getTabs().add(new Tab(userDni, userBox));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void listCars(ActionEvent event) {
        String cars = "Listado de coches";
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(R.getUI("results.fxml"));
        CarController carController = new CarController();
        loader.setController(carController);

        try {
            VBox carBox = loader.load();
            tpCars.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
            tpCars.getTabs().add(new Tab(cars, carBox));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
