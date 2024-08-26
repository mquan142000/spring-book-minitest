package com.example.springbookminitest.controller;

import com.example.springbookminitest.model.Book;
import com.example.springbookminitest.model.DTO.ICountBook;
import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.service.impl.BookService;
import com.example.springbookminitest.service.impl.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/types")
public class TypeController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BookService bookService;

    @GetMapping
    private ModelAndView listType() {
        ModelAndView modelAndView = new ModelAndView("/type/list");
        Iterable<Type> types = typeService.findAll();
        modelAndView.addObject("types", types);
        Iterable<ICountBook> type1 = typeService.getCountBooks();
        modelAndView.addObject("type1", type1);
        return modelAndView;
    }

    @GetMapping("/create")
    private ModelAndView createForm() {
        ModelAndView modelAndView = new ModelAndView("/type/create");
        modelAndView.addObject("type", new Type());
        return modelAndView;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("type") Type type,
                         RedirectAttributes redirectAttributes) {
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Create new type successfully");
        return "redirect:/types";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Type> type = typeService.findById(id);
        if (type.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/type/update");
            modelAndView.addObject("type", type.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        typeService.deleteTypeById(id);
        return "redirect:/types";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("type") Type type,
                         RedirectAttributes redirectAttributes) {
        typeService.save(type);
        redirectAttributes.addFlashAttribute("message", "Update type successfully");
        return "redirect:/types";
    }

    @GetMapping("/view-type/{id}")
    public ModelAndView viewType(@PathVariable("id") Long id) {
        Optional<Type> typeOptional = typeService.findById(id);
        if (!typeOptional.isPresent()) {
            return new ModelAndView("/error_404");
        }

        Iterable<Book> books = bookService.findAllByType(typeOptional.get());
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/demo")
    public ModelAndView getDemo() {
        ModelAndView modelAndView = new ModelAndView("/type/list");
        Iterable<ICountBook> types = typeService.getCountBooks();
        modelAndView.addObject("ts", types);
        return modelAndView;
    }
}
