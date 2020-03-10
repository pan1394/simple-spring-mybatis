package com.yilin.function.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Date;
 
public final class DateUtil {

    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
    public static final String DATE_FORMAT_SEPARATE_YYYYMMDDHHMMSS = "yyyy-MM-dd HHmmssSSS";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

 
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";

   
    private static final String DATE_FORMAT_YYMMDD = "yyMMdd";

   
    private static final String DATE_FORMAT_YYYYMM = "yyyyMM";

 
    private static final String TIME_FORMAT_HHMMSS = "HHmmss";
 
    private DateUtil() {
    }

   
    public static Date getSystemNitizi() {
        try {
            return (new SimpleDateFormat(DateUtil.DATE_FORMAT_SEPARATE_YYYYMMDDHHMMSS)
                    .parse(DateUtil.getNow("yyyy-MM-dd HHmmssSSS")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    
    public static String getNow(final String format) {
        return DateUtil.format(LocalDateTime.now(), format);
    }

    
    public static String format(final LocalDateTime date, final String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT);
        return formatter.format(date);
    }
}
