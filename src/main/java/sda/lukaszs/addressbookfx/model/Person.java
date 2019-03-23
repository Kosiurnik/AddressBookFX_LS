package sda.lukaszs.addressbookfx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.NoArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class Person {

    //JSON wywala błąd, jeśli się nie zainicjalizuje w tym miejscu
    private StringProperty name = new SimpleStringProperty();
    private StringProperty lastName = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty postalCode = new SimpleStringProperty();
    private StringProperty telephone = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();

    public Person(String name, String lastName, String address, String postalCode, String telephone, String city) {
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.telephone = new SimpleStringProperty(telephone);
        this.city = new SimpleStringProperty(city);
    }

    public static void toJSON(String filename, List<Person> people){
        ObjectMapper mapper = new ObjectMapper();
        try {
            String JsonString = mapper.writeValueAsString(people);
            Files.write(Paths.get(filename),JsonString.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Person> fromJSON(File file){
        ObservableList<Person> output = FXCollections.observableArrayList();
        List<Person> personList = readListFromJSON(file);
        for(Person person : personList){
            output.add(person);
        }
        return output;
    }

    private static List<Person> readListFromJSON(File file){
        ObjectMapper mapper = new ObjectMapper();
        List<Person> output = new ArrayList<>();
        Person[] outArray;
        try {
            outArray = mapper.readValue(file,Person[].class);
            output = Arrays.asList(outArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }


    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name.set(name);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    @JsonProperty("postalCode")
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    @JsonProperty("telephone")
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city.set(city);
    }

    @Override
    public String toString() {
        return String.format("%s %s, ul. %s %s %s, tel.: %s", getName(),getLastName(),getAddress(),getPostalCode(),getCity(),getTelephone());
    }
}
