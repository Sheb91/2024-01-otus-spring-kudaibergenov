package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Modifying
    @Query("UPDATE Author a SET a.name = :name WHERE a.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
