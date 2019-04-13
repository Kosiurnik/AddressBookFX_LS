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

public class EditPersonController implements Initializable {

    private Main main;


    private Person selectedPerson;

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


    public void setMain(Main main) {
        this.main = main;
    }


    public void setSelectedPerson(Person selectedPerson) {
        this.selectedPerson = selectedPerson;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initForm(){
        if(selectedPerson!=null){
            fxPersonFormName.setText(selectedPerson.getName());
            fxPersonFormLastName.setText(selectedPerson.getLastName());
            fxPersonFormAddress.setText(selectedPerson.getAddress());
            fxPersonFormPostalCode.setText(selectedPerson.getPostalCode());
            fxPersonFormCity.setText(selectedPerson.getCity());
            fxPersonFormTelephone.setText(selectedPerson.getTelephone());
        }
    }

    public void savePerson(MouseEvent mouseEvent) {
        selectedPerson.setName(fxPersonFormName.getText());
        selectedPerson.setLastName(fxPersonFormLastName.getText());
        selectedPerson.setAddress(fxPersonFormAddress.getText());
        selectedPerson.setPostalCode(fxPersonFormPostalCode.getText());
        selectedPerson.setTelephone(fxPersonFormTelephone.getText());
        selectedPerson.setCity(fxPersonFormCity.getText());
        /*Person person = new Person(
                fxPersonFormName.getText(),
                fxPersonFormLastName.getText(),
                fxPersonFormAddress.getText(),
                fxPersonFormPostalCode.getText(),
                fxPersonFormTelephone.getText(),
                fxPersonFormCity.getText()
        );*/
        main.getPersonList().set(main.getPersonList().indexOf(selectedPerson),selectedPerson);
        main.getPersonRepository().editPerson(selectedPerson);
        Stage stage = (Stage) fxPersonFormSaveButton.getScene().getWindow();
        stage.close();
    }

    public void cancel(MouseEvent mouseEvent) {
        Stage stage = (Stage) fxPersonFormCancelButton.getScene().getWindow();
        stage.close();
    }

}
