package ibf.ssf.booksearch.models;

import jakarta.json.JsonObject;

import static ibf.ssf.booksearch.Constants.*;

public class Book {
    private String id;
    private String title;
    private String description;
    private String excerpt;

    public Book () {}

    public Book(String id) {
        this.id = id;
    }

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExcerpt() {
        return this.excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public static Book create(JsonObject jsonObj) {
        Book book = new Book();
        book.setId(cleanId(jsonObj.getString(SEARCH_ID_FIELD)));
        book.setTitle(jsonObj.getString(SEARCH_TITLE_FIELD));
        return book;
    }

    private static String cleanId(String id) {
        // the openlibrary api returns "key": "/works/OL27448W"
        // but we only need the <works_id> part without the works path
        return id.replace("/works/", "");
    }

}
