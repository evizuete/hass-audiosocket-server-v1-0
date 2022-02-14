package es.voiping.hass;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.voiping.hass.controller.Request;
import es.voiping.hass.controller.helpers.ContainersHelper;
import es.voiping.hass.controller.helpers.NetworksHelper;
import org.apache.log4j.Logger;

public class TestMain {
    private static final Logger logger = Logger.getLogger("TestMain");

    public static void main( String[] args ) {
        AudioServer server= new AudioServer();
        server.start(9000);
    }
}
