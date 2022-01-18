package ibf.ssf.booksearch;

import java.util.Arrays;

public class Constants {
    public static final String OPENLIBRARY_API_URL = "http://openlibrary.org/search.json";
    public static final String OPENLIBRARY_WORKS_API_FORMAT_STRING = "https://openlibrary.org/works/%s.json";
    public static final String OPENLIBRARY_COVER_IMAGE_FORMAT_STRING = "https://covers.openlibrary.org/b/id/%s-M.jpg";
    public static final int SEARCH_RESULT_LIMIT = 20;
    // these are the fields used by openlibrary
    public static final String SEARCH_ID_FIELD = "key";
    public static final String SEARCH_TITLE_FIELD = "title";
    public static final String DESCRIPTION_FIELD = "description";
    public static final String EXCERPT_FIELD = "excerpt";
    // these are our custom fields
    public static final String COVER_ID_FIELD = "cover_id";
    public static final String WORKS_ID_FIELD = "works_id";
    public static final String SEARCH_FIELDS = String.join(",", Arrays.asList(SEARCH_ID_FIELD, SEARCH_TITLE_FIELD));
    public static final long REDIS_CACHE_DURATION_MINUTES = 10L;
}
