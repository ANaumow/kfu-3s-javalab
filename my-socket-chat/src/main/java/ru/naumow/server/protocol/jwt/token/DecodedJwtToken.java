package ru.naumow.server.protocol.jwt.token;

public interface DecodedJwtToken {
    String getSubject();
    String getRole();
    String getType();
    String getAlgorithm();
}
