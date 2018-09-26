package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.message.Result;
import com.zte.mos.util.tools.RefThread;
import com.zte.smartlink.log.CpuUsingLog;
import com.zte.smartlink.log.EventOrIndiTaskMaker;
import com.zte.smartlink.log.IoUsingLog;
import com.zte.smartlink.log.MemoryLog;
import com.zte.smartlink.log.MsgHandlingLog;
import com.zte.smartlink.log.RMILog;
import com.zte.smartlink.log.ThreadsMonitorLog;
import com.zte.smartlink.util.MwCpuWorkState;
import com.zte.ums.umd.api.outer.UmdInfoQueryException;
import com.zte.ums.umd.common.api.CpuWorkState;
import com.zte.ums.umd.common.util.ToolUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.zte.mos.util.basic.Logger.logger;

/**
 * Created by olinchy on 16-6-21.
 */
public class Counter
{
    private static ConcurrentLinkedQueue<MsgHandlingLog> queue = new ConcurrentLinkedQueue<MsgHandlingLog>();
    private static HashMap<String, HashMap<MoCmds, CounterFactor>> map = new HashMap<String, HashMap<MoCmds, CounterFactor>>();
    private static TimerTask msgInSum = new TimerTask()
    {
        @Override
        public void run()
        {
            logger(Counter.class).info("debugging in massive<Message>, msg in sum: " + map.toString());
        }
    };
    private static TimerTask mosHeapOutput = new TimerTask()
    {
        @Override
        public void run()
        {
            Runtime runtime = Runtime.getRuntime();
            long mb = 1048567;
            MemoryLog log = new MemoryLog("mos",
                    (RefThread.totalMemory(runtime) - RefThread.freeMemory(runtime)) / mb, RefThread.totalMemory(runtime) / mb);
            LoggerToRemote.log(log);
        }
    };

    private static TimerTask mosneHeapOutput = new TimerTask()
    {
        @Override
        public void run()
        {
            Runtime runtime = Runtime.getRuntime();
            long mb = 1048567;
            MemoryLog log = new MemoryLog("mos-ne",
                                          (RefThread.totalMemory(runtime) - RefThread.freeMemory(runtime)) / mb, RefThread.totalMemory(runtime) / mb);
            LoggerToRemote.log(log);
        }
    };

    private static TimerTask cpuUsing = new TimerTask()
    {
        @Override
        public void run()
        {
            try
            {
                String cpuRate = CpuWorkState.getCpuUsage();
                CpuUsingLog log = new CpuUsingLog(cpuRate);
                LoggerToRemote.log(log);
            }
            catch (UmdInfoQueryException e)
            {
                logger(Counter.class).error(e.getMessage(), e);
            }
        }
    };

    private static TimerTask mosCpuUsing = new TimerTask() {
        @Override
        public void run() {

            String cpuRate = MwCpuWorkState.getProcessCpuRate("zte_mwmos1")+"%";
            CpuUsingLog log = new CpuUsingLog(cpuRate, "Mos");
            LoggerToRemote.log(log);

        }
    };



    private static TimerTask mosNeCpuUsing = new TimerTask()
    {
        @Override
        public void run()
        {
            String cpuRate = MwCpuWorkState.getProcessCpuRate("zte_mwne1")+"%";
            CpuUsingLog log = new CpuUsingLog(cpuRate, "Mos-Ne");
            LoggerToRemote.log(log);
        }
    };

    private static TimerTask ioUsing = new TimerTask() {
        @Override
        public void run() {
            String strOSName = ToolUtil.getOSName();
            if(!strOSName.startsWith("Linux")) {  // support linux only 2017-8-28
                return;
            }
            try {
                String ioRate = getIoUsageOfLinux();
                IoUsingLog log = new IoUsingLog(ioRate);
                LoggerToRemote.log(log);
            } catch (Exception e){
                logger(Counter.class).error(e.getMessage(), e);
            }
        }
    };

    private static TimerTask mosNeThreadsMonitorTask = new TimerTask()
    {
        @Override
        public void run()
        {
            ThreadsMonitorLog log = new ThreadsMonitorLog("Mos-Ne");
            LoggerToRemote.log(log);
        }
    };

    private static TimerTask mosThreadsMonitorTask = new TimerTask()
    {
        @Override
        public void run()
        {
            ThreadsMonitorLog log = new ThreadsMonitorLog("Mos");
            LoggerToRemote.log(log);
        }
    };

