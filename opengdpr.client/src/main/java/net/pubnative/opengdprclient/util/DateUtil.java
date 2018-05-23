package net.pubnative.opengdprclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
    public static String getRFC3339DateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd'T'h:m:ssZZZZZ", Locale.ENGLISH).format(date);
    }

    public static String getRFC3339DateString(long time) {
        return getRFC3339DateString(new Date(time));
    }
}
