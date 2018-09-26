package com.zte.smartlink.addressbook;
import static com.zte.mos.util.basic.Logger.logger;

public abstract class SendNodeThread implements Runnable
{
    protected final NodeAddress[] nodes;

    public SendNodeThread(NodeAddress[] nodes)
    {
        this.nodes = nodes;
    }

    public SendNodeThread(NodeAddress node)
    {
        this(new NodeAddress[] { node });
    }

    @Override
    public void run()
    {
        try
        {
            send();
        }
        catch (Exception e)
        {
            logger(SendNodeThread.class).error(e.getMessage(), e);
        }
    }

    public abstract void send() throws Exception;
}
