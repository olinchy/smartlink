package com.zte.mw.components.tools.infrastructure;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class Prop {
    private static Properties prop = new Properties();
    private static Timer timer = new Timer("reload properties timer", true);

    static {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                reload();
            }
        }, 600000, 600000);
        reload();
    }

    private static void reload() {
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File("conf.properties"));
            prop.load(fi);
        } catch (IOException e) {
            logger(Prop.class).info("conf.properties not found");
        } finally {
            if (null != fi) {
                try {
                    fi.close();
                } catch (IOException e) {
                    logger(Prop.class).error(e.getMessage(), e);
                }
            }
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }

    public static void set(Properties properties) {
        prop = properties;
    }

    public static Properties getProp() {
        return prop;
    }

    public static void append(Properties pop) {
        for (Map.Entry<Object, Object> entry : pop.entrySet()) {
            prop.setProperty(entry.getKey().toString(), String.valueOf(entry.getValue()));
        }
    }

    public static void setProperty(String key, String value) {
        prop.setProperty(key, value);
    }
}
