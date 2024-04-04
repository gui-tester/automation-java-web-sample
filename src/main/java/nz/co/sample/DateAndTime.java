package nz.co.sample;

import com.mdimension.jchronic.Chronic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by gmendonca on 26/08/2019.
 */

public class DateAndTime {
    private static final DateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private DateAndTime() {
    }

    public static String formatDate(String chronic, DateFormat dateFormat) {
        return dateFormat.format(Chronic.parse(chronic).getBeginCalendar().getTime());
    }

    public static String chronicDateAsIso( String chronic ) {
        return isoDateFormat.format(Chronic.parse(chronic).getBeginCalendar().getTime());
    }

    public static String chronicDateAsNz( String chronic ) {
        return dateFormat.format(Chronic.parse(chronic).getBeginCalendar().getTime());
    }
}
