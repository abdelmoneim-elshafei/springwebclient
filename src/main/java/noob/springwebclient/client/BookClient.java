package noob.springwebclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import noob.springwebclient.model.BookDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface BookClient {

    Flux<String> listBook();
    Flux<Map> listBookMap();
    Flux<JsonNode> listBookJsonNode();

    Flux<BookDTO> listBookPojo();
    Mono<BookDTO> getBookById(String id);
    Flux<BookDTO> getBookByIsbn(String isbn);

    Mono<BookDTO> createNewBook(BookDTO bookDTO);
    Mono<BookDTO> updateBook(BookDTO bookDTO);
}

