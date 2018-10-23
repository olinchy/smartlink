/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure.leaking_bucket;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by olinchy on 16-11-30.
 */
public class Fusing<T> {
    public Fusing(final long timeout, final LeakingFunction<T> function) {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (queue.size() > 0) {
                    FusingDateWrap<T> wrap = queue.peek();
                    if (wrap != null) {
                        if (System.currentTimeMillis() - wrap.getDate() >= timeout) {
                            FusingDateWrap<T> pollData = queue.poll();
                            if (pollData != null) {
                                function.run(pollData.getT());
                            }
                        }
                    }
                }
            }
        }, 10, 20);
    }
    private static Timer timer = new Timer("leaking bucket timer", true);
    private ConcurrentLinkedQueue<FusingDateWrap<T>> queue = new ConcurrentLinkedQueue<FusingDateWrap<T>>();

    public void push(T t) {
        queue.remove(new FusingDateWrap<>(t));
        queue.offer(new FusingDateWrap<>(t));
    }
}
