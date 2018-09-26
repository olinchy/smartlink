package com.zte.smartlink.deliver;

import com.zte.mos.exception.MOSException;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.inf.MoMsg;
import com.zte.mos.message.Failure;
import com.zte.mos.message.Result;
import com.zte.mos.message.Successful;
import com.zte.mos.util.Visitor;
import com.zte.mos.util.basic.Logger;
import com.zte.mos.util.msg.IndicateMsg;
import com.zte.mos.util.msg.MoActionMsg;
import com.zte.mos.util.msg.MoConfigMsg;
import com.zte.smartlink.Address;
import com.zte.smartlink.SmartLink;
import com.zte.smartlink.SmartLinkRepository;
import com.zte.smartlink.addressbook.client.LocalAddressBook;

import java.util.*;

import static com.zte.mos.util.Singleton.getInstance;

public class DeliverService
{
    private final SmartLink tree;
    private final String name;

    public DeliverService(String name) throws MOSException
    {
        tree = getInstance(SmartLinkRepository.class).getTree(name);
        this.name = name;
    }

    public Result<Result> send(MoMsg msg, Address... exclude)
            throws MOSException
    {

        String msgLogContent = "send msg from " + name + " " + msg + " exclude " + (exclude != null ?
                Arrays.toString(exclude) :
                "");
        if (msg instanceof MoActionMsg)
        {
            Logger.logger(getClass()).info(msgLogContent);
        }
        else
        {
            Logger.logger(getClass()).debug(msgLogContent);
        }

        class AddressVisitor implements Visitor<Address>
        {
            private final MoMsg msg;
            private final List<Address> exclude;
            ArrayList<Result> lstRes = new ArrayList<Result>();

            public AddressVisitor(MoMsg msg, Address[] exclude)
            {
                this.msg = msg;
                this.exclude = Arrays.asList(exclude);
            }

            @Override
            public void visit(Address addr) throws MOSException
            {
                if (exclude.contains(addr))
                {
                    return;
                }
                try
                {
                    lstRes.add(addr.on(msg));
                }
                catch (Throwable ex)
                {
                    Logger.logger(getClass()).warn("send msg failed!", ex);
                    lstRes.add(new Failure(new MOSException("send msg to " + addr + " failed!", ex)));
                }
            }
        }
        if (msg instanceof MoConfigMsg)
        {
            getInstance(SenderStorage.class).put(
                    msg,
                    getInstance(LocalAddressBook.class).getAddress(name));
        }

        AddressVisitor visitor = new AddressVisitor(msg, exclude);
        tree.accept(msg.dn(), MsgMask.mask(msg), visitor);

        return new Successful<Result>(visitor.lstRes);
    }

    public void indicate(LinkedList<IndicateMsg> msgLst) throws MOSException
    {
        final HashMap<Address, ArrayList<IndicateMsg>> jobList = new HashMap<Address, ArrayList<IndicateMsg>>();
        for (final IndicateMsg indicateMsg : msgLst)
        {
            final ArrayList<Address> exclude = new ArrayList<Address>();
            MoMsg sender = ghostSender(indicateMsg);
            Address[] sendersOf = getInstance(SenderStorage.class).sendersOf(sender);
            exclude.addAll(Arrays.asList(sendersOf));

            Visitor<Address> visitor = new Visitor<Address>()
            {
                @Override
                public void visit(Address address) throws MOSException
                {
                    if (exclude.contains(address))
                    {
                        return;
                    }
                    if (!jobList.containsKey(address))
                    {
                        jobList.put(address, new ArrayList<IndicateMsg>());
                    }
                    jobList.get(address).add(indicateMsg);
                }
            };
            try
            {
                tree.accept(indicateMsg.dn(), MsgMask.mask(indicateMsg), visitor);
            }
            finally
            {
                getInstance(SenderStorage.class).clear(sender);
            }
        }
        if (jobList.size() > 0)
            IndicationSender.addJob(jobList);
    }

    private MoMsg ghostSender(IndicateMsg indicateMsg)
    {
        switch (indicateMsg.getCmd())
        {
            case MoCreateInd:
                return new MoMsgGhost(MoCmds.MoCreateRequest, indicateMsg.dn());
            case MoSetInd:
                return new MoMsgGhost(MoCmds.MoSetRequest, indicateMsg.dn());
            case MoDeleteInd:
                return new MoMsgGhost(MoCmds.MoDeleteRequest, indicateMsg.dn());
        }
        return null;
    }
}
