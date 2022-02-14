package es.voiping.hass.utils;

import org.apache.log4j.Logger;

import java.util.Arrays;

public class MyStackTrace {
    private static final Logger logger = Logger.getLogger("MyStackTrace");

    public static void print(Exception e) {
        Arrays.stream(e.getStackTrace()).iterator().forEachRemaining(
                element -> {
                    logger.warn(element.toString());
                }
        );
    }
}
