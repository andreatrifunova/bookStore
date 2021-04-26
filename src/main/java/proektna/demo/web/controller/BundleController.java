package proektna.demo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import proektna.demo.model.Book;
import proektna.demo.model.Bundle;
import proektna.demo.model.dto.BundleDto;
import proektna.demo.service.BookService;
import proektna.demo.service.BundleService;
import proektna.demo.web.controller.FileUploadUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bundles")
public class BundleController {

    private final BundleService bundleService;
    private static final Logger logger1 = LoggerFactory.getLogger("BundleController.class");
    private final BookService bookService;

    public BundleController(BundleService bundleService, BookService bookService) {
        this.bundleService = bundleService;
        this.bookService = bookService;
    }

    @GetMapping
    public String getBundlesPage(Model model) {
        List<Bundle> bundles = this.bundleService.findAll();
        model.addAttribute("bundles", bundles);
        return "bundles";
    }

    @GetMapping("/{id}")
    public String getBundlePage(@PathVariable Long id, Model model) {
        if (this.bundleService.findById(id).isPresent()) {
            Bundle bundle = this.bundleService.findById(id).get();
            model.addAttribute("bundle", bundle);
            return "bundle-view";
        }
        return "redirect:/bundles?error=BundleNotFound";
    }

    @DeleteMapping("/delete-bundle/{id}")
    public String deleteBook(@PathVariable Long id) {
        this.bundleService.deleteById(id);
        return "redirect:/bundles";
    }

    @GetMapping("/edit-bundle/{id}")
    public String editBookPage(@PathVariable Long id, Model model) {
        if (this.bundleService.findById(id).isPresent()) {
            Bundle bundle = this.bundleService.findById(id).get();
            List<Book> bookList=this.bookService.findAll();
            model.addAttribute("bookList",bookList);
            model.addAttribute("bundle",bundle);
            return "add-bundle";
        }
        return "redirect:/bundles?error=BundleNotFound";
    }

    @GetMapping("/add-bundle")
    public String getPage(Model model){
        return "add-bundle";
    }
    @PostMapping("/add-bundle/upload")
    public String uploadImage(@RequestParam(required = false) Long id,
                              @ModelAttribute("bundle") BundleDto bundle,
                              @RequestParam MultipartFile imageFile ) throws IOException {


        String fileName = StringUtils.cleanPath(imageFile.getOriginalFilename());
        Optional<Bundle> bundle1=null;
        if (id != null) {
            bundle1=this.bundleService.edit(bundle.getId(),bundle.getName(),bundle.getDescription(),bundle.getPriceBundle(),bundle.getCoupons(),fileName);
        } else {
            bundle1=this.bundleService.save(bundle.getName(),bundle.getDescription(),bundle.getPriceBundle(),bundle.getCoupons(),fileName);
        }

        String uploadDir = "bundle-photos/" + bundle1.get().getId();

        FileUploadUtil.saveFile(uploadDir, fileName, imageFile);

        return "redirect:/bundles";

    }


}