package com.zte.mw.components.communicate.smartlink.model;

public interface Address {
    void on(Message message, final Callback callback);

    void publish();

    Response on(Message msg);
}
