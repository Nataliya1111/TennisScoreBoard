package com.nataliya.util;

public class JspUtil {

    private JspUtil(){
    }

    public static String getPath(String jspName){
        String path = "/WEB-INF/jsp/%s.jsp";
        return String.format(path, jspName);
    }
}
