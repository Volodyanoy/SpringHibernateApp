package org.example.volodyanoy.dao;

import org.example.volodyanoy.models.Person;
import org.hibernate.Session;
import org.hibernate.collection.internal.PersistentBag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


//Solve problem 'N + 1'
@Component
public class PersonDAO {

    private final EntityManager entityManager;

    @Autowired
    public PersonDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional(readOnly = true)
    public void testNPlus1(){
        Session session = entityManager.unwrap(Session.class);

        //PROBLEM 'N+1'
        /* // 1 Запрос
        List<Person> people = session.createQuery("select p from Person p", Person.class)
                .getResultList();

           // N запросов к БД
        for(Person person: people)
            System.out.println("Person " + person.getName() + " has items: " + person.getItems());*/

        //SOLUTION
        Set<Person> people = new HashSet<Person>(session.createQuery("select p from Person p LEFT JOIN FETCH p.items", Person.class)
                .getResultList());

        for(Person person: people)
            System.out.println("Person " + person.getName() + " has items: " + person.getItems());

    }

}
