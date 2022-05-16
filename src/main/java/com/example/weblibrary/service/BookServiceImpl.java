package com.example.weblibrary.service;

import com.example.weblibrary.model.author.Author;
import com.example.weblibrary.model.book.Book;
import com.example.weblibrary.model.book.dto.InputBookDTO;
import com.example.weblibrary.repository.AuthorRepository;
import com.example.weblibrary.repository.BookRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Реализация интерфейса {@link BookService}
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;
    @Autowired
    private AuthorRepository authorRepository;

    private final SimpleDateFormat FORMATTER = new SimpleDateFormat("dd.MM.yyyy");
    private final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getBook(String bookId) {
        isUUID(bookId);
        return repository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new RuntimeException("Книги с таким идентификатором не существует"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createBook(InputBookDTO dto) {
        Book book = new Book();
        LOGGER.info("Создание новой книги");
        book.setName(dto.getName());
        book.setPrivate(dto.isPrivate());
        Date date = parseDateFromString(dto.getReleaseDate());
        book.setReleaseDate(date);
        isUUID(dto.getAuthor());
        Author author = authorRepository.findById(UUID.fromString(dto.getAuthor()))
                .orElseThrow(() -> new RuntimeException("Автора с таким идентификатором не существует"));
        book.setAuthor(author);
        repository.save(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateBook(String bookId, InputBookDTO dto) {
        isUUID(bookId);
        Book book = repository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new RuntimeException("Книги с таким идентификатором не существует"));
        LOGGER.info(MessageFormat.format("Обновление книги с идентификатором {0}.", book.getId()));
        if (StringUtils.isNotBlank(dto.getReleaseDate())) {
            Date date = parseDateFromString(dto.getReleaseDate());
            book.setReleaseDate(date);
        }
        if (StringUtils.isNotBlank(dto.getAuthor())) {
            isUUID(dto.getAuthor());
            Author author = authorRepository.findById(UUID.fromString(dto.getAuthor()))
                    .orElseThrow(() -> new RuntimeException("Автора с таким идентификатором не существует"));
            book.setAuthor(author);
        }
        if (StringUtils.isNotBlank(dto.getName())) {
            book.setName(dto.getName());
        }
        repository.save(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBook(String bookId) {
        isUUID(bookId);
        Book book = repository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new RuntimeException("Книги с таким идентификатором не существует"));
        LOGGER.info(MessageFormat.format("Удаление книги с идентификатором {0}.", book.getId()));
        repository.delete(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeBookDirectory(String bookId) {
        isUUID(bookId);
        Book book = repository.findById(UUID.fromString(bookId))
                .orElseThrow(() -> new RuntimeException("Книги с таким идентификатором не существует"));
        LOGGER.info(MessageFormat.format("Изменение каталога для книги с идентификатором {0}.", book.getId()));
        book.setPrivate(!book.isPrivate());
        repository.save(book);
    }

    /**
     * Метод для проверки валидности UUID
     * @throws RuntimeException в случае невалидного UUID
     */
    private void isUUID(String uuid) throws RuntimeException {
        if (!uuid.matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")) {
            throw new RuntimeException("Введенный идентификатор должен быть в формате UUID");
        }
    }

    /**
     * Метод для парсинга даты из запроса пользователя
     * @throws RuntimeException
     */
    private Date parseDateFromString(String dateFromRequest) throws RuntimeException {
        Date date = null;
        try {
            date = FORMATTER.parse(dateFromRequest);
        } catch (ParseException e) {
            throw new RuntimeException("Укажите дату в формате dd.MM.yyyy");
        }
        return date;
    }
}
