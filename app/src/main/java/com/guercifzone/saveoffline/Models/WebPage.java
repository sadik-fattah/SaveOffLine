package com.guercifzone.saveoffline.Models;

public class WebPage {

    private String url;
    private String htmlContent;

    public WebPage(String htmlContent, String url) {
        this.htmlContent = htmlContent;
        this.url = url;

    }


    public void setUrl(String url) {
        this.url = url;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getUrl() {
        return url;
    }

    public String getHtmlContent() {
        return htmlContent;
    }
}
