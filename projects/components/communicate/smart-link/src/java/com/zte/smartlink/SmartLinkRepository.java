package com.zte.smartlink;

import java.util.ArrayList;

public class SmartLinkRepository
{
    private final ArrayList<SmartLinkAdapter> smartLinks = new ArrayList<SmartLinkAdapter>();

    private SmartLinkRepository()
    {

    }

    public void add(String name, SmartLink tree)
    {
        SmartLinkAdapter linkAdapter = new SmartLinkAdapter(name, tree);
        if (!smartLinks.contains(linkAdapter))
        {
            smartLinks.add(linkAdapter);
        }
    }

    public SmartLink getTree(String name)
    {
        for (int i = 0; i < smartLinks.size(); i++)
        {
            SmartLinkAdapter linkAdapter = smartLinks.get(i);
            if (linkAdapter.name.equals(name))
            {
                return linkAdapter.smartLink;
            }
        }
        // TODO add dump

        return null;
    }

    private class SmartLinkAdapter
    {
        public String name;
        public SmartLink smartLink;

        public SmartLinkAdapter(String name, SmartLink smartLink)
        {
            this.name = name;
            this.smartLink = smartLink;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o)
            {
                return true;
            }
            if (o == null || getClass() != o.getClass())
            {
                return false;
            }

            SmartLinkAdapter that = (SmartLinkAdapter) o;

            return !(name != null ? !name.equals(that.name) : that.name != null);
        }

        @Override
        public int hashCode()
        {
            return name != null ? name.hashCode() : 0;
        }
    }

}
