package com.sabana.event_sourcing_poc.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum States {

    CREATED(null, "PAYMENT"),
    PAYMENT("CREATED", "DISPATCH"),
    DISPATCH("PAYMENT", "COMPLETE"),
    COMPLETE("DISPATCH", null);

    private final String previousState;

    private final String nextState;

    public static States fromString(String text) {
        return valueOf(text.toUpperCase());
    }


}
