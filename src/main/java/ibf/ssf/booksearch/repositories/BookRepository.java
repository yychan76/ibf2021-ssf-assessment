package ibf.ssf.booksearch.repositories;

import static ibf.ssf.booksearch.Constants.REDIS_CACHE_DURATION_MINUTES;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.logging.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import ibf.ssf.booksearch.models.Book;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Repository
public class BookRepository {
    private final Logger logger = Logger.getLogger(BookRepository.class.getName());

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void save(String worksId, Book book) {
        redisTemplate.opsForValue()
            .set(worksId, book.toJson().toString(), Duration.ofMinutes(REDIS_CACHE_DURATION_MINUTES));
    }

    public Optional<Book> get(String worksId) {
        Optional<String> opt = Optional.ofNullable(redisTemplate.opsForValue().get(worksId));
        if (opt.isPresent()) {
            logger.info("Reading from redis cache: %s".formatted(opt.get().toString()));
            JsonObject jsonObj = parseJsonObject(opt.get());
            return Optional.of(Book.create(jsonObj));
        }
        return Optional.empty();
    }

    private JsonObject parseJsonObject(String jsonString) {
        try (InputStream inputStream = new ByteArrayInputStream(jsonString.getBytes());
             JsonReader reader = Json.createReader(inputStream)) {
            // parse the json string and return a JsonObject
            return reader.readObject();
        } catch (JsonProcessingException jpe) {
            logger.severe("Unable to parse JSON: %s".formatted(jpe.getMessage()));
        } catch (IOException ioe) {
            logger.severe("Unable to read string: %s".formatted(ioe.getMessage()));
        }
        // return empty JsonObject
        return Json.createObjectBuilder().build();
    }
}
