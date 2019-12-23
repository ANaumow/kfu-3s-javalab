package ru.naumow.server.protocol;

import ru.naumow.server.dto.Dto;

public interface Response {
/*
    String getHeader();

    D getPayload();

    int getError();

    String toJson();*/

    <D extends Dto>void setData(D data);

}
