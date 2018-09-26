package com.zte.smartlink;

import com.zte.mos.util.OperationMask;

public class NodeOp implements Comparable<NodeOp>
{
    private String name;
    private OperationMask mask;

    public NodeOp()
    {

    }

    public NodeOp(String name, OperationMask operationMask)
    {
        this.name = name;
        mask = operationMask;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof NodeOp))
        {
            return false;
        }
        return name.equals (((NodeOp) object).name);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public int compareTo(NodeOp other)
    {
        return name.compareTo(other.getName());
    }

    OperationMask getMask()
    {
        return mask;
    }
}
