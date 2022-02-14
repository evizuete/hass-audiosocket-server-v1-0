package es.voiping.hass.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDetail {
    private String object;
    private String method;
    private String arguments;
}
