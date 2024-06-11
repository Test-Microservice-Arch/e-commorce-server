package com.example.productserver.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
