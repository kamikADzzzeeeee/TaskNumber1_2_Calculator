package ru.yamshikov.servlet.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Example {
    private String x;
    private String y;
    private String operation;
}
