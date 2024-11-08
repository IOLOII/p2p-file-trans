package org.example.p2p.p2pfiletransfx.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    public static String getFormattedTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "[" + now.format(formatter) + "] ";
    }
}
