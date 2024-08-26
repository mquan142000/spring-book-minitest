package com.example.springbookminitest.controller;

import com.example.springbookminitest.model.Book;
import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.service.IBookService;
import com.example.springbookminitest.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    @Value("${file-upload}")
    private String upload;

    @Autowired
    private IBookService bookService;

    @Autowired
    private ITypeService typeService;

    @ModelAttribute("types")
    public Iterable<Type> listTypes() {
        return typeService.findAll();
    }

    @GetMapping
    public ModelAndView listBook() {
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView listBooksSearch(@RequestParam("search") Optional<String> search, Pageable pageable) {
        Page<Book> books;
        if (search.isPresent()) {
            books = bookService.findAllByNameContaining(pageable, search.get());
        } else {
            books = bookService.findAll(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("book") Book book,
                         RedirectAttributes redirectAttributes) {
        bookService.save(book);
        redirectAttributes.addFlashAttribute("message", "Create new book successfully");
        return "redirect:/books";
    }

    @PostMapping("/create1")
    public String create(@RequestParam String name,
                         @RequestParam String author,
                         @RequestParam double price,
                         @RequestParam String image,
                         @RequestParam Long typeId) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setPrice(price);
        book.setImage(image);
        Type t = typeService.findById(typeId).get();
        book.setType(t);
        return "redirect:/books";
    }


    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/book/update");
            modelAndView.addObject("book", book.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("book") Book book,
                         RedirectAttributes redirectAttributes) {
        bookService.save(book);
        redirectAttributes.addFlashAttribute("message", "Update book successfully");
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirectAttributes) {
        bookService.remove(id);
        redirectAttributes.addFlashAttribute("message", "Delete book successfully");
        return "redirect:/books";
    }
}
