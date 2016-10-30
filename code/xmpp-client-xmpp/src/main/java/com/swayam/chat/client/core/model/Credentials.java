/*
 * Credentials.java
 *
 * Created on Dec 5, 2009 4:31:08 PM
 *
 * Copyright (c) 2002 - 2009 : Swayam Inc.
 *
 * All rights reserved. Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.swayam.chat.client.core.model;

/**
 * 
 * @author paawak
 */
public class Credentials {

    public static final String GTALK_SERVER = "gmail.com";

    private static final int DEFAULT_PORT = 5222;

    private final String server;

    private final int port;

    private final String userName;

    private final String password;

    public Credentials(String server, int port, String userName, String password) {
        this.server = server;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    public Credentials(String server, String userName, String password) {
        this(server, DEFAULT_PORT, userName, password);
    }

    /**
     * Use for connecting to GTalk
     * 
     * @param userName
     * @param password
     */
    public Credentials(String userName, String password) {
        this(GTALK_SERVER, DEFAULT_PORT, userName, password);
    }

    public static int getDefaultPort() {
        return DEFAULT_PORT;
    }

    public String getServer() {
        return server;
    }

    public int getPort() {
        return port;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + port;
        result = prime * result + ((server == null) ? 0 : server.hashCode());
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Credentials other = (Credentials) obj;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (port != other.port)
            return false;
        if (server == null) {
            if (other.server != null)
                return false;
        } else if (!server.equals(other.server))
            return false;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

}
