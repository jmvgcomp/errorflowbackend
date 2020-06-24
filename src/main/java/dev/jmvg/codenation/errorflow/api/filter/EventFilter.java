package dev.jmvg.codenation.errorflow.api.filter;

import dev.jmvg.codenation.errorflow.api.model.Level;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class EventFilter {
    private String description;
    private String origin;
    private Level level;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date eventDateFrom;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date eventDateTo;
}
