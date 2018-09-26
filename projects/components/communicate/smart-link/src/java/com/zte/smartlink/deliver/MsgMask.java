package com.zte.smartlink.deliver;

import com.zte.mos.inf.MoMsg;
import com.zte.mos.util.OperationMask;

public enum MsgMask
{
    MoCreateRequest(OperationMask.configChecking),
    MoSetRequest(OperationMask.configChecking),
    MoDeleteRequest(OperationMask.configChecking),
    MoCreateInd(OperationMask.configIndicationAdd),
    MoSetInd(OperationMask.configIndicationUpdate),
    MoDeleteInd(OperationMask.configIndicationDel),
    MoFindRequest(OperationMask.get),
    MoLsRequest(OperationMask.get),
    MoAck(OperationMask.get),
    MoVersionRequest(OperationMask.get),
    MoGetMetaRequest(OperationMask.get),
    MoGetRequest(OperationMask.get),
    MoGetConfigRequest(OperationMask.get),
    Event(OperationMask.event),
    MoAction(OperationMask.action),
    // for reference
    MoRef(OperationMask.ref),
    MoDestroyRef(OperationMask.ref),
    MoDeRef(OperationMask.ref);
    private OperationMask operationMask;

    MsgMask(OperationMask mask)
    {
        this.operationMask = mask;
    }

    public static OperationMask mask(MoMsg msg)
    {
        return valueOf(msg.getCmd().name()).operationMask;
    }

    public static SendMethod method(MoMsg msg)
    {
        if (mask(msg).isRpc())
        {
            return new RpcMethod();
        }
        else
        {
            return new AsyncMethod();
        }
    }
}
