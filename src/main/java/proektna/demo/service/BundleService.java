package proektna.demo.service;


import proektna.demo.model.Book;
import proektna.demo.model.Bundle;

import java.util.List;
import java.util.Optional;

public interface BundleService {

    List<Bundle> findAll();

    Optional<Bundle> findById(Long id);

    void deleteById(Long id);

    Optional<Bundle> save(String name, String description,
                        Double price, Integer coupons, String picturePath);


    Optional<Bundle> edit(Long id, String name, String description,
                        Double price,Integer coupons, String picturePath);

}
