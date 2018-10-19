package com.zte.mw.components.communicate.smartlink.model;

import java.io.Serializable;
import java.util.List;

public interface Response<T> extends Serializable {
    List<T> getContent();

    Boolean isSuccess();

    Exception ex();
}
