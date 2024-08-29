package com.example.springbookminitest.controller;

import com.example.springbookminitest.model.Book;
import com.example.springbookminitest.model.BookForm;
import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.service.IBookService;
import com.example.springbookminitest.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.security.Principal;

@Controller
@RequestMapping("/books")
public class BookController {
    @Value("${file-upload}")
    private String upload;

    @Autowired
    private IBookService bookService;

    @Autowired
    private ITypeService typeService;

    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("/book/list");
    }

//    @GetMapping("/admin")
//    public ModelAndView admin(){
//        SecurityContext context = SecurityContextHolder.getContext();
//        System.out.println(context.getAuthentication().getName());
//        return new ModelAndView("/admin");
//    }

    @ModelAttribute("types")
    public Iterable<Type> listTypes() {
        return typeService.findAll();
    }

    @GetMapping("")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Book> bookPage = bookService.findAll(pageable);
        model.addAttribute("bookList", bookPage);

        return "/book/list";
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("book", new Book());
        return "/book/create";
    }

    @PostMapping("/save")
    public String save(BookForm bookForm) {
        MultipartFile file = bookForm.getImage();

        String fileName = file.getOriginalFilename();

        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Book book = new Book();
            book.setId(bookForm.getId());
            book.setName(bookForm.getName());
            book.setAuthor(bookForm.getAuthor());
            book.setPrice(bookForm.getPrice());
            book.setImage(fileName);
            book.setType(bookForm.getType());
            bookService.save(book);
            return "redirect:/books";
        }
    }

//    @PostMapping("/create1")
//    public String create(@RequestParam String name,
//                         @RequestParam String author,
//                         @RequestParam double price,
//                         @RequestParam String image,
//                         @RequestParam Long typeId) {
//        Book book = new Book();
//        book.setName(name);
//        book.setAuthor(author);
//        book.setPrice(price);
//        book.setImage(image);
//        Type t = typeService.findById(typeId).get();
//        book.setType(t);
//        return "redirect:/books";
//    }


    @GetMapping("/{id}/edit")
    public String showFormEdit(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        return "/book/update";
    }

    @PostMapping("/update")
    public String update(BookForm bookForm) {
        MultipartFile file = bookForm.getImage();

        String fileName = file.getOriginalFilename();

        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Book book = new Book();
            book.setId(bookForm.getId());
            book.setName(bookForm.getName());
            book.setAuthor(bookForm.getAuthor());
            book.setPrice(bookForm.getPrice());
            book.setType(bookForm.getType());
            book.setImage(fileName);
            bookService.save(book);
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}/delete")
    public String showFormDelete(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.findById(id));
        Iterable<Type> types = typeService.findAll();
        model.addAttribute("types", types);
        return "/book/delete";
    }

    @PostMapping("/delete")
    public String delete(Long id, RedirectAttributes redirect) {
        bookService.remove(id);
        redirect.addFlashAttribute("message", "Removed book successfully");
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         Model model) {
        Page<Book> bookList = bookService.findAllByNameContaining(PageRequest.of(page, size), name);
        model.addAttribute("bookList", bookList);
        return "/book/list";
    }

}
