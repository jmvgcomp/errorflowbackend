package dev.jmvg.codenation.errorflow.api.utils;

import dev.jmvg.codenation.errorflow.api.model.Level;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EnumConverter implements Converter<String, Level> {

    @Override
    public Level convert(String s) {
        try {
            return Level.valueOf(s.toUpperCase());
        } catch(Exception e) {
            return Level.ERROR;
        }
    }
}