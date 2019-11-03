package ru.naumow.server.services;

import ru.naumow.server.services.impl.Auth;
import ru.naumow.server.services.impl.Messages;
import ru.naumow.server.services.impl.Users;

public class ServiceFactory {

    public static Auth auth(){
        return new Auth();
    }

    public static Messages messages() {
        return new Messages();
    }

    public static Users users() {
        return new Users();
    }

}
