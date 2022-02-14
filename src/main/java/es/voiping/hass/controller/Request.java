package es.voiping.hass.controller;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonPropertyOrder({"id", "timestamp", "toController", "details"})
public class Request {
    private Long id;
    private Long timestamp;
    private String toController;

    @JsonView
    private List<RequestDetail> details;
}
