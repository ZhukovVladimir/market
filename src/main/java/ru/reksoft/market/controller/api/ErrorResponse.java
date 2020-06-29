package ru.reksoft.market.controller.api;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    public ErrorResponse(List<String> message) {
        this.message = message;
    }

    public ErrorResponse(String message) {
        this.message = new ArrayList<>();
        this.message.add(message);
    }

    private List<String> message;
}
