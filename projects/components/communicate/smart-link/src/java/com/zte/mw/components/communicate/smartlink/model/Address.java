package com.zte.mw.components.communicate.smartlink.model;

public interface Address<T extends Message> {
    void on(T message);
}
