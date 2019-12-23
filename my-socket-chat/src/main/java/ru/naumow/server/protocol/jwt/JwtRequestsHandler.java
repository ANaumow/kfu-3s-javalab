package ru.naumow.server.protocol.jwt;

import ru.naumow.server.dto.Dto;
import ru.naumow.server.dto.ErrorDto;
import ru.naumow.server.protocol.RequestDispatcher;
import ru.naumow.server.protocol.RequestsHandler;

public class JwtRequestsHandler implements RequestsHandler<JwtRequest, JwtResponse> {

    private RequestDispatcher<JwtRequest, JwtResponse> dispatcher;

    public JwtRequestsHandler(JwtRequestsDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public JwtResponse handleRequest(JwtRequest request) {
        String header = request.getCommand();
        JwtResponseWrapper<Dto> response = new JwtResponseWrapper<>(header);
        response.setHeader(header);
        try {
            this.dispatcher.doDispatch(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.setErrorCode(1);
            response.setData(new ErrorDto(e.getMessage()));
        }
        return response;
    }

}
