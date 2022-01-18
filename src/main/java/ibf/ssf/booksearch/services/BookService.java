package ibf.ssf.booksearch.services;

import static ibf.ssf.booksearch.Constants.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import ibf.ssf.booksearch.models.Book;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class BookService {
    private final Logger logger = Logger.getLogger(BookService.class.getName());

    public List<Book> search(String searchTerm) {
        String title = getQueryString(searchTerm);
        String url = UriComponentsBuilder
                        .fromUriString(OPENLIBRARY_API_URL)
                        .queryParam("title", title)
                        .queryParam("fields", SEARCH_FIELDS)
                        .queryParam("limit", SEARCH_RESULT_LIMIT)
                        .toUriString();

        RequestEntity<Void> request = RequestEntity.get(url).build();
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> response;

        try {
            response = template.exchange(request, String.class);
            logger.info(response.getStatusCode().toString());
            logger.info(response.getBody().toString());
        } catch (HttpClientErrorException e) {
            logger.severe(e.getResponseBodyAsString());
            return Collections.emptyList();
        }

        final Optional<String> bodyOpt = Optional.ofNullable(response.getBody());
        if (bodyOpt.isEmpty()) {
            return Collections.emptyList();
        }

        try (InputStream inputStream = new ByteArrayInputStream(bodyOpt.get().getBytes());
            final JsonReader reader = Json.createReader(inputStream)) {
            final JsonObject result = reader.readObject();
            final JsonArray docs = result.getJsonArray("docs");
            return docs.stream()
                .filter(JsonObject.class::isInstance)
                .map(JsonObject.class::cast)
                .map(Book::create)
                .toList();

        } catch (IOException ioe) {
            logger.severe("Unable to read response: %s".formatted(ioe.getMessage()));
        }
        // if something wrong happened, still return an empty list
        return Collections.emptyList();
    }

    private String getQueryString(String title) {
        // replace multiple spaces between with single + to send as get query string
        return String.join("+", title.trim().split("\\s+"));
    }
}
