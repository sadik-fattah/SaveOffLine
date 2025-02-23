package com.guercifzone.saveoffline.Models;

public class WebPage {
    private int id;
    private String url;
    private String htmlContent;

    public WebPage(String htmlContent, String url, int id) {
        this.htmlContent = htmlContent;
        this.url = url;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
