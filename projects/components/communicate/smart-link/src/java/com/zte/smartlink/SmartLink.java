package com.zte.smartlink;

import com.zte.mos.exception.MOSException;
import com.zte.mos.util.OperationMask;
import com.zte.mos.util.Visitor;

import java.util.ArrayList;

import static com.zte.mos.util.tools.StringUtil.fnmatch;

public class SmartLink
{
    ArrayList<NodeLink> nodeLinks = new ArrayList<NodeLink>();

    public void accept(String dn, OperationMask mask, Visitor<Address> visitor)
            throws MOSException
    {
        ArrayList<NodeLink> nodes = new ArrayList<NodeLink>();
        getRelativeNode(nodes, dn);
        for (NodeLink node : nodes)
        {
            node.accept(mask, visitor);
        }
    }

    private void getRelativeNode(
            ArrayList<NodeLink> nodes, String dn)
    {
        for (NodeLink nodeLink : nodeLinks)
        {
            if (fnmatch(nodeLink.getDN(), dn))
            {
                nodes.add(nodeLink);
            }
        }
    }

    public void addLink(NodeLink link)
    {
        nodeLinks.add(link);
    }

    public String toString()
    {
        StringBuffer buffer = new StringBuffer();
        for (NodeLink link : nodeLinks)
        {
            buffer.append("\t" + link.toString() + "\n");
        }

        return buffer.toString();
    }
}
