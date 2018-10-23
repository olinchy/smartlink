package com.zte.mw.components.tools.environment;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.RollingFileAppender;

/**
 * Created by olinchy on 4/3/15 for mosjava.
 */
public class ExtendedRollingFileAppender extends RollingFileAppender
{
    @Override
    public synchronized void setFile(String fileName, boolean append, boolean bufferedIO,
                                     int bufferSize) throws IOException
    {
        File file = new File(fileName);
        if (!file.exists())
        {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        super.setFile(fileName, append, bufferedIO, bufferSize);
    }
}
