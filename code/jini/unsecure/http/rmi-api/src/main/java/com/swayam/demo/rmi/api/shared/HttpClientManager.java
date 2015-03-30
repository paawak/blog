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

import com.sun.jini.collection.SoftCache;

/**
 * Class for managing client-side functions shared among multiple connections
 * (e.g., tracking of unsent response acknowledgments, caching of information
 * about contacted HTTP servers).
 *
 * @author Sun Microsystems, Inc.
 * 
 */
public class HttpClientManager {

    private final SoftCache rolodex = new SoftCache();

    /**
     * Creates new HttpClientManager which expires unsent acknowledgments after
     * the specified timeout.
     */
    public HttpClientManager(long ackTimeout) {
    }

    /**
     * Returns cached information about specified HTTP server, or ServerInfo
     * struct with default values if no entry found.
     */
    ServerInfo getServerInfo(String host, int port) {
        ServerKey key = new ServerKey(host, port);
        synchronized (rolodex) {
            ServerInfo info = (ServerInfo) rolodex.get(key);
            return (info != null) ? (ServerInfo) info.clone() : new ServerInfo(host, port);
        }
    }

    /**
     * Server lookup key.
     */
    private static class ServerKey {

        private final String host;
        private final int port;
        private final int hash;

        ServerKey(String host, int port) {
            this.host = host;
            this.port = port;
            hash = (host.hashCode() << 10) | (port & 0x3FF);
        }

        public int hashCode() {
            return hash;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ServerKey) {
                ServerKey key = (ServerKey) obj;
                return host.equals(key.host) && port == key.port;
            }
            return false;
        }
    }
}
