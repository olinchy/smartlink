/*
 * Copyright © 2016 ZTE and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package com.zte.mw.components.communicate.restful.client;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;

import com.zte.mw.components.communicate.restful.JsonUtil;

public class JsonProvider<T> implements MessageBodyReader<T> {
    @Override
    public boolean isReadable(
            final Class<?> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType) {
        return MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType);
    }

    @Override
    public T readFrom(
            final Class<T> aClass, final Type type, final Annotation[] annotations, final MediaType mediaType,
            final MultivaluedMap<String, String> multivaluedMap,
            final InputStream inputStream) throws IOException, WebApplicationException {
        return JsonUtil.toObject(inputStream, aClass);
    }
}
