package com.example.weblibrary.service;

import com.example.weblibrary.model.book.Book;
import com.example.weblibrary.model.book.dto.InputBookDTO;

/**
 * Интерфейс с набором методов для работы библиотеки
 */
public interface BookService {

    /**
     * Метод для получения книги из базы данных
     *
     * @param bookId идентификатор книги в формате UUID
     * @return {@link Book}
     */
    Book getBook(String bookId);

    /**
     * Метод создает книгу с заданными парметрами
     *
     * @param dto {@link InputBookDTO}
     */
    void createBook(InputBookDTO dto);

    /**
     * Метод для обновления книг
     *
     * @param bookId bookId идентификатор книги в формате UUID
     * @param dto    {@link InputBookDTO}
     */
    void updateBook(String bookId, InputBookDTO dto);

    /**
     * Метод для удаления книги из репозитория
     *
     * @param bookId идентификатор книги в формате UUID
     */
    void deleteBook(String bookId);

    /**
     * Метод меняет каталог книги с приватного на публичный и наоборот
     *
     * @param bookId идентификатор книги в формате UUID
     */
    void changeBookDirectory(String bookId);

}
