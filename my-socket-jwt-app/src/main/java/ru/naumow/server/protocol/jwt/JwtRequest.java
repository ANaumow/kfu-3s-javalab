package ru.naumow.server.protocol.jwt;

import ru.naumow.server.protocol.Request;
import ru.naumow.server.protocol.jwt.token.DecodedJwtToken;

public interface JwtRequest extends Request {

    DecodedJwtToken getDecodedToken();

}
