package sda.lukaszs.addressbookfx.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sda.lukaszs.addressbookfx.Main;
import sda.lukaszs.addressbookfx.model.Person;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
    public Button fxButtonImportData;
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete person");
            alert.setHeaderText(null);
            alert.setContentText(String.format("Do you want to delete %s %s?", fxPersonTableView.getSelectionModel().getSelectedItem().getName(), fxPersonTableView.getSelectionModel().getSelectedItem().getLastName()));

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                main.getPersonRepository().deletePerson(fxPersonTableView.getSelectionModel().getSelectedItem());
                main.getPersonList().remove(fxPersonTableView.getSelectionModel().getSelectedItem());
                if(fxPersonTableView.getSelectionModel().getSelectedItem() != null)
                    fillLabels(fxPersonTableView.getSelectionModel().getSelectedItem());
                else
                    fillLabels(new Person("","","","","",""));
            }
        }
    }

    public void savePerson(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save database to file");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv"),
                new FileChooser.ExtensionFilter("JSON", "*.json")
        );
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        if(file != null){
            try{
                System.out.println(file.getName());
                switch(getFileExtension(file.getName())){
                    case "csv": Person.toCSV(file.getPath(),main.getPersonList()); break;
                    case "json": Person.toJSON(file.getPath(),main.getPersonList()); break;
                    default: throw new IOException("Unsupported extension. Can read only valid csv or json.");
                }
            }catch(IOException e){
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unsupported extension!");
                alert.showAndWait();
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Database saved");
            alert.setHeaderText(null);
            alert.setContentText("Database saved successfully!");
            alert.showAndWait();
        }
    }

    private void fillLabels(Person person){
        fxName.setText(person.getName());
        fxLastName.setText(person.getLastName());
        fxAddress.setText(person.getAddress());
        fxPostalCode.setText(person.getPostalCode());
        fxCity.setText(person.getCity());
        fxTelephone.setText(person.getTelephone());
    }

    public void importData(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file to import");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        FileChooser.ExtensionFilter fileExtensions =
                new FileChooser.ExtensionFilter(
                        "Addressbook data", "*.json", "*.csv");
        fileChooser.getExtensionFilters().add(fileExtensions);
        Stage stage = new Stage();
        File file = fileChooser.showOpenDialog(stage);
        if(file != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Import data");
            alert.setHeaderText(null);
            alert.setContentText("Importing from the file will clear current data. Do you want to proceed?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK){
                switch(getFileExtension(file.getName())){
                    case "csv": main.getPersonList().clear(); main.getPersonList().addAll(Person.fromCSV(file)); break;
                    case "json": main.getPersonList().clear(); main.getPersonList().addAll(Person.fromJSON(file)); break;
                    default: break;
                }
                if(main.getPersonList().size() != 0)
                    main.getPersonRepository().addPeople(main.getPersonList());
            }

        }
    }

    public static String getFileExtension(String fileName){
        int i = fileName.lastIndexOf('.');
        return i >= 0 ? fileName.substring(i+1) : "";
    }

}
