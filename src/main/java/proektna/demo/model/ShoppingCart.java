package proektna.demo.model;

import lombok.Data;
import proektna.demo.model.enumerations.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dateCreated;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<Book> bookList;

    @Enumerated(EnumType.STRING)
    private Status status;

    public ShoppingCart() {
    }

    public ShoppingCart(User user) {
        this.dateCreated = LocalDateTime.now();
        this.user = user;
        this.bookList = new ArrayList<>();
        this.status = Status.CREATED;
    }

}
