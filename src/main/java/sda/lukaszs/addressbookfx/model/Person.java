package sda.lukaszs.addressbookfx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {
    private StringProperty name;
    private StringProperty lastName;
    private StringProperty address;
    private StringProperty postalCode;
    private StringProperty telephone;
    private StringProperty city;

    public Person(String name, String lastName, String address, String postalCode, String telephone, String city) {
        this.name = new SimpleStringProperty(name);
        this.lastName = new SimpleStringProperty(lastName);
        this.address = new SimpleStringProperty(address);
        this.postalCode = new SimpleStringProperty(postalCode);
        this.telephone = new SimpleStringProperty(telephone);
        this.city = new SimpleStringProperty(city);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLastName() {
        return lastName.get();
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    @Override
    public String toString() {
        return String.format("%s %s, ul. %s %s %s, tel.: %s", getName(),getLastName(),getAddress(),getPostalCode(),getCity(),getTelephone());
    }
}
