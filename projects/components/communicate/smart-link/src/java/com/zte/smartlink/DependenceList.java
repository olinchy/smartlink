package com.zte.smartlink;

import com.zte.mos.reflection.safe.InstanceUtil;
import com.zte.mos.util.LambdaConverter;
import com.zte.mos.util.To;
import com.zte.mos.util.basic.Logger;

import java.util.LinkedList;
import java.util.Set;

/**
 * Created by olinchy on 15-9-1.
 */
public class DependenceList
{
    static <T extends Dependable> void addTo(final T dependable, LinkedList<T> lst)
    {
        if (dependable.depends().equals(""))
        {
            lst.add(0, dependable);
        }
        else
        {
            int index = lst.indexOf(
                    new Dependable()
                    {
                        @Override
                        public String getName()
                        {
                            return dependable.depends();
                        }

                        @Override
                        public String depends()
                        {
                            return "";
                        }

                        public boolean equals(Object obj)
                        {
                            return this.getName().equals(((Dependable) obj).getName());
                        }
                    });
            index = index == -1 ? lst.size() : index + 1;
            lst.add(index, dependable);
        }
    }

    public static void sort(LinkedList<SmartLinkNode> lstNode, Set<Class<SmartLinkNode>> set)
    {
        final LinkedList<SmartLinkNode> nodes = new LinkedList<SmartLinkNode>();
        nodes.addAll(To.map(set, new LambdaConverter<SmartLinkNode, Class<SmartLinkNode>>()
        {
            @Override
            public SmartLinkNode to(Class<SmartLinkNode> content)
            {
                try
                {
                    return InstanceUtil.newInstance(content);
                }
                catch (Exception e)
                {
                    Logger.logger(this.getClass()).warn("instance " + content + " caught exception", e);
                }
                return null;
            }
        }));

        for (int i = nodes.size() - 1; i >= 0; i--)
        {
            if (nodes.get(i) != null && nodes.get(i).depends().equals(""))
            {
                lstNode.add(nodes.remove(i));
            }
        }
        while (nodes.size() > 0)
        {
            int toResolveDependencyNodes = nodes.size();

            for (int i = nodes.size() - 1; i >= 0; i--)
            {
                final int finalI = i;
                Dependable dependable = new Dependable()
                {
                    @Override
                    public String getName()
                    {
                        return "";
                    }

                    @Override
                    public String depends()
                    {
                        return null;
                    }

                    @Override
                    public boolean equals(Object obj)
                    {
                        return ((Dependable) obj).getName().equals(nodes.get(finalI).depends());
                    }
                };

                int index = lstNode.lastIndexOf(dependable);
                if (index != -1)
                {
                    lstNode.add(index + 1, nodes.remove(i));
                }
            }

            if (nodes.size() == toResolveDependencyNodes)
            {
                for (SmartLinkNode node: nodes)
                {
                    Logger.logger(DependenceList.class).warn("instance " + node.getName() + " cannot resolve start dependency");
                }
                break;
            }
        }
    }
}
