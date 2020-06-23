package dev.jmvg.codenation.errorflow.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull(message = "{event.not.null.level}")
    @Enumerated(EnumType.STRING)
    private Level level;

    @Column(name = "description")
    @NotEmpty(message = "{description.not.empty.event}")
    private String description;

    @Column(name = "origin")
    @NotEmpty(message = "{origin.not.empty.event}")
    private String origin;

    @Column(name = "log")
    @NotEmpty(message = "{log.not.empty.event}")
    private String log;

    @Column(name = "quantity")
    @Min(value = 1, message = "{event.quantity.min}")
    private Integer quantity;

    @Column(name = "created_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date createdAt;

    @Column(name = "last_modified_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastModifiedAt;


    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null) setCreatedAt(dateNow());
        if (this.lastModifiedAt == null) setLastModifiedAt(dateNow());
    }

    @PreUpdate
    protected void preUpdate() {
        setLastModifiedAt(dateNow());
    }

    private Date dateNow(){
        return new Date(System.currentTimeMillis()-(3 * 60 * 60 * 1000));
    }
}
