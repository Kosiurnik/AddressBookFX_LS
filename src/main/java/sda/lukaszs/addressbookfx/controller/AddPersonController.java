package sda.lukaszs.addressbookfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sda.lukaszs.addressbookfx.Main;
import sda.lukaszs.addressbookfx.model.Person;

import java.net.URL;
import java.util.ResourceBundle;

public class AddPersonController implements Initializable {

    private Main main;

    @FXML
    private TextField fxPersonFormName;
    @FXML
    private TextField fxPersonFormLastName;
    @FXML
    private TextField fxPersonFormAddress;
    @FXML
    private TextField fxPersonFormPostalCode;
    @FXML
    private TextField fxPersonFormCity;
    @FXML
    private TextField fxPersonFormTelephone;
    @FXML
    private Button fxPersonFormSaveButton;
    @FXML
    private Button fxPersonFormCancelButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addPerson(MouseEvent mouseEvent) {
        Person person = new Person(
                fxPersonFormName.getText(),
                fxPersonFormLastName.getText(),
                fxPersonFormAddress.getText(),
                fxPersonFormPostalCode.getText(),
                fxPersonFormTelephone.getText(),
                fxPersonFormCity.getText()
        );
        main.getPersonList().add(person);
        main.getPersonRepository().addPerson(person);
        Stage stage = (Stage) fxPersonFormSaveButton.getScene().getWindow();
        stage.close();
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) fxPersonFormCancelButton.getScene().getWindow();
        stage.close();
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
