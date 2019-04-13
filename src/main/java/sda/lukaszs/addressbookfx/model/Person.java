package sda.lukaszs.addressbookfx.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Person")
@Access(value = AccessType.PROPERTY)
@NoArgsConstructor
public class Person {

    private long personID;
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

    public static void toCSV(String filename, List<Person> people){
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter((new FileWriter(filename)));
            for(Person person : people){
                bufferedWriter.write(person.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null){
                try {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static List<Person> fromJSON(File file){
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



    public static List<Person> fromCSV(File csvFile) {
        List<Person> output = new ArrayList<>();
        BufferedReader bufferedReader = null;
        String line = "";
        String splitBy = ";"; //windowsowy excel oddziela średnikiem

        try {
            bufferedReader = new BufferedReader(new FileReader(csvFile));
            while ((line = bufferedReader.readLine()) != null){
                String[] person = line.split(splitBy);
                output.add(new Person(person[0].replaceAll("\"", ""),
                        person[1].replaceAll("\"", ""),
                        person[2].replaceAll("\"", ""),
                        person[3].replaceAll("\"", ""),
                        person[4].replaceAll("\"", ""),
                        person[5].replaceAll("\"", "")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return output;
    }



    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "personID", unique = true, nullable = false)
    public long getPersonID() { return personID; }
    public void setPersonID(long personID) { this.personID = personID; }

    @JsonProperty("name")
    @Column(name = "name", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setName(String name) {
        this.name.set(name);
    }
    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }

    @JsonProperty("lastName")
    @Column(name = "lastName", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public String getLastName() {
        return lastName.get();
    }
    public StringProperty lastNameProperty() {
        return lastName;
    }

    @JsonProperty("address")
    @Column(name = "address", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setAddress(String address) {
        this.address.set(address);
    }
    public String getAddress() {
        return address.get();
    }
    public StringProperty addressProperty() {
        return address;
    }

    @JsonProperty("postalCode")
    @Column(name = "postalCode", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }
    public String getPostalCode() {
        return postalCode.get();
    }
    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    @JsonProperty("telephone")
    @Column(name = "telephone", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }
    public String getTelephone() {
        return telephone.get();
    }
    public StringProperty telephoneProperty() {
        return telephone;
    }


    @JsonProperty("city")
    @Column(name = "city", columnDefinition="VARCHAR(255) DEFAULT NULL")
    public void setCity(String city) {
        this.city.set(city);
    }
    public String getCity() {
        return city.get();
    }
    public StringProperty cityProperty() {
        return city;
    }



    @Override
    public String toString() { //format csv rozdzielanego średnikami (microsoft excel)
        return String.format("\"%s\";\"%s\";\"%s\";\"%s\";\"%s\";\"%s\"", getName(),getLastName(),getAddress(),getPostalCode(),getCity(),getTelephone());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(getName(), person.getName()) &&
                Objects.equals(getLastName(), person.getLastName()) &&
                Objects.equals(getAddress(), person.getAddress()) &&
                Objects.equals(getPostalCode(), person.getPostalCode()) &&
                Objects.equals(getTelephone(), person.getTelephone()) &&
                Objects.equals(getCity(), person.getCity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLastName(), getAddress(), getPostalCode(), getTelephone(), getCity());
    }
}
