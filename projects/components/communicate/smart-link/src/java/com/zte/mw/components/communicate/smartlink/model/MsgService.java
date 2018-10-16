package com.zte.mw.components.communicate.smartlink.model;

import java.io.Serializable;

public interface MsgService<T extends Request<R>, R extends Response> extends Service, Serializable {
    R on(T msg);
}
