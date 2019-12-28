package ru.naumow.presenters;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public abstract class AbstractPresenter<T extends AbstractPresenter> implements Presenter {
    private Template            template;
    private Map<String, Object> root;
    private Writer              out;

    protected AbstractPresenter(Writer out, Configuration configuration, String templatePath) {
        this.out = out;
        this.root = new HashMap<>();
        try {
            configuration.setEncoding(new Locale("RU", "ru"), "UTF-8");
            template = configuration.getTemplate(templatePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void put(String key, Object object) {
        root.put(key, object);
    }

    public void render() {
        try {
            template.process(root, out);
            out.close();
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }
    }
}
