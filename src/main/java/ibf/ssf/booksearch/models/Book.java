package ibf.ssf.booksearch.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import static ibf.ssf.booksearch.Constants.*;

public class Book {
    private String id;
    private String title;
    private String description;
    private String excerpt;
    private String coverId;
    private String coverUrl;
    private boolean cached = false;

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

    public String getCoverId() {
        return this.coverId;
    }

    public void setCoverId(String coverId) {
        this.coverId = coverId;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return OPENLIBRARY_COVER_IMAGE_FORMAT_STRING.formatted(this.coverId);
    }

    public boolean isCached() {
        return this.cached;
    }

    public boolean getCached() {
        return this.cached;
    }

    public void setCached(boolean cached) {
        this.cached = cached;
    }


    public static Book create(JsonObject jsonObj) {
        Book book = new Book();
        // these should be populated when the search results page is requested
        book.setId(cleanId(jsonObj.get(SEARCH_ID_FIELD) != null ? jsonObj.getString(SEARCH_ID_FIELD) : ""));
        book.setTitle(jsonObj.get(SEARCH_TITLE_FIELD) != null ? jsonObj.getString(SEARCH_TITLE_FIELD) : "");
        // these fields are not populated until the book details page is requested
        book.setDescription(jsonObj.get(DESCRIPTION_FIELD) != null ? jsonObj.getString(DESCRIPTION_FIELD) : "");
        book.setExcerpt(jsonObj.get(EXCERPT_FIELD) != null ? jsonObj.getString(EXCERPT_FIELD) : "");
        book.setCoverId(jsonObj.get(COVER_ID_FIELD) != null ? jsonObj.getString(COVER_ID_FIELD) : "");
        return book;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
                    .add(WORKS_ID_FIELD, getId() != null ? getId() : "")
                    .add(SEARCH_TITLE_FIELD, getTitle() != null ? getTitle() : "")
                    .add(DESCRIPTION_FIELD, getDescription() != null ? getDescription() : "")
                    .add(EXCERPT_FIELD, getExcerpt() != null ? getExcerpt() : "")
                    .add(COVER_ID_FIELD, getCoverId() != null ? getCoverId() : "")
                    .build();
    }

    private static String cleanId(String id) {
        // the openlibrary api returns "key": "/works/OL27448W"
        // but we only need the <works_id> part without the works path
        return id.replace("/works/", "");
    }

}
