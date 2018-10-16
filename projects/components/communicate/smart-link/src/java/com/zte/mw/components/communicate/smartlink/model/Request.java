package com.zte.mw.components.communicate.smartlink.model;

import java.io.Serializable;

public interface Request<T extends Response> extends Serializable {
    String key();

    default Class<T> getRespClass() {
        return (Class<T>) getClass().getGenericSuperclass().getClass();
    }
}
