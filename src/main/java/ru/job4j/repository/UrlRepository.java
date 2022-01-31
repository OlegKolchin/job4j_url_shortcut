package ru.job4j.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.job4j.model.Url;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<Url, Integer> {

    Optional<Url> findUrlByShortcut(String shortcut);

    @Modifying
    @Query(value = "update Url set calls = calls + 1 where shortcut = :s")
    void updateCalls(@Param("s") String s);


    Optional<Url> findUrlByName(String name);
}
