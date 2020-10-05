package es.spin21.bluu.streamer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatesHelper {

    public static Date getDateFromString(String dateStr, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(dateStr);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
