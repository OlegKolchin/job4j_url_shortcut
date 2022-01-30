package ru.job4j.model;

public class UrlDTO {
    private String url;
    private int calls;

    public static UrlDTO of(String url, int calls) {
        UrlDTO urlDTO = new UrlDTO();
        urlDTO.url = url;
        urlDTO.calls = calls;
        return  urlDTO;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCalls() {
        return calls;
    }

    public void setCalls(int calls) {
        this.calls = calls;
    }
}
