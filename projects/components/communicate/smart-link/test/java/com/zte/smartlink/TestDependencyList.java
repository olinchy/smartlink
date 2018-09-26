package com.zte.smartlink;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by olinchy on 15-9-1.
 */
public class TestDependencyList
{
    private static ArrayList<StubDependable> lst;

    @BeforeClass
    public static void setUp() throws Exception
    {
        lst = new ArrayList<StubDependable>();
        lst.add(new StubDependable("1", "2"));
        lst.add(new StubDependable("2"));
        lst.add(new StubDependable("3", "2"));
        lst.add(new StubDependable("4", "1"));
        lst.add(new StubDependable("5"));
        lst.add(new StubDependable("6"));
        lst.add(new StubDependable("7", "3"));
        lst.add(new StubDependable("8", "1"));

    }

    @Test
    public void test() throws Exception
    {
        LinkedList<StubDependable> stubs = new LinkedList<StubDependable>();
        for (StubDependable dependable : lst)
        {
            DependenceList.addTo(dependable, stubs);
        }

        System.out.println(stubs);

    }

    private static class StubDependable implements Dependable
    {
        private String depends;
        private String name;

        public StubDependable(String name)
        {
            this(name, "");
        }

        public StubDependable(String name, String depends)
        {
            this.name = name;
            this.depends = depends;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public String depends()
        {
            return depends;
        }

        @Override
        public String toString()
        {
            return "StubDependable{" +
                    "depends='" + depends + '\'' +
                    ", name='" + name + '\'' +
                    "}\n";
        }
    }
}
