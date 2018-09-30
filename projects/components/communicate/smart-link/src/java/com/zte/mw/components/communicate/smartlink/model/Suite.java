package com.zte.mw.components.communicate.smartlink.model;

public interface Suite {
    /**
     * read configuration to start client
     * @return
     */
    Address startClient(Then then);

    /**
     * start app node under client
     * @param nodeClass
     * @return
     */
    Address start(SmartLinkNode nodeClass);
}
