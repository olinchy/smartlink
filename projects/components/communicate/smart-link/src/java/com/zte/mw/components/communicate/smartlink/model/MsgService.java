package com.zte.mw.components.communicate.smartlink.model;

import java.io.Serializable;

import com.zte.mw.components.communicate.smartlink.exception.SmartLinkException;

public interface MsgService extends Service, Serializable {
    Response on(Message msg) throws SmartLinkException;
}
