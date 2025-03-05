package ru.practicum.shareit.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByOwnerId(Long ownerId);
    List<Item> findByRequestId(Long requestId);

    @Query("SELECT i FROM Item i WHERE LOWER(i.name) LIKE %:text% OR LOWER(i.description) LIKE %:text%")
    List<Item> searchByNameOrDescription(String text);
}
