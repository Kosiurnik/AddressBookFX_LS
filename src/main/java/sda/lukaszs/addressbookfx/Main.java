package sda.lukaszs.addressbookfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sda.lukaszs.addressbookfx.controller.AddressBookFXController;
import sda.lukaszs.addressbookfx.model.Person;
import sda.lukaszs.addressbookfx.repository.PersonRepository;

import java.io.IOException;

public class Main extends Application {

    private final static Logger logger = Logger.getLogger(Main.class);
    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("addressbook-context.xml");
    private PersonRepository personRepository = context.getBean("PersonRepository",PersonRepository.class);

    private ObservableList<Person> personList = FXCollections.observableArrayList();

    public Main() {
        loadFromRepository();
    }

    public ObservableList<Person> getPersonList() {
        return personList;
    }
    public PersonRepository getPersonRepository() { return personRepository; }

    public static void main(String[] args) {
        BasicConfigurator.configure();
        new HibernateInit();
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

    private void loadFromRepository(){
        //wyciągam wszystkich ludzi z bazy danych
        personList.addAll(personRepository.getPeople());
    }
}
