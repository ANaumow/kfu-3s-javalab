package ru.naumow.common.dto;

public abstract class CommandPayload<T> extends Payload {

    private String command;

    public String getCommand() {
        return command;
    }

    public T setCommand(String command) {
        this.command = command;
        return (T)this;
    }
}
