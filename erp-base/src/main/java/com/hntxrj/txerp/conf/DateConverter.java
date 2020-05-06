package com.hntxrj.txerp.conf;

import com.hntxrj.txerp.core.util.SimpleDateFormatUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        String pattern = source.length() == 10 ? "yyyy-MM-dd" : "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat format = SimpleDateFormatUtil.getSimpleDataFormat(pattern);

        try {
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}