package ibf.ssf.booksearch.models;

import jakarta.json.JsonObject;

import static ibf.ssf.booksearch.Constants.*;

public class Book {
    private String id;
    private String title;

    public Book () {}

    public Book(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static Book create(JsonObject jsonObj) {
        Book book = new Book();
        book.setId(jsonObj.getString(SEARCH_ID_FIELD));
        book.setTitle(jsonObj.getString(SEARCH_TITLE_FIELD));
        return book;
    }

}
