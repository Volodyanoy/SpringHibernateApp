package org.example.volodyanoy.repositories;

import org.example.volodyanoy.models.Item;
import org.example.volodyanoy.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findByOwner(Person owner);
    List<Item> findByItemName(String itemName);
}
