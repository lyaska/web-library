package com.example.weblibrary.model.book.dto;

import com.example.weblibrary.model.interfaces.OnCreate;
import com.example.weblibrary.model.interfaces.OnUpdate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * ДТО для создания/обновления книги
 */
public class InputBookDTO {

    @NotEmpty(groups = {OnCreate.class}, message = "Укажите название книги")
    @Size(groups = {OnCreate.class, OnUpdate.class}, max = 255, message = "Слишком длинное название книги")
    private String name;

    @NotEmpty(groups = {OnCreate.class}, message = "Укажите дату выхода книги")
    private String releaseDate;

    private boolean isPrivate;

    @NotEmpty(groups = OnCreate.class, message = "Укажите автора")
    private String author;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
