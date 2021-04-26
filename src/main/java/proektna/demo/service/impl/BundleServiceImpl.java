package proektna.demo.service.impl;

import org.springframework.stereotype.Service;
import proektna.demo.model.Book;
import proektna.demo.model.Bundle;
import proektna.demo.model.exceptions.BundleNotFoundException;
import proektna.demo.repository.BundleRepository;
import proektna.demo.service.BookService;
import proektna.demo.service.BundleService;

import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BundleServiceImpl implements BundleService {

    private final BundleRepository bundleRepository;
    private final BookService bookService;


    public BundleServiceImpl(BundleRepository bundleRepository, BookService bookService) {
        this.bundleRepository = bundleRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Bundle> findAll() {
        return this.bundleRepository.findAll();
    }

    public Optional<Bundle> findById(Long id){
      return  this.bundleRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        this.bundleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<Bundle> save(String name,
                               String description,
                               Double price, Integer coupons, String picturePath) {

        return Optional.of(this.bundleRepository.save(new Bundle(name,
                description,price,coupons,picturePath)));
    }


    @Override
    @Transactional
    public Optional<Bundle> edit(Long id, String name,
                                 String description,
                                 Double price, Integer coupons, String picturePath) {

        Bundle bundle= this.bundleRepository.findById(id).orElseThrow(() -> new BundleNotFoundException(id));

        bundle.setName(name);
        bundle.setDescription(description);
        bundle.setPriceBundle(price);
        bundle.setCoupons(coupons);
        bundle.setBundlePicturePath(picturePath);


        return Optional.of(this.bundleRepository.save(bundle));
    }


}
