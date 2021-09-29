package com.dvoraninovich.establishment.controller.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeTag extends TagSupport {
    private LocalDateTime localDateTime;

    public void setTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            String date = localDateTime.format(DateTimeFormatter.ofPattern ( "dd-MM-yyyy" ));
            LocalTime time = localDateTime.toLocalTime();
            StringBuilder stringBuilder = new StringBuilder(date).append(" ").append(time);
            pageContext.getOut().write(stringBuilder.toString());
        } catch (IOException e) {
            throw new JspException("Time presentation error " + e.getMessage());
        }
        return SKIP_BODY;
    }
}
