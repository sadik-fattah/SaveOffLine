package com.guercifzone.saveoffline.Models;

public class WebPage {
    private String url;
    private String htmlContent;

    public WebPage(String htmlContent, String url) {
        this.htmlContent = htmlContent;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }
}
