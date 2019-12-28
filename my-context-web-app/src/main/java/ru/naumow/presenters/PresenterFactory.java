package ru.naumow.presenters;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import ru.naumow.context.Component;
import ru.naumow.presenters.impl.PresenterImpl;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PresenterFactory {

    private Configuration       configuration;
    private Map<String, String> templates;

    public PresenterFactory(ServletContext servletContext) {
        this.configuration = new Configuration(Configuration.VERSION_2_3_29);
        this.configuration.setServletContextForTemplateLoading(servletContext, "");
        this.configuration.setEncoding(new Locale("RU", "ru"), "UTF-8");
        this.templates = new HashMap<>();
        this.templates.put("sign_in", "./freemarker/sign_in.ftl");
    }

    public Presenter createPresenter(String name) throws IOException {
        String templatePath = this.templates.get(name);
        Template template = this.configuration.getTemplate(templatePath);
        return new PresenterPro(template);
    }

    public static PresenterImpl signInPresenter(Writer out) {
        return new PresenterImpl(out, new Configuration(Configuration.VERSION_2_3_29), TemplatesPaths.SIGN_IN);
    }

}
