package com.zte.mw.components.tools.infrastructure.leaking_bucket;

/**
 * Created by olinchy on 16-11-30.
 */
public interface LeakingFunction<T>
{
    void run(T t);
}
