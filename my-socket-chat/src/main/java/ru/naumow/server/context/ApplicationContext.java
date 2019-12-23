package ru.naumow.server.context;

public interface ApplicationContext {
    <T> T getComponent(Class<T> componentType, String name);
}
