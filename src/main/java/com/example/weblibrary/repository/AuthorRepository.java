package com.example.weblibrary.repository;

import com.example.weblibrary.model.author.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link Author}
 */
@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {

    Optional<Author> findById(UUID id);
}
