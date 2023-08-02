package noob.springwebclient.client;

import noob.springwebclient.model.BookDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookClientImplTest {
   @Autowired
   BookClient bookClient;
    @Test
    void updateBook(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String newName = "Java";
        bookClient.listBookPojo()
                .next()
                .doOnNext(bookDTO -> bookDTO.setBookName(newName))
                .flatMap(dto -> bookClient.updateBook(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void createNewBook(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        BookDTO bookDTO = BookDTO.builder()
                .bookIsbn("3832-43434-12123")
                .price(new BigDecimal("30.3"))
                .bookName("c++")
                .version(2)
                .quantityOnHand(300)
                .publisher("orill")
                .build();

        bookClient.createNewBook(bookDTO).subscribe(
                bookDTO1 -> {
                    System.out.println(bookDTO1.toString());
                    atomicBoolean.set(true);
                }
        );

        await().untilTrue(atomicBoolean);
    }
    @Test
    void getById(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        bookClient.listBookPojo()
                .flatMap(dto -> bookClient.getBookById(dto.getBookId()))
                        .subscribe(byIdDto -> {
                            System.out.println(byIdDto.getBookName());
                            atomicBoolean.set(true);
                        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void getBookByIsbn(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        final String isbn = "978-3-16-148410-0";
        bookClient.getBookByIsbn(isbn)
                        .subscribe(dto ->{
                            System.out.println(dto.toString());
                            atomicBoolean.set(true);
                        });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void listBook(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        bookClient.listBook().subscribe(response ->{
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void listBookMap(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        bookClient.listBookMap().subscribe(response ->{
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void listBookJsonNode(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        bookClient.listBookJsonNode().subscribe(response ->{
            System.out.println(response.toPrettyString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }
    @Test
    void listBookPojo(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        bookClient.listBookPojo().subscribe(response ->{
            System.out.println(response.getBookName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

}