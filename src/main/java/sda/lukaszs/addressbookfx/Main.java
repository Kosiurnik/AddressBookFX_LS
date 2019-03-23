package sda.lukaszs.addressbookfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sda.lukaszs.addressbookfx.controller.AddressBookFXController;
import sda.lukaszs.addressbookfx.model.Person;

public class Main extends Application {

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public Main() {
        personList.add(new Person("Jan","Kowalski","Bydgoska 4","85-123","123123111","Bydgoszcz"));
        personList.add(new Person("Barbara","Jasińska","Toruńska 3","85-123","123123111","Bydgoszcz"));
        personList.add(new Person("Hubert","Rozwarski","Lazurowa 1","85-123","123123111","Bydgoszcz"));
        personList.add(new Person("Anna","Sobczak","Powstańców 6","85-123","123123111","Bydgoszcz"));
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/root.fxml"));
        loader.load();
        Parent root = loader.getRoot();
        AddressBookFXController addressBookFXController = loader.getController();
        addressBookFXController.setMain(this);
        addressBookFXController.loadPerson();

        primaryStage.setTitle("AddressBookFX");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
