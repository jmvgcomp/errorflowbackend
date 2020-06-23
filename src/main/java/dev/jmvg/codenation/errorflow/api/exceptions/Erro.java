package dev.jmvg.codenation.errorflow.api.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Erro{
    private String userMessage;
    private String developerMessage;
}