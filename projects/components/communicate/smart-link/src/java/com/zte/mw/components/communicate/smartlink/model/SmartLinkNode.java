package com.zte.mw.components.communicate.smartlink.model;

import com.zte.mw.components.tools.infrastructure.structure.Dependable;

public interface SmartLinkNode extends Dependable {
    void start();

    void post();

    MsgService service();
}
