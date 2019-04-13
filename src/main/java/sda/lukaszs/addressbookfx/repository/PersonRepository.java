package sda.lukaszs.addressbookfx.repository;


import org.springframework.stereotype.Repository;
import sda.lukaszs.addressbookfx.manager.HibernateEntityManager;
import sda.lukaszs.addressbookfx.model.Person;
import sda.lukaszs.addressbookfx.repository.interfaces.PersonRepositoryInterface;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonRepository implements PersonRepositoryInterface {
    @Override
    public Person getPersonById(int id) {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            TypedQuery<Person> query = em.getEntityManager().createQuery("select p from Person p where p.personID = :pID",Person.class).setParameter("pID", id);
            Person person = query.getSingleResult();
            if(person != null){
                return person;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Person> getPeople() {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            List<Person> people = em.getEntityManager().createQuery("select p from Person p order by p.personID",Person.class).getResultList();
            if(people.size()!=0){
                return people;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void addPerson(Person person) {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            em.getEntityManager().getTransaction().begin();
            em.getEntityManager().persist(person);
            em.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPeople(List<Person> people) {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            em.getEntityManager().getTransaction().begin();
            for(Person person : people){
                em.getEntityManager().persist(person);
            }
            em.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editPerson(Person person) {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            em.getEntityManager().getTransaction().begin();
            em.getEntityManager().merge(person);
            em.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePerson(Person person) {
        try(HibernateEntityManager em = new HibernateEntityManager()){
            em.getEntityManager().getTransaction().begin();
            em.getEntityManager().remove(em.getEntityManager().find(Person.class, person.getPersonID()));
            em.getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
