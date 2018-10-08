package com.zte.mw.components.communicate.smartlink.model;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;

public interface MsgService {
    void on(Message msg, final Callback callback);

    Response on(Message msg) throws SmartLinkException;
}
