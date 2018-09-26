package com.zte.smartlink;

import org.junit.Test;

import java.rmi.Naming;
import java.util.Date;

/**
 * Created by olinchy on 16-5-27.
 */
public class TestNamingLookup
{
    @Test
    public void name() throws Exception
    {
        long time = new Date().getTime();

        try
        {
            Naming.lookup("//10.86.54.33:55642/client/test");
        }
        catch (Throwable e)
        {
            System.out.println(new Date().getTime() - time);
        }
    }
}
