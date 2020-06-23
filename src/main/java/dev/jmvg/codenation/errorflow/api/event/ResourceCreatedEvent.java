package dev.jmvg.codenation.errorflow.api.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import javax.servlet.http.HttpServletResponse;

public class ResourceCreatedEvent extends ApplicationEvent {
    @Getter
    private final HttpServletResponse response;
    @Getter
    private final Integer id;

    public ResourceCreatedEvent(Object source, HttpServletResponse response, Integer id) {
        super(source);
        this.response = response;
        this.id = id;
    }

}
