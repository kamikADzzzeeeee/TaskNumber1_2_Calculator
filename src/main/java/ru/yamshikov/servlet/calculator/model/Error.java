package ru.yamshikov.servlet.calculator.model;

import lombok.Builder;

@Builder
public class Error {

    private int code;
    private String message;
    private String time;

}
