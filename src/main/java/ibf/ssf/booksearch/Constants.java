package ibf.ssf.booksearch;

import java.util.Arrays;

public class Constants {
    public static final String OPENLIBRARY_API_URL = "http://openlibrary.org/search.json";
    public static final String OPENLIBRARY_WORKS_API_FORMAT_STRING = "https://openlibrary.org/works/%s.json";
    public static final int SEARCH_RESULT_LIMIT = 20;
    public static final String SEARCH_ID_FIELD = "key";
    public static final String SEARCH_TITLE_FIELD = "title";
    public static final String SEARCH_FIELDS = String.join(",", Arrays.asList(SEARCH_ID_FIELD, SEARCH_TITLE_FIELD));

}
