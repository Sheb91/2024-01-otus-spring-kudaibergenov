package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.otus.hw.models.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Modifying
    @Query("UPDATE Genre g SET g.name = :name WHERE g.id = :id")
    void updateNameById(@Param("id") Long id, @Param("name") String name);
}
