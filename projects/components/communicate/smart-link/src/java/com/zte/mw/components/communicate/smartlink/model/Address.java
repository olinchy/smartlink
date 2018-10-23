package com.zte.mw.components.communicate.smartlink.model;

public interface Address<T extends Request<R>, R extends Response> extends MsgService<T, R> {
    void bind(Service service);

    Address publish(SmartLinkNode smartLinkNode);

    boolean equals(Object obj);
}
