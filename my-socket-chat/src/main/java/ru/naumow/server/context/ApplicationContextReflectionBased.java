package ru.naumow.server.context;

import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ApplicationContextReflectionBased implements ApplicationContext {

    private Map<String, Component> components;

    public ApplicationContextReflectionBased() {
        this.components = new HashMap<>();
        Reflections                     reflections   = new Reflections("ru.naumow");
        Set<Class<? extends Component>> allComponents = reflections.getSubTypesOf(Component.class);
        for (Class<? extends Component> componentClass : allComponents) {
            try {
                Component component = componentClass.getConstructor().newInstance();
                components.put(component.getName(), component);
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        List<Component> objectComponents = new ArrayList<>(components.values());
        for (Component objComponent1 : objectComponents) {
            for (Field field : objComponent1.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Component objComponent2 : objectComponents) {
                    if (field.getType().isAssignableFrom(objComponent2.getClass())) {
                        try {
                            field.set(objComponent1, objComponent2);
                        } catch (IllegalAccessException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                }
            }
        }
        System.out.println("context is initialized");
        System.out.println(components);
    }

    public <T> T getComponent(Class<T> componentType, String name) {
        return (T) components.get(name);
    }
}
