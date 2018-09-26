package com.zte.smartlink;

/**
 * Created by olinchy on 16-7-20.
 */
public class CounterFactor
{
    public int count = 0;
    public long cost = 0;

    @Override
    public String toString()
    {
        return "{" +
                "count=" + count +
                ", cost=" + cost + ", average=" + cost / count +
                '}';
    }
}
