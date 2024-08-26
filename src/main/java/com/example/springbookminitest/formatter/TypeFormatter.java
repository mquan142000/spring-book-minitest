package com.example.springbookminitest.formatter;

import com.example.springbookminitest.model.Type;
import com.example.springbookminitest.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Optional;

@Component
public class TypeFormatter implements Formatter<Type> {

    private final ITypeService typeService;

    @Autowired
    public TypeFormatter(ITypeService typeService) {
        this.typeService = typeService;
    }


    @Override
    public Type parse(String text, Locale locale) {
        Optional<Type> typeOptional = typeService.findById(Long.parseLong(text));
        return typeOptional.orElse(null);
    }

    @Override
    public String print(Type object, Locale locale) {
        return "[" + object.getId() + ", " + object.getName() + "]";
    }
}
