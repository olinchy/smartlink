package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.OperationMask;
import com.zte.mos.util.Visitor;
import com.zte.smartlink.addressbook.client.LocalAddressBook;

import java.util.Set;
import java.util.TreeSet;

import static com.zte.mos.util.Singleton.getInstance;
import static com.zte.mos.util.basic.Logger.logger;

public class NodeLink
{
    private final String dn;
    private Set<NodeOp> links = new TreeSet<NodeOp>();

    public NodeLink(String dn)
    {
        this.dn = dn;
    }

    public void addNode(String name, OperationMask mask)
    {
        NodeOp nodeOp = new NodeOp(name, mask);
        links.add(nodeOp);
    }

    public void accept(OperationMask mask, Visitor<Address> visitor)
            throws MOSException
    {

        for (NodeOp nodeOp : links)
        {
            if (nodeOp.getMask().isSet(mask))
            {
                Address[] addresses = getInstance(LocalAddressBook.class)
                        .getAddress(nodeOp.getName());
                if (addresses == null || addresses.length == 0) {
                    logger(getClass()).debug("cannot find address of " + nodeOp.getName());
                    continue;
                }
                for (Address address : addresses)
                {
                    visitor.visit(address);
                }
            }
        }
    }

    public void reset()
    {
        links = new TreeSet<NodeOp>();
    }

    public String getDN()
    {
        return dn;
    }

    @Override
    public String toString()
    {
        StringBuilder buffer = new StringBuilder();
        for (NodeOp op : links)
        {
            buffer.append("name ").append(op.getName()).append(" mask ").append(
                    op.getMask()).append("\t");
        }
        return buffer.toString();
    }
}
