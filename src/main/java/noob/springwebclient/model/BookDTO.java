package noob.springwebclient.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
public class BookDTO {

    private String bookId;
    private String bookName;
    private String bookIsbn;
    private String publisher;
    private Integer version;
    private BigDecimal price;
    private Integer quantityOnHand;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;

    public BookDTO(String bookId, String bookName, String bookIsbn, String publisher, Integer version, BigDecimal price, Integer quantityOnHand, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookIsbn = bookIsbn;
        this.publisher = publisher;
        this.version = version;
        this.price = price;
        this.quantityOnHand = quantityOnHand;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
    }
}

