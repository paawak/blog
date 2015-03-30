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
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.net.Socket;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.WeakHashMap;
import java.util.logging.Logger;

import javax.net.SocketFactory;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.jeri.Endpoint;
import net.jini.jeri.OutboundRequest;
import net.jini.jeri.OutboundRequestIterator;
import net.jini.jeri.http.HttpServerEndpoint;
import net.jini.security.proxytrust.TrustEquivalence;

import com.sun.jini.jeri.internal.runtime.Util;

/**
 * An implementation of the {@link Endpoint} abstraction that uses HTTP messages
 * sent over TCP sockets (instances of {@link Socket}) for the underlying
 * communication mechanism.
 *
 * <p>
 * <code>HttpEndpoint</code> instances contain a host name and a TCP port
 * number, as well as an optional {@link SocketFactory} for customizing the type
 * of <code>Socket</code> to use. The host name and port number are used as the
 * remote address to connect to when making socket connections. Note that
 * constructing an <code>HttpEndpoint</code> with a <code>SocketFactory</code>
 * instance that produces SSL sockets does not result in an endpoint that is
 * fully HTTPS capable.
 *
 * <p>
 * <code>HttpEndpoint</code> instances map outgoing requests to HTTP
 * request/response messages; when possible, underlying TCP connections are
 * reused for multiple non-overlapping outgoing requests. Outbound request data
 * is sent as the <code>entity-body</code> of an HTTP POST request; inbound
 * response data is received as the <code>entity-body</code> of the
 * corresponding HTTP response message. For information on HTTP, refer to <a
 * href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616</a>.
 * 
 * <p>
 * <code>HttpEndpoint</code> can be configured via system properties to send
 * HTTP messages through an intermediary HTTP proxy server. It also supports
 * basic and digest HTTP authentication, specified in <a
 * href="http://www.ietf.org/rfc/rfc2617.txt">RFC 2617</a>. The mechanisms
 * involved in configuring each of these features are the same as those used by
 * {@link java.net.HttpURLConnection}; for details, see the
 * {@link net.jini.jeri.http} package documentation.
 *
 * <p>
 * A <code>SocketFactory</code> used with an <code>HttpEndpoint</code> should be
 * serializable and must implement {@link Object#equals Object.equals} to obey
 * the guidelines that are specified for <code>equals</code> methods of
 * {@link Endpoint} instances.
 *
 * @author Sun Microsystems, Inc.
 * @see HttpServerEndpoint
 * @since 2.0
 **/
public final class JettyEndpoint2 implements Endpoint, TrustEquivalence, Serializable {
    private static final long serialVersionUID = -7094180943307123931L;

    /** set of canonical instances */
    private static final Map internTable = new WeakHashMap();

    /** client transport logger */
    private static final Logger logger = Logger.getLogger("net.jini.jeri.http.client");

    /**
     * The host that this <code>HttpEndpoint</code> connects to.
     *
     * @serial
     **/
    private final String host;

    /**
     * The TCP port that this <code>HttpEndpoint</code> connects to.
     *
     * @serial
     **/
    private final int port;

    /**
     * The socket factory that this <code>HttpEndpoint</code> uses to create
     * {@link Socket} objects.
     *
     * @serial
     **/
    private final SocketFactory sf;

    /**
     * Returns an <code>HttpEndpoint</code> instance for the given host name and
     * TCP port number. Note that if HTTP proxying is in effect, then an
     * explicit host name or IP address (i.e., not "localhost") must be
     * provided, or else the returned <code>HttpEndpoint</code> will be unable
     * to properly send requests through the proxy.
     *
     * <p>
     * The {@link SocketFactory} contained in the returned
     * <code>HttpEndpoint</code> will be <code>null</code>, indicating that this
     * endpoint will create {@link Socket} objects directly.
     *
     * @param host
     *            the host for the endpoint to connect to
     *
     * @param port
     *            the TCP port on the given host for the endpoint to connect to
     * 
     * @return an <code>HttpEndpoint</code> instance
     *
     * @throws IllegalArgumentException
     *             if the port number is out of the range <code>1</code> to
     *             <code>65535</code> (inclusive)
     *
     * @throws NullPointerException
     *             if <code>host</code> is <code>null</code>
     **/
    public static JettyEndpoint2 getInstance(String host, int port) {
        return intern(new JettyEndpoint2(host, port, null));
    }

