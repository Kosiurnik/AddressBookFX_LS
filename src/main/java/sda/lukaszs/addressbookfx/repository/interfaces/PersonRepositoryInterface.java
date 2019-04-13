package sda.lukaszs.addressbookfx.repository.interfaces;

import sda.lukaszs.addressbookfx.model.Person;

import java.util.List;

public interface PersonRepositoryInterface {
    Person getPersonById(int id);
    List<Person> getPeople();
    void addPerson(Person person);
    void addPeople(List<Person> people);
    void editPerson(Person person);
    void deletePerson(Person person);
}
