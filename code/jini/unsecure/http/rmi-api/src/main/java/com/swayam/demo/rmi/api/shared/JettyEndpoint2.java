/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swayam.demo.rmi.api.shared;

import java.io.IOException;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.Endpoint;
import net.jini.jeri.OutboundRequest;
import net.jini.jeri.OutboundRequestIterator;

public final class JettyEndpoint2 implements Endpoint, Serializable {

    private static final long serialVersionUID = -7094180943307123931L;

    /** set of canonical instances */
    private static final Map<JettyEndpoint2, Reference<JettyEndpoint2>> internTable = new WeakHashMap<>();

    private final String host;

    private final int port;

    public static JettyEndpoint2 getInstance(String host, int port) {
        return intern(new JettyEndpoint2(host, port));
    }

    private static JettyEndpoint2 intern(JettyEndpoint2 endpoint) {
        synchronized (internTable) {
            Reference<JettyEndpoint2> ref = internTable.get(endpoint);
            if (ref != null) {
                JettyEndpoint2 canonical = (JettyEndpoint2) ref.get();
                if (canonical != null) {
                    return canonical;
                }
            }
            internTable.put(endpoint, new SoftReference<JettyEndpoint2>(endpoint));
            return endpoint;
        }
    }

    private JettyEndpoint2(String host, int port) {
        if (host == null) {
            throw new NullPointerException();
        }
        if (port < 1 || port > 0xFFFF) {
            throw new IllegalArgumentException("port number out of range: " + port);
        }

        this.host = host;
        this.port = port;
    }

    @Override
    public OutboundRequestIterator newRequest(final InvocationConstraints constraints) {
        if (constraints == null) {
            throw new NullPointerException();
        }
        return new OutboundRequestIterator() {
            private boolean nextCalled = false;
            private OutboundRequest currentRequest;

            public OutboundRequest next() throws IOException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                nextCalled = true;
                currentRequest = nextRequest();
                return currentRequest;
            }

            public boolean hasNext() {
                // NYI: determine if HTTP failure suggests retry
                return !nextCalled;
            }
        };
    }

    private OutboundRequest nextRequest() throws IOException {
        IOStreamProvider ioStreamProvider;
        if (true) {
            ioStreamProvider = new SocketFactoryIOStreamProvider(host, port);
        } else {
            // FIXME: hardcoded port: does not work
            ioStreamProvider = new HttpIOStreamProvider(host, 8100, 23);
        }
        return new OutboundRequestImpl(ioStreamProvider);
    }

}
