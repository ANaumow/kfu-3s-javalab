package ru.naumow.presenters;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.PrintWriter;

// for freemarker
public interface Presenter {

    void put(String key, Object value);

    void render(PrintWriter out) throws IOException;

}
