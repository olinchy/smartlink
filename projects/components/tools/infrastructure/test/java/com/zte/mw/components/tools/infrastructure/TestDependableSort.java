/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.zte.mw.components.tools.infrastructure.structure.Dependable;
import com.zte.mw.components.tools.infrastructure.structure.DependableSorter;
import com.zte.mw.components.tools.environment.TestBuilder;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class TestDependableSort extends TestBuilder {
    public TestDependableSort(final String input, final String[] expected) {
        this.input = input;
        this.expected = expected;
    }

    private String input;
    private String[] expected;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"0->1,1->2,2->NULL", new String[]{"2", "1", "0"}},
                {"A->B,B->C,C->NULL", new String[]{"C", "B", "A"}},
                {"TEST->NULL,CM->MOS,PM->MOS,MOS->NULL", new String[]{"TEST", "MOS", "CM", "PM"}},
        });
    }

    @Test
    public void name() {

        // "A->B,B->C,C->NULL",
        ArrayList<Dependable> list = Arrays.stream(input.split(",")).map(token -> new Dependable() {
            @Override
            public String depends() {
                return token.split("->")[1].equals("NULL") ? "" : token.split("->")[1];
            }

            @Override
            public String name() {
                return token.split("->")[0];
            }
        }).collect(Collectors.toCollection(ArrayList::new));

        assertThat(
                DependableSorter.sort(list).stream().map(Dependable::name).toArray(), is(expected));
    }
}
