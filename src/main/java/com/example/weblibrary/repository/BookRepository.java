package com.example.weblibrary.repository;

import com.example.weblibrary.model.book.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Репозиторий для работы с {@link Book}
 */
@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    Optional<Book> findById(UUID id);
}
