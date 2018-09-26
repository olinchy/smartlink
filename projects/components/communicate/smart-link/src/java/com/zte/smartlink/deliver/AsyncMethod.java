package com.zte.smartlink.deliver;

import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.tools.RefThread;
import com.zte.smartlink.Sending;

import java.util.concurrent.*;

/**
 * Created by olinchy on 15-12-31.
 */
public class AsyncMethod implements SendMethod
{
    private static ThreadPoolExecutor pool = new ThreadPoolExecutor(
            30, 30, 2, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()
            , new TaskFactory(), new TaskHandlePolicy());

    private static class TaskFactory implements ThreadFactory
    {
        @Override
        public Thread newThread(Runnable r)
        {
            Thread thread = RefThread.newThread(r);
            thread.setName("Task Thread of Msg[" + "smartlink-AsyncMethod" + "]");
            return thread;
        }
    }

    private static class TaskHandlePolicy implements RejectedExecutionHandler
    {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor)
        {
        }
    }

    @Override
    public Result send(final Sending sending)
    {
        pool.execute(new EventAsyncSendThread(sending));
        return new Successful();
    }
}
