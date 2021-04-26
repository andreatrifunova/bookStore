package proektna.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Bundle {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double priceBundle;

    @Basic(fetch = FetchType.LAZY)
    private String bundlePicturePath;

    private Integer coupons;

    @Transient
    public String getBundlePicturePath() {
        if (bundlePicturePath== null || id == null) return null;

        return "/bundle-photos/" + id + "/" + bundlePicturePath;
    }

    public Bundle() {
    }

    public Bundle(String name, String description, Double priceBundle,
                  Integer coupons,String bundlePicturePath) {
        this.name = name;
        this.description = description;
        this.priceBundle = priceBundle;
        this.bundlePicturePath = bundlePicturePath;
        this.coupons=coupons;
    }
}
