package ru.naumow.common.dto;

public class Request {

    private String header;
    private Payloader payloader;

    public String getHeader() {
        return header;
    }

    public Request setHeader(String header) {
        this.header = header;
        return this;
    }

    public Payloader getPayloader() {
        return payloader;
    }

    public Request setPayloader(Payloader payloader) {
        this.payloader = payloader;
        return this;
    }
}
