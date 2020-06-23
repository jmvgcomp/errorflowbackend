package dev.jmvg.codenation.errorflow.api.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "level")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Level {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
