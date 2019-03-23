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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main extends Application {

    private String jsonFileName = "json/database.json";
    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public Main() throws IOException {
        File jsonFile = new File(jsonFileName);
        if(Files.exists(Paths.get(jsonFileName))){
            personList = Person.fromJSON(jsonFile);
        }else{
            if(!Files.exists(Paths.get("json")))
                Files.createDirectory(Paths.get("json"));
            Files.createFile(Paths.get(jsonFileName));
            personList.add(new Person("Jan","Kowalski","Bydgoska 4","85-123","123123111","Bydgoszcz"));
            personList.add(new Person("Barbara","Jasińska","Toruńska 3","85-123","123123111","Bydgoszcz"));
            personList.add(new Person("Hubert","Rozwarski","Lazurowa 1","85-123","123123111","Bydgoszcz"));
            personList.add(new Person("Anna","Sobczak","Powstańców 6","85-123","123123111","Bydgoszcz"));
            Person.toJSON(jsonFileName,personList);
        }

    }
    public String getJsonFileName() {
        return jsonFileName;
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
