package proektna.demo.model.dto;

import lombok.Data;
import org.springframework.stereotype.Component;
import proektna.demo.model.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class BundleDto {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double priceBundle;

    private Integer coupons;



    public BundleDto() {
    }

    public BundleDto(String name, String description, Double priceBundle,
                  Integer coupons) {
        this.name = name;
        this.description = description;
        this.priceBundle = priceBundle;
        this.coupons=coupons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPriceBundle() {
        return priceBundle;
    }

    public void setPriceBundle(Double priceBundle) {
        this.priceBundle = priceBundle;
    }

    public Integer getCoupons() {
        return coupons;
    }

    public void setCoupons(Integer coupons) {
        this.coupons = coupons;
    }
}