    private static Timer timer = new Timer("Timer-" + Counter.class.getSimpleName());
    private static Timer timer_cost_time = new Timer("Timer-COST" + Counter.class.getSimpleName());
    private static Thread statistics = RefThread.newThread(new Runnable() {
        @Override
        public void run()
        {
            while (true)
            {
                try
                {
                    RefThread.sleep(1);
                }
                catch (InterruptedException ignored)
                {
                }


                while (true)
                {
                    synchronized (queue) {
                        if (queue.isEmpty())
                            break;
                        push(queue.poll(), map);
                    }
                }

            }
        }

        private void push(MsgHandlingLog log, HashMap<String, HashMap<MoCmds, CounterFactor>> map)
        {
            HashMap<MoCmds, CounterFactor> mapFactor = map.get(log.name);
            if (mapFactor == null)
            {
                map.put(log.name, mapFactor = new HashMap<MoCmds, CounterFactor>());
            }

            CounterFactor factor = mapFactor.get(log.msg.getCmd());
            if (factor == null)
                mapFactor.put(log.msg.getCmd(), factor = new CounterFactor());
            factor.count++;
            factor.cost += log.timeCost;
        }
    });
    private static ThreadGroup rmiThreadGroup = null;
    private static final TimerTask rmiThreadsCount = new TimerTask()
    {
        @Override
        public void run()
        {
            if (rmiThreadGroup == null)
                findThreadGroup("RMI");
            if (rmiThreadGroup != null)
            {
                RMILog log = new RMILog(rmiThreadGroup.activeCount());
                LoggerToRemote.log(log);
            }
        }

        private void findThreadGroup(String namePrefix)
        {
            ThreadGroup group = Thread.currentThread().getThreadGroup();
            while (group != null)
            {
                if (group.getName().startsWith("RMI"))
                {
                    rmiThreadGroup = group;
                    break;
                }
                ThreadGroup[] groups = new ThreadGroup[group.activeGroupCount()];
                group.enumerate(groups);
                for (ThreadGroup threadGroup : groups)
                {
                    if (threadGroup.getName().startsWith(namePrefix))
                    {
                        rmiThreadGroup = threadGroup;
                        break;
                    }
                }
                if (rmiThreadGroup != null)
                    break;
                group = group.getParent();
            }
        }
    };

    static
    {
        RefThread.start(statistics);
    }

    public static void startMosThreadMonitor(){
        timer_cost_time.schedule(cpuUsing, 5000, 60000);
        timer_cost_time.schedule(ioUsing, 5000, 60000);
        timer.schedule(mosHeapOutput, 3000, 5000);
        timer.schedule(msgInSum, 25000, 60000);
        timer.schedule(rmiThreadsCount, 2000, 5000);
        timer.schedule(mosThreadsMonitorTask, 3000, 15000);
        timer.schedule(EventOrIndiTaskMaker.getEventMonitorTask("Mos"), 3000, 30000);
        timer.schedule(EventOrIndiTaskMaker.getBatchIndicationMonitorTask("Mos"), 3000, 30000);
    }

    public static void startMosNeThreadMonitor(){
        timer.schedule(mosNeThreadsMonitorTask, 3000, 15000);
        timer.schedule(mosneHeapOutput, 3000, 5000);
        timer.schedule(mosCpuUsing, 3000, 5000);
        timer.schedule(mosNeCpuUsing, 3000, 5000);
        timer.schedule(EventOrIndiTaskMaker.getEventMonitorTask("Mos-Ne"), 3000, 30000);
        timer.schedule(EventOrIndiTaskMaker.getBatchIndicationMonitorTask("Mos-Ne"), 3000, 30000);
    }

    public static Result count(Executing executing, String name) throws MOSException, RemoteException
    {
        Date date = new Date();
        Result result = executing.execute();

        MsgHandlingLog msgHandlingLog;
        queue.add(msgHandlingLog = new MsgHandlingLog(executing.getMsg(), new Date().getTime() - date.getTime(), name));
        LoggerToRemote.log(msgHandlingLog);

        return result;
    }

    private static String getIoUsageOfLinux() {
        String deviceName = "sda";
        String cmdStr = "iostat -d -k -x 1 1 | grep" + deviceName + " | awk \'{print $NF}\'";
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", cmdStr });
            LineNumberReader br = new LineNumberReader(new InputStreamReader(process.getInputStream()));
            StringBuffer strBuffer = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                strBuffer.append(line).append("\n");
            }
            return strBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
