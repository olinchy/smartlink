/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure;

import java.util.ArrayList;
import java.util.stream.IntStream;

import org.junit.Test;

import com.zte.mw.components.tools.infrastructure.leaking_bucket.Fusing;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TestFusing {
    @Test
    public void test_fusing_sequence() throws InterruptedException {
        ArrayList<Stub> list = new ArrayList<>();

        Fusing<Stub> fusing = new Fusing<>(3000, list::add);

        // push 10 stub, wait for timeout
        for (int i = 0; i < 10; i++) {
            fusing.push(new Stub(String.valueOf(i)));
            Thread.sleep(10);
        }

        while (true) {
            if (list.size() == 10) {
                assertThat(list.stream().map(Stub::toString).toArray(String[]::new), is(IntStream.rangeClosed(
                        0, 9).boxed().map(
                        String::valueOf).toArray(String[]::new)));
                return;
            } else {
                Thread.sleep(10);
            }
        }
    }

    @Test
    public void test_fusing_replace() throws InterruptedException {
        ArrayList<String> list = new ArrayList<>();
        Fusing<Stub> fusing = new Fusing<>(3000, x -> list.add(x.toString()));

        fusing.push(new Stub("2"));
        Thread.sleep(10);
        fusing.push(new Stub("3"));
        Thread.sleep(10);
        fusing.push(new Stub("2"));

        Thread.sleep(10000);

        assertThat(list.toArray(new String[0]), is(new String[]{"3", "2"}));
    }

    private class Stub {
        Stub(String name) {
            this.name = name;
        }

        private final String name;

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final Stub stub = (Stub) o;

            return name != null ? name.equals(stub.name) : stub.name == null;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
