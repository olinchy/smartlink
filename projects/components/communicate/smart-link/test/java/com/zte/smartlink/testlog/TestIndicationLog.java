package com.zte.smartlink.testlog;

import com.zte.mos.exception.MOSException;
import com.zte.mos.message.Mo;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.mos.util.msg.MoCreateIndicationMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.UrlAddress;
import com.zte.smartlink.deliver.IndicationSender;
import com.zte.smartlink.log.BatchIndicationLog;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertTrue;

/**
 * Created by zhangwei on 16-11-30.
 */
public class TestIndicationLog
{
    @Test
    public void testBatchIndication() throws InterruptedException, MOSException
    {
        BatchIndicationLog log = BatchIndicationLog.instance("Mos");
        HashMap<Address, ArrayList<IndicateMsg>> jobList = new HashMap<Address, ArrayList<IndicateMsg>>();

        //no indication
        IndicationSender.addJob(jobList);
        sleep(100);
        assertTrue(log.toString().equals("Mos BatchIndication msgs count is 0, runCostTime is 0ms, scheduleCostTime is 0ms"));

        //insert indications
        ArrayList<IndicateMsg> nelst = new ArrayList<IndicateMsg>();
        UrlAddress neAddr = new AddressMock("//108.28.170.240:8000/client/APP_NE_RES");
        MoCreateIndicationMsg createNeInd = new MoCreateIndicationMsg("/Ems/1/Ne/1", new Mo());
        nelst.add(createNeInd);
        jobList.put(neAddr, nelst);

        ArrayList<IndicateMsg> boardlst = new ArrayList<IndicateMsg>();
        UrlAddress boardAddr = new AddressMock("//108.28.170.240:8000/client/APP_BOARD_RES");
        MoCreateIndicationMsg createBoardInd = new MoCreateIndicationMsg("/Ems/1/Ne/1/Product/1/Shelf/1/Board/1", new Mo());
        boardlst.add(createBoardInd);
        jobList.put(boardAddr, boardlst);
        IndicationSender.addJob(jobList);
        sleep(2000);
        System.out.println(log.toString());
    }

    private void sleep(long time) throws InterruptedException
    {
        Thread.sleep(time);
    }
}
