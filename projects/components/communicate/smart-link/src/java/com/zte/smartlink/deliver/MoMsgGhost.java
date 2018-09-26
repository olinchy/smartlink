package com.zte.smartlink.deliver;

import com.zte.mos.domain.DN;
import com.zte.mos.inf.Maybe;
import com.zte.mos.inf.MoCmds;
import com.zte.mos.util.msg.MoMsgAdapter;

/**
 * Created by olinchy on 5/27/14 for MO_JAVA.
 */
public class MoMsgGhost extends MoMsgAdapter
{
    private MoCmds cmd;

    public MoMsgGhost(MoCmds cmd, String dn)
    {
        super(dn);
        this.cmd = cmd;
    }

    @Override public MoCmds getCmd()
    {
        return cmd;
    }

    @Override public Maybe<Integer> getTransactionID()
    {
        return new Maybe<Integer>(null);
    }

    @Override public void setTransId(Maybe<Integer> integerMaybe)
    {
    }

}
