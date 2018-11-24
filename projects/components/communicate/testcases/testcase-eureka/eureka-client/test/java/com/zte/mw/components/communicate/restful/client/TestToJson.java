package com.zte.mw.components.communicate.restful.client;

import com.zte.mw.components.communicate.restful.JsonUtil;
import com.zte.mw.components.communicate.restful.ShrankRestfulAddress;
import com.zte.mw.components.communicate.smartlink.addressBook.AddressBook;
import com.zte.mw.components.communicate.smartlink.model.message.RegisterMsg;
import com.zte.mw.components.tools.environment.TestBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestToJson {


    @BeforeClass
    public static void setUp() throws Exception {
        TestBuilder.setUp();
    }

    @Test
    public void name() throws Exception {

        RegisterMsg msg = new RegisterMsg(new AddressBook(), new ShrankRestfulAddress("" + "/test"));

        System.out.println(JsonUtil.toString(msg));
    }


}
