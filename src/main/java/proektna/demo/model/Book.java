package proektna.demo.model;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Book {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private Long id;

    private String name;

    private String izdavackaKukja;

    @Column(length = 1000)
    private String description;

    //tuka da se vidi vrskava, ne sum sigurna deka e vaka
    @ManyToOne
    private Author author;

    private Double price;

    private String zanr;

    private Integer votes;

    @Basic(fetch = FetchType.LAZY)
    private String picturePath;

    private Integer couponsForBook;

    @Transient
    public String getPicturePath() {
        if (picturePath== null || id == null) return null;

        return "/book-photos/" + id + "/" + picturePath;
    }

    public Book() {
    }

  public Book(String name, Author author ,String izdavac,String description,
              String zanr,Double price,Integer coupons,String picturePath) {
    this.name = name;
    this.author=author;
    this.izdavackaKukja=izdavac;
    this.price=price;
    this.zanr=zanr;
    this.picturePath=picturePath;
    this.description=description;
    this.couponsForBook=coupons;
    votes=0;
    }
}

