package proektna.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String surname;

    private Date birthDate;

    private String country;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    public Author(){}

    public Author(String name, String surname,Date birthDate,String country) {
        this.name = name;
        this.surname = surname;
        this.birthDate=birthDate;
        this.books=new ArrayList<>();
        this.country=country;
    }

}
