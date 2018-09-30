package com.zte.mw.components.tools.environment;

public interface ResourceProvider {
    <T> T get(String name, Class<T> tClass, Creator<T> creator);

    <T> T get(String name, Class<T> tClass);
}
