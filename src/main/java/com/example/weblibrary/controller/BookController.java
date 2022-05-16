package com.example.weblibrary.controller;

import com.example.weblibrary.model.book.Book;
import com.example.weblibrary.model.book.dto.InputBookDTO;
import com.example.weblibrary.model.interfaces.OnCreate;
import com.example.weblibrary.model.interfaces.OnUpdate;
import com.example.weblibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

/**
 * Контроллер библиотеки
 */
@RestController
@RequestMapping(value = "v1/library/books")
public class BookController {

    @Autowired
    private BookService bookService;

    /**
     * Метод для получения книги
     *
     * @param bookId идентификатор книги в формате UUID
     * @return {@link Book}
     */
    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getBook(@PathVariable("bookId") String bookId) {
        Book book = null;
        try {
            book = bookService.getBook(bookId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(book);
    }

    /**
     * Метод создает книгу с заданными парметрами
     *
     * @param request       {@link InputBookDTO}
     * @param bindingResult
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> createBook(@RequestBody @Validated(OnCreate.class) InputBookDTO request, BindingResult bindingResult) {
        if (bindingResult.getErrorCount() != 0) {
            String error = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body("Ошибка входных параметров: " + error);
        }
        try {
            bookService.createBook(request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Книга успешно добавлена в каталог!");
    }

    /**
     * Метод для обновления книги
     *
     * @param request       {@link InputBookDTO}
     * @param bookId        идентификатор книги в формате UUID
     * @param bindingResult
     */
    @RequestMapping(value = "/{bookId}/update", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> updateBook(@RequestBody @Validated(OnUpdate.class) InputBookDTO request,
                                             @PathVariable("bookId") String bookId, BindingResult bindingResult) {
        if (bindingResult.getErrorCount() != 0) {
            String error = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("; "));
            return ResponseEntity.badRequest().body("Ошибка входных параметров: " + error);
        }
        try {
            bookService.updateBook(bookId, request);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Книга успешно обновлена!");
    }

    /**
     * Метод для удаления книги
     *
     * @param bookId идентификатор книги в формате UUID
     */
    @RequestMapping(value = "/{bookId}/delete", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") String bookId) {
        try {
            bookService.deleteBook(bookId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Книга успешено удалена!");
    }

    /**
     * Метод меняет каталог книги с приватного на публичный и наоборот
     *
     * @param bookId идентификатор книги в формате UUID
     */
    @RequestMapping(value = "/{bookId}/changeDirectory", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<String> changeBookDirectory(@PathVariable("bookId") String bookId) {
        try {
            bookService.changeBookDirectory(bookId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Каталог книги изменен!");
    }

}
