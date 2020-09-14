/**
 * 
 */
package com.eventsourcing.eventsourcinglib.util;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import lombok.extern.log4j.Log4j2;

/**
 * @author kaihe
 *
 */ 

@Log4j2
public class CommonUtil {

    public static LocalDateTime stringToLocalDateTime(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        if (StringUtils.isEmpty(time)) {
            return LocalDateTime.now();
        } else {
            return LocalDateTime.parse(time, formatter);
        }
    }

    public static String localDateTimeToString(LocalDateTime time) {
        if (null == time) {
            return "";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return time.format(formatter);
        }
    }
    
    public static String replaceWithParam(String text, String param) {
        return param != null ? text.replaceFirst("\\{.*?\\}", param) : text;
    }
    
    public static String replaceWithParams(String text, List<String> params) {
        if (params == null) {
            return text;
        }
        
        String result = text;
        for (int i=0; i < params.size(); i++) {
            result = result.replaceFirst("\\{.*?\\}", params.get(i));
        }
        return result;
    }
    
    public static String loadFileContent(String fileName) {
        String content = "";
        try {
            File file = ResourceUtils.getFile("classpath:" + fileName);
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        } 
        return content;
    }
}

