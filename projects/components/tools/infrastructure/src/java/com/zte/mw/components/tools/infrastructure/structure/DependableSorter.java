/*
 * Copyright © 2015 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.tools.infrastructure.structure;

import java.util.LinkedList;
import java.util.List;

import com.zte.mw.components.tools.logger.Logger;

import static com.zte.mw.components.tools.infrastructure.LoggerLocator.logger;

public class DependableSorter {
    private static Logger log = logger(DependableSorter.class);

    public static <T extends Dependable> List<T> sort(List<T> inputs) {
        final LinkedList<T> list = new LinkedList<>();

        inputs.removeIf(t -> {
            boolean hasNoDepends = t.depends() == null || t.depends().equals("");
            if (hasNoDepends) {
                list.add(t);
            }
            return hasNoDepends;
        });

        while (inputs.size() > 0) {
            int toResolveDependencyNodes = inputs.size();

            for (int i = inputs.size() - 1; i >= 0; i--) {
                final int finalI = i;
                Dependable depended = new Dependable() {
                    @Override
                    public String depends() {
                        return null;
                    }

                    @Override
                    public String name() {
                        return "";
                    }

                    @Override
                    public boolean equals(Object obj) {
                        String name = ((Dependable) obj).name();
                        if (name == null) {
                            return false;
                        }
                        return name.equals(inputs.get(finalI).depends());
                    }
                };

                int index = list.lastIndexOf(depended);
                if (index != -1) {
                    list.add(index + 1, inputs.remove(i));
                }
            }

            if (inputs.size() == toResolveDependencyNodes) {
                for (Dependable node : inputs) {
                    log.warn("instance " + node.name() + " cannot resolve start dependency");
                }
                break;
            }
        }
        return list;
    }
}
