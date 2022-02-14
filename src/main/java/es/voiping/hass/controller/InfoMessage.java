package es.voiping.hass.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class InfoMessage {
    private Long id;
    private Long roundtrip;
    private Long fromController;
    private String type;
    private String payload;

    public InfoMessage(Long id, Long timestamp, Long fromController, String type, String payload) {
        this.id = id;
        this.roundtrip = (new Date().getTime()) - timestamp;
        this.fromController = fromController;
        this.type = type;
        this.payload = payload;
    }
}
