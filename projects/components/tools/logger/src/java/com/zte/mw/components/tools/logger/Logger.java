package com.zte.mw.components.tools.logger;

public interface Logger {
    void info(String msg);

    void info(String msg, Throwable e);

    void info(Object message);

    void error(Object message, Throwable t);

    void error(String msg);

    void error(String msg, Throwable e);

    void warn(String msg);

    void warn(String msg, Throwable e);

    void debug(String msg, Throwable e);

    void debug(String msg);
}
