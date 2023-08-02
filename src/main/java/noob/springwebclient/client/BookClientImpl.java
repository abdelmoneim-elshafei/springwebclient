package noob.springwebclient.client;

import com.fasterxml.jackson.databind.JsonNode;
import noob.springwebclient.model.BookDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class BookClientImpl implements BookClient {
   private final WebClient webClient;
   public static final String BOOK_PATH = "/api/book";
    public BookClientImpl() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();
    }

    @Override
    public Flux<String> listBook() {
        return webClient.get().uri(BOOK_PATH)
                .retrieve().bodyToFlux(String.class);
    }

    @Override
    public Flux<Map> listBookMap() {
        return webClient.get().uri(BOOK_PATH)
                .retrieve().bodyToFlux(Map.class);
    }

    @Override
    public Flux<JsonNode> listBookJsonNode() {
        return webClient.get().uri(BOOK_PATH)
                .retrieve().bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<BookDTO> listBookPojo() {
        return webClient.get().uri(BOOK_PATH)
                .retrieve().bodyToFlux(BookDTO.class);
    }

    @Override
    public Mono<BookDTO> getBookById(String id) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path("api/book/{bookId}").build(id))
                .retrieve().bodyToMono(BookDTO.class);
    }

    @Override
    public Flux<BookDTO> getBookByIsbn(String isbn) {
        return webClient.get().uri(uriBuilder -> uriBuilder
                .path(BOOK_PATH)
                .queryParam("bookIsbn",isbn).build()).retrieve()
                .bodyToFlux(BookDTO.class);
    }

    @Override
    public Mono<BookDTO> createNewBook(BookDTO bookDTO) {
        return webClient.post().uri(BOOK_PATH)
                .body(Mono.just(bookDTO),BookDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> Mono.just(voidResponseEntity
                                .getHeaders().get("Location").get(0)))
                .map(path -> path.split("/")[path.split("/").length - 1])
                .flatMap(this::getBookById);
    }

    @Override
    public Mono<BookDTO> updateBook(BookDTO bookDTO) {
        return webClient.put().uri(uriBuilder -> uriBuilder.path(BOOK_PATH + "/{bookId}")
                .build(bookDTO.getBookId()))
                .body(Mono.just(bookDTO),BookDTO.class)
                .retrieve()
                .toBodilessEntity()
                .flatMap(voidResponseEntity -> getBookById(bookDTO.getBookId()));
    }
}
