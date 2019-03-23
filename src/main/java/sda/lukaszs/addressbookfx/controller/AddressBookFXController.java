package sda.lukaszs.addressbookfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sda.lukaszs.addressbookfx.Main;
import sda.lukaszs.addressbookfx.model.Person;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddressBookFXController implements Initializable {

    private Main main;

    @FXML
    public Label fxName;
    @FXML
    public Label fxLastName;
    @FXML
    public Label fxPostalCode;
    @FXML
    public Label fxAddress;
    @FXML
    public Label fxTelephone;
    @FXML
    public Label fxCity;
    @FXML
    public Button fxButtonNewPerson;
    @FXML
    public Button fxButtonEditPerson;
    @FXML
    public Button fxButtonDeletePerson;
    @FXML
    public Button fxButtonSavePerson;
    @FXML
    public TableView<Person> fxPersonTableView;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnName;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnLastName;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnAddress;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnPostalCode;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnCity;
    @FXML
    public TableColumn<Person, String> fxPersonTableColumnTelephone;

    public void setMain(Main main) {
        this.main = main;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fxName.setText("");
        fxLastName.setText("");
        fxAddress.setText("");
        fxPostalCode.setText("");
        fxCity.setText("");
        fxTelephone.setText("");
    }

    public void loadPerson() {
        System.out.printf("Załadowano osób: %d%n", main.getPersonList().size());
        fxPersonTableView.setItems(main.getPersonList());
        fxPersonTableColumnName.setCellValueFactory(c-> c.getValue().nameProperty());
        fxPersonTableColumnLastName.setCellValueFactory(c->c.getValue().lastNameProperty());
        fxPersonTableColumnAddress.setCellValueFactory(c->c.getValue().addressProperty());
        fxPersonTableColumnPostalCode.setCellValueFactory(c->c.getValue().postalCodeProperty());
        fxPersonTableColumnCity.setCellValueFactory(c->c.getValue().cityProperty());
        fxPersonTableColumnTelephone.setCellValueFactory(c->c.getValue().telephoneProperty());
    }

    public void selectedPerson(MouseEvent mouseEvent) {
        fillLabels(fxPersonTableView.getSelectionModel().getSelectedItem());
    }

    public void newPerson(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/addPerson.fxml"));
        Scene scene = new Scene(loader.load(), 600, 400);
        AddPersonController addPersonController = loader.getController();
        addPersonController.setMain(main);
        Stage stage = new Stage();
        stage.setTitle("Add new person");
        stage.requestFocus();
        stage.setScene(scene);
        stage.show();
    }

    public void editPerson(MouseEvent mouseEvent) throws IOException {
        if(fxPersonTableView.getSelectionModel().getSelectedItem() != null){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/editPerson.fxml"));
            Scene scene = new Scene(loader.load(), 600, 400);
            EditPersonController editPersonController = loader.getController();
            editPersonController.setMain(main);
            editPersonController.setSelectedPerson(fxPersonTableView.getSelectionModel().getSelectedItem());
            editPersonController.initForm();
            Stage stage = new Stage();
            stage.setTitle("Edit person");
            stage.requestFocus();
            stage.setScene(scene);
            stage.show();
        }
    }

    public void deletePerson(MouseEvent mouseEvent) {
        if(fxPersonTableView.getSelectionModel().getSelectedItem() != null){
            main.getPersonList().remove(fxPersonTableView.getSelectionModel().getSelectedItem());
            if(fxPersonTableView.getSelectionModel().getSelectedItem() != null)
                fillLabels(fxPersonTableView.getSelectionModel().getSelectedItem());
            else
                fillLabels(new Person("","","","","",""));
        }
    }

    public void savePerson(MouseEvent mouseEvent) {

    }

    private void fillLabels(Person person){
        fxName.setText(person.getName());
        fxLastName.setText(person.getLastName());
        fxAddress.setText(person.getAddress());
        fxPostalCode.setText(person.getPostalCode());
        fxCity.setText(person.getCity());
        fxTelephone.setText(person.getTelephone());
    }
}