    /**
     * Returns an <code>HttpEndpoint</code> instance for the given host name and
     * TCP port number that contains the given {@link SocketFactory}. Note that
     * if HTTP proxying is in effect, then an explicit host name or IP address
     * (i.e., not "localhost") must be provided, or else the returned
     * <code>HttpEndpoint</code> will be unable to properly send requests
     * through the proxy.
     *
     * <p>
     * If the socket factory argument is <code>null</code>, then this endpoint
     * will create {@link Socket} objects directly.
     *
     * @param host
     *            the host for the endpoint to connect to
     *
     * @param port
     *            the TCP port on the given host for the endpoint to connect to
     *
     * @param sf
     *            the <code>SocketFactory</code> to use for this
     *            <code>HttpEndpoint</code>, or <code>null</code>
     *
     * @return an <code>HttpEndpoint</code> instance
     *
     * @throws IllegalArgumentException
     *             if the port number is out of the range <code>1</code> to
     *             <code>65535</code> (inclusive)
     *
     * @throws NullPointerException
     *             if <code>host</code> is <code>null</code>
     **/
    public static JettyEndpoint2 getInstance(String host, int port, SocketFactory sf) {
        return intern(new JettyEndpoint2(host, port, sf));
    }

    /**
     * Returns canonical instance equivalent to given instance.
     **/
    private static JettyEndpoint2 intern(JettyEndpoint2 endpoint) {
        synchronized (internTable) {
            Reference ref = (SoftReference) internTable.get(endpoint);
            if (ref != null) {
                JettyEndpoint2 canonical = (JettyEndpoint2) ref.get();
                if (canonical != null) {
                    return canonical;
                }
            }
            internTable.put(endpoint, new SoftReference(endpoint));
            return endpoint;
        }
    }

    /**
     * Constructs a new (not fully initialized) instance.
     **/
    private JettyEndpoint2(String host, int port, SocketFactory sf) {
        if (host == null) {
            throw new NullPointerException();
        }
        if (port < 1 || port > 0xFFFF) {
            throw new IllegalArgumentException("port number out of range: " + port);
        }

        this.host = host;
        this.port = port;
        this.sf = sf;
    }

    /*
     * [This is not a doc comment to prevent its appearance in HttpEndpoint's
     * serialized form specification.]
     * 
     * Resolves deserialized instance to equivalent canonical instance.
     */
    private Object readResolve() {
        return intern(this);
    }

    /**
     * Returns the host that this <code>HttpEndpoint</code> connects to.
     *
     * @return the host that this endpoint connects to
     **/
    public String getHost() {
        return host;
    }

    /**
     * Returns the TCP port that this <code>HttpEndpoint</code> connects to.
     *
     * @return the TCP port that this endpoint connects to
     **/
    public int getPort() {
        return port;
    }

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
        return new OutboundRequestImpl(host, port, new SocketFactoryAdapter(sf));
    }

    /**
     * Returns the hash code value for this <code>HttpEndpoint</code>.
     *
     * @return the hash code value for this <code>HttpEndpoint</code>
     **/
    public int hashCode() {
        return host.hashCode() ^ port ^ (sf != null ? sf.hashCode() : 0);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof JettyEndpoint2)) {
            return false;
        }
        JettyEndpoint2 other = (JettyEndpoint2) obj;
        return host.equals(other.host) && port == other.port && Util.sameClassAndEquals(sf, other.sf);
    }

    public boolean checkTrustEquivalence(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof JettyEndpoint2)) {
            return false;
        }
        JettyEndpoint2 other = (JettyEndpoint2) obj;
        return host.equals(other.host) && port == other.port && Util.sameClassAndEquals(sf, other.sf);
    }

    public String toString() {
        return JettyEndpoint2.class.getSimpleName() + "[" + host + ":" + port + (sf != null ? "," + sf : "") + "]";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (host == null) {
            throw new InvalidObjectException("null host");
        }
        if (port < 1 || port > 0xFFFF) {
            throw new InvalidObjectException("port number out of range: " + port);
        }
    }

}
