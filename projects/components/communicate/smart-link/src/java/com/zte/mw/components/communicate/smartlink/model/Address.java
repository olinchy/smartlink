package com.zte.mw.components.communicate.smartlink.model;

public interface Address extends MsgService {
    void bind(Service service);

    Address publish(SmartLinkNode smartLinkNode);
}
