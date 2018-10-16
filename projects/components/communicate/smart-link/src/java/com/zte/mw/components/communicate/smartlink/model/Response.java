package com.zte.mw.components.communicate.smartlink.model;

import java.io.Serializable;

public interface Response extends Serializable {
    <T> T fetch(String name, Class<T> tClass);

    Successful result();
}
