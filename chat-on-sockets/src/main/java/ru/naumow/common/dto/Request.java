package ru.naumow.common.dto;

public class Request {

    private String  header;
    private Payload payload;

    public String getHeader() {
        return header;
    }

    public Request setHeader(String header) {
        this.header = header;
        return this;
    }

    public Payload getPayload() {
        return payload;
    }

    public Request setPayload(Payload payload) {
        this.payload = payload;
        return this;
    }
}
