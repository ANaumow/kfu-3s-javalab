package ru.naumow.server.controllers;

//import ru.naumow.server.context.Component;
import ru.naumow.context.Component;
import ru.naumow.server.dto.TokenDto;
import ru.naumow.server.dto.UserDto;
import ru.naumow.server.protocol.jwt.JwtRequest;
import ru.naumow.server.protocol.jwt.JwtResponse;
import ru.naumow.server.protocol.jwt.token.JwtTokenCoder;
import ru.naumow.server.protocol.jwt.token.JwtTokenCoderAuth0Based;
import ru.naumow.server.services.SignInService;

public class AuthController implements Component {

    private SignInService signInService;

    public void signIn(JwtRequest request, JwtResponse response) {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        UserDto userDto = signInService.signIn(login, password);
        String sub = userDto.getId().toString();
        String rol = userDto.getRole();
        JwtTokenCoder tokenCoder = new JwtTokenCoderAuth0Based("i like pizza");
        String token = tokenCoder.encode(sub, rol);
        response.setData(new TokenDto(token));
    }

    @Override
    public String getName() {
        return "authController";
    }

}
