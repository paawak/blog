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

package com.swayam.demo.rmi.server.core.rmi;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.AccessControlContext;
import java.security.PrivilegedAction;
import java.util.logging.Logger;

import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

import net.jini.core.constraint.InvocationConstraints;
import net.jini.io.UnsupportedConstraintException;
import net.jini.jeri.Endpoint;
import net.jini.jeri.InboundRequest;
import net.jini.jeri.ServerEndpoint;
import net.jini.security.Security;
import net.jini.security.SecurityContext;

import com.sun.jini.jeri.internal.runtime.Util;
import com.sun.jini.logging.Levels;
import com.sun.jini.logging.LogUtil;
import com.sun.jini.thread.Executor;
import com.sun.jini.thread.GetThreadPoolAction;
import com.swayam.demo.rmi.api.shared.Constraints;
import com.swayam.demo.rmi.api.shared.JettyEndpoint2;

/**
 * An implementation of the {@link ServerEndpoint} abstraction that uses HTTP
 * messages sent over TCP sockets (instances of {@link ServerSocket}) for the
 * underlying communication mechanism.
 *
 * <p>
 * <code>HttpServerEndpoint</code> instances contain a host name and a TCP port
 * number, as well as an optional {@link ServerSocketFactory} for customizing
 * the type of <code>ServerSocket</code> to use and an optional
 * {@link SocketFactory} for customizing the type of {@link Socket} that client
 * endpoints will use. The port number is the local TCP port to bind to when
 * listening for incoming socket connections. If the port number is zero, then
 * each listen operation will bind to a free (non-zero) port, which will be the
 * port number contained in the resulting {@link JettyEndpoint2}. The host name
 * contained in an <code>HttpServerEndpoint</code> controls the host name that
 * will be contained in the <code>HttpEndpoint</code> instances produced when
 * {@link #enumerateListenEndpoints enumerateListenEndpoints} is invoked to
 * listen on the <code>HttpServerEndpoint</code> (this host name does not affect
 * the behavior of listen operations themselves). If the host name in an
 * <code>HttpServerEndpoint</code> is <code>null</code>, then the host name in
 * the <code>HttpEndpoint</code> instances that it produces will be the IP
 * address string obtained from {@link InetAddress#getLocalHost
 * InetAddress.getLocalHost} when {@link #enumerateListenEndpoints
 * enumerateListenEndpoints} is invoked.
 *
 * <p>
 * <code>HttpServerEndpoint</code> instances map incoming HTTP messages to
 * requests; when possible, underlying TCP connections are persisted to
 * accommodate multiple non-overlapping incoming requests. Inbound request data
 * is received as the <code>entity-body</code> of an HTTP POST request; outbound
 * response data is sent as the <code>entity-body</code> of the corresponding
 * HTTP return message. For information on HTTP, refer to <a
 * href="http://www.ietf.org/rfc/rfc2616.txt">RFC 2616</a>. Note that providing
 * socket factories that produce SSL sockets does not result in endpoints that
 * are fully HTTPS capable.
 *
 * <p>
 * A <code>ServerSocketFactory</code> used with an
 * <code>HttpServerEndpoint</code> must implement {@link Object#equals
 * Object.equals} to obey the guidelines that are specified for
 * <code>equals</code> methods of
 * {@link net.jini.jeri.ServerEndpoint.ListenEndpoint ListenEndpoint} instances.
 * A <code>SocketFactory</code> used with a <code>HttpServerEndpoint</code>
 * should be serializable and must implement <code>Object.equals</code> to obey
 * the guidelines that are specified for <code>equals</code> methods of
 * {@link Endpoint} instances.
 *
 * @author Sun Microsystems, Inc.
 * @see JettyEndpoint2
 * @since 2.0
 **/
public final class JettyServerEndpoint2 implements ServerEndpoint {

    /**
     * pool of threads for executing tasks in system thread group: used for
     * socket accept threads
     **/
    static final Executor systemThreadPool = (Executor) Security.doPrivileged(new GetThreadPoolAction(false));

    /** server transport logger */
    static final Logger logger = Logger.getLogger("net.jini.jeri.http.server");

    /** local host name to fill in to corresponding HttpEndpoints */
    private final String host;
    /** port to listen on */
    private final int port;
    /** client socket factory used by corresponding HttpEndpoints */
    private final SocketFactory sf;
    /** socket factory used to create server sockets */
    private final ServerSocketFactory ssf;

    /**
     * Returns an <code>HttpServerEndpoint</code> instance for the given TCP
     * port number.
     *
     * <p>
     * The host name contained in the returned <code>HttpServerEndpoint</code>
     * will be <code>null</code>, so that when its
     * {@link #enumerateListenEndpoints enumerateListenEndpoints} method
     * produces an {@link JettyEndpoint2}, the <code>HttpEndpoint</code>'s host
     * name will be the IP address string obtained from
     * {@link InetAddress#getLocalHost InetAddress.getLocalHost}.
     *
     * <p>
     * The <code>ServerSocketFactory</code> contained in the returned
     * <code>HttpServerEndpoint</code> will be <code>null</code>, indicating
     * that this endpoint will create <code>ServerSocket</code> objects
     * directly. The <code>SocketFactory</code> contained in the returned
     * <code>HttpServerEndpoint</code> will also be <code>null</code>.
     *
     * @param port
     *            the TCP port on the local host to listen on
     *
     * @return an <code>HttpServerEndpoint</code> instance
     *
     * @throws IllegalArgumentException
     *             if the port number is out of the range <code>0</code> to
     *             <code>65535</code> (inclusive)
     **/
    public static JettyServerEndpoint2 getInstance(int port) {
        return getInstance(null, port, null, null);
    }

    /**
     * Returns an <code>HttpServerEndpoint</code> instance for the given host
     * name and TCP port number.
     *
     * <p>
     * If <code>host</code> is <code>null</code>, then when the returned
     * <code>HttpServerEndpoint</code>'s {@link #enumerateListenEndpoints
     * enumerateListenEndpoints} method produces an {@link JettyEndpoint2}, the
     * <code>HttpEndpoint</code>'s host name will be the IP address string
     * obtained from {@link InetAddress#getLocalHost InetAddress.getLocalHost}.
     *
     * <p>
     * The <code>ServerSocketFactory</code> contained in the returned
     * <code>HttpServerEndpoint</code> will be <code>null</code>, indicating
     * that this endpoint will create <code>ServerSocket</code> objects
     * directly. The <code>SocketFactory</code> contained in the returned
     * <code>HttpServerEndpoint</code> will also be <code>null</code>.
     *
     * @param host
     *            the host name to be used in <code>HttpEndpoint</code>
     *            instances produced by listening on the returned
     *            <code>HttpServerEndpoint</code>, or <code>null</code>
     *
     * @param port
     *            the TCP port on the local host to listen on
     *
     * @return an <code>HttpServerEndpoint</code> instance
     *
     * @throws IllegalArgumentException
     *             if the port number is out of the range <code>0</code> to
     *             <code>65535</code> (inclusive)
     **/
    public static JettyServerEndpoint2 getInstance(String host, int port) {
        return getInstance(host, port, null, null);
    }

    /**
     * Returns an <code>HttpServerEndpoint</code> instance for the given host
     * name and TCP port number that contains the given
     * <code>SocketFactory</code> and <code>ServerSocketFactory</code>.
     *
     * <p>
     * If <code>host</code> is <code>null</code>, then when the returned
     * <code>HttpServerEndpoint</code>'s {@link #enumerateListenEndpoints
     * enumerateListenEndpoints} method produces an {@link JettyEndpoint2}, the
     * <code>HttpEndpoint</code>'s host name will be the IP address string
     * obtained from {@link InetAddress#getLocalHost InetAddress.getLocalHost}.
     *
     * <p>
     * If the server socket factory argument is <code>null</code>, then this
     * endpoint will create <code>ServerSocket</code> objects directly.
     *
     * @param host
     *            the host name to be used in <code>HttpEndpoint</code>
     *            instances produced by listening on the returned
     *            <code>HttpServerEndpoint</code>, or <code>null</code>
     *
     * @param port
     *            the TCP port on the local host to listen on
     *
     * @param sf
     *            the <code>SocketFactory</code> to use for this
     *            <code>HttpServerEndpoint</code>, or <code>null</code>
     *
     * @param ssf
     *            the <code>ServerSocketFactory</code> to use for this
     *            <code>HttpServerEndpoint</code>, or <code>null</code>
     *
     * @return an <code>HttpServerEndpoint</code> instance
     *
     * @throws IllegalArgumentException
     *             if the port number is out of the range <code>0</code> to
     *             <code>65535</code> (inclusive)
     **/
    public static JettyServerEndpoint2 getInstance(String host, int port, SocketFactory sf, ServerSocketFactory ssf) {
        return new JettyServerEndpoint2(host, port, sf, ssf);
    }

    /**
     * Constructs a new instance.
     **/
    private JettyServerEndpoint2(String host, int port, SocketFactory sf, ServerSocketFactory ssf) {
        if (port < 0 || port > 0xFFFF) {
            throw new IllegalArgumentException("port number out of range: " + port);
        }
        this.host = host;
        this.port = port;
        this.sf = sf;
        this.ssf = ssf;
    }

    /**
     * Returns the host name that will be used in <code>HttpEndpoint</code>
     * instances produced by listening on this <code>HttpServerEndpoint</code>,
     * or <code>null</code> if the IP address string obtained from
     * {@link InetAddress#getLocalHost InetAddress.getLocalHost} will be used.
     *
     * @return the host name to use in <code>HttpEndpoint</code> instances
     *         produced from this object, or <code>null</code>
     **/
    public String getHost() {
        return host;
    }

    /**
     * Returns the TCP port that this <code>HttpServerEndpoint</code> listens
     * on.
     *
     * @return the TCP port that this endpoint listens on
     **/
    public int getPort() {
        return port;
    }

    /**
     * Returns the <code>SocketFactory</code> that <code>HttpEndpoint</code>
     * objects produced by listening on this <code>HttpServerEndpoint</code>
     * will use to create <code>Socket</code> objects.
     *
     * @return the socket factory that client-side endpoints corresponding to
     *         this server endpoint will use to create sockets, or
     *         <code>null</code> if no factory will be used
     **/
    public SocketFactory getSocketFactory() {
        return sf;
    }

    /**
     * Returns the <code>ServerSocketFactory</code> that this endpoint uses to
     * create <code>ServerSocket</code> objects.
     *
     * @return the socket factory that this endpoint uses to create sockets, or
     *         <code>null</code> if no factory is used
     **/
    public ServerSocketFactory getServerSocketFactory() {
        return ssf;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NullPointerException
     *             {@inheritDoc}
     **/
    public InvocationConstraints checkConstraints(InvocationConstraints constraints) throws UnsupportedConstraintException {
        return Constraints.check(constraints, true);
    }

    /**
     * Passes the {@link net.jini.jeri.ServerEndpoint.ListenEndpoint
     * ListenEndpoint} for this <code>HttpServerEndpoint</code> to
     * <code>listenContext</code>, which will ensure an active listen operation
     * on the endpoint, and returns an <code>HttpEndpoint</code> instance
     * corresponding to the listen operation chosen by
     * <code>listenContext</code>.
     *
     * <p>
     * If this <code>HttpServerEndpoint</code>'s host name is not
     * <code>null</code>, then the returned <code>HttpEndpoint</code> will
     * contain that host name. If this <code>HttpServerEndpoint</code>'s host
     * name is <code>null</code>, then this method invokes
     * {@link InetAddress#getLocalHost InetAddress.getLocalHost} to obtain an
     * <code>InetAddress</code> for the local host. If
     * <code>InetAddress.getLocalHost</code> throws an
     * {@link UnknownHostException}, this method throws an
     * <code>UnknownHostException</code>. The returned <code>HttpEndpoint</code>
     * 's host name will be the string returned by invoking
     * {@link InetAddress#getHostAddress getHostAddress} on that
     * <code>InetAddress</code>. If there is a security manager, its
     * {@link SecurityManager#checkConnect(String,int) checkConnect} method will
     * be invoked with the string returned by invoking
     * {@link InetAddress#getHostName getHostName} on that same
     * <code>InetAddress</code> as the host argument and <code>-1</code> as the
     * port argument; this could result in a <code>SecurityException</code>.
     *
     * <p>
     * This method invokes <code>addListenEndpoint</code> on
     * <code>listenContext</code> once, passing a <code>ListenEndpoint</code> as
     * described below. If <code>addListenEndpoint</code> throws an exception,
     * then this method throws that exception. Otherwise, this method returns an
     * <code>HttpEndpoint</code> instance with the host name described above,
     * the TCP port number bound by the listen operation represented by the
     * {@link net.jini.jeri.ServerEndpoint.ListenHandle ListenHandle} returned
     * by <code>addListenEndpoint</code>, and the same
     * <code>SocketFactory</code> as this <code>HttpServerEndpoint</code>.
     *
     * <p>
     * The <code>ListenEndpoint</code> passed to <code>addListenEndpoint</code>
     * represents the TCP port number and <code>ServerSocketFactory</code> of
     * this <code>HttpServerEndpoint</code>. Its methods behave as follows:
     *
     * <p>
     * {@link net.jini.jeri.ServerEndpoint.ListenEndpoint#listen ListenHandle
     * listen(RequestDispatcher)}:
     *
     * <blockquote>
     *
     * Listens for requests received on this endpoint's TCP port, dispatching
     * them to the supplied <code>RequestDispatcher</code> in the form of
     * {@link InboundRequest} instances.
     *
     * <p>
     * When the implementation of this method needs to create a new
     * <code>ServerSocket</code>, it will do so by invoking one of the
     * <code>createServerSocket</code> methods that returns a bound server
     * socket on the contained <code>ServerSocketFactory</code> if non-
     * <code>null</code>, or it will create a <code>ServerSocket</code> directly
     * otherwise.
     *
     * <p>
     * If there is a security manager, its {@link SecurityManager#checkListen
     * checkListen} method will be invoked with this endpoint's TCP port; this
     * could result in a <code>SecurityException</code>. Furthermore, before a
     * given <code>InboundRequest</code> gets dispatched to the supplied request
     * dispatcher, the security manager's {@link SecurityManager#checkAccept
     * checkAccept} method must have been successfully invoked in the security
     * context of this <code>listen</code> invocation with the remote IP address
     * and port of the <code>Socket</code> used to receive the request. The
     * <code>checkPermissions</code> method of the dispatched
     * <code>InboundRequest</code> also performs this latter security check.
     * (Note that in some cases, the implementation may carry out these security
     * checks indirectly, such as through invocations of
     * <code>ServerSocket</code>'s constructors or <code>accept</code> method.)
     *
     * <p>
     * Requests will be dispatched in a {@link PrivilegedAction} wrapped by a
     * {@link SecurityContext} obtained when this method was invoked, with the
     * {@link AccessControlContext} of that <code>SecurityContext</code> in
     * effect.
     *
     * <p>
     * Dispatched requests will implement {@link InboundRequest#populateContext
     * populateContext} to populate the supplied collection with context
     * information representing the request.
     *
     * <p>
     * Throws {@link IOException} if an I/O exception occurs while performing
     * this operation, such as if the TCP port is already in use.
     *
     * <p>
     * Throws {@link SecurityException} if there is a security manager and the
     * invocation of its <code>checkListen</code> method fails.
     *
     * <p>
     * Throws {@link NullPointerException} if <code>requestDispatcher</code> is
     * <code>null</code>
     *
     * </blockquote>
     *
     * <p>
     * {@link net.jini.jeri.ServerEndpoint.ListenEndpoint#checkPermissions void
     * checkPermissions()}:
     *
     * <blockquote>
     *
     * Verifies that the calling context has all of the security permissions
     * necessary to listen for requests on this endpoint.
     *
     * <p>
     * If there is a security manager, its <code>checkListen</code> method will
     * be invoked with this endpoint's TCP port; this could result in a
     * <code>SecurityException</code>.
     *
     * <p>
     * Throws {@link SecurityException} if there is a security manager and the
     * invocation of its <code>checkListen</code> method fails.
     *
     * </blockquote>
     *
     * <p>
     * {@link Object#equals boolean equals(Object)}:
     *
     * <blockquote>
     *
     * Compares the specified object with this <code>ListenEndpoint</code> for
     * equality.
     *
     * <p>
     * This method returns <code>true</code> if and only if
     *
     * <ul>
     *
     * <li>the specified object is also a <code>ListenEndpoint</code> produced
     * by an <code>HttpServerEndpoint</code>,
     *
     * <li>the port in the specified object is equal to the port in this object,
     * and
     *
     * <li>either this object and the specified object both have no
     * <code>ServerSocketFactory</code> or the <code>ServerSocketFactory</code>
     * in the specified object has the same class and is equal to the one in
     * this object.
     *
     * </ul>
     *
     * </blockquote>
     *
     * @param listenContext
     *            the <code>ListenContext</code> to pass this
     *            <code>HttpServerEndpoint</code>'s <code>ListenEndpoint</code>
     *            to
     *
     * @return the <code>HttpEndpoint</code> instance for sending requests to
     *         this <code>HttpServerEndpoint</code>'s endpoint being listened on
     *
     * @throws UnknownHostException
     *             if this <code>HttpServerEndpoint</code>'s host name is
     *             <code>null</code> and <code>InetAddress.getLocalHost</code>
     *             throws an <code>UnknownHostException</code>
     *
     * @throws IOException
     *             if an I/O exception occurs while performing this operation,
     *             such as if the TCP port is already in use
     *
     * @throws SecurityException
     *             if there is a security manager and either the invocation of
     *             its <code>checkListen</code> method fails or this
     *             <code>HttpServerEndpoint</code>'s host name is
     *             <code>null</code> and the invocation of the security
     *             manager's <code>checkConnect</code> method fails
     *
     * @throws IllegalArgumentException
     *             {@inheritDoc}
     *
     * @throws NullPointerException
     *             {@inheritDoc}
     **/
    public Endpoint enumerateListenEndpoints(ListenContext listenContext) throws IOException {

        ListenEndpointImpl listenEndpoint = new ListenEndpointImpl(port, ssf); // REMIND:
                                                                               // needn't
                                                                               // be
                                                                               // new?
        // ListenCookie listenCookie =
        listenContext.addListenEndpoint(listenEndpoint);

        // return new JettyEndPoint("localhost", 8100);
        return JettyEndpoint2.getInstance(host, port, sf);
    }

    /**
     * Returns the hash code value for this <code>HttpServerEndpoint</code>.
     *
     * @return the hash code value for this <code>HttpServerEndpoint</code>
     **/
    public int hashCode() {
        return port ^ (host != null ? host.hashCode() : 0) ^ (sf != null ? sf.hashCode() : 0) ^ (ssf != null ? ssf.hashCode() : 0);
    }

    /**
     * Compares the specified object with this <code>HttpServerEndpoint</code>
     * for equality.
     *
     * <p>
     * This method returns <code>true</code> if and only if
     *
     * <ul>
     *
     * <li>the specified object is also an <code>HttpServerEndpoint</code>,
     *
     * <li>the host and port in the specified object are equal to the host and
     * port in this object,
     *
     * <li>either this object and the specified object both have no
     * <code>SocketFactory</code> or the <code>ServerFactory</code> in the
     * specified object has the same class and is equal to the one in this
     * object, and
     *
     * <li>either this object and the specified object both have no
     * <code>ServerSocketFactory</code> or the <code>ServerSocketFactory</code>
     * in the specified object has the same class and is equal to the one in
     * this object.
     *
     * </ul>
     *
     * @param obj
     *            the object to compare with
     *
     * @return <code>true</code> if <code>obj</code> is equivalent to this
     *         object; <code>false</code> otherwise
     **/
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof JettyServerEndpoint2)) {
            return false;
        }
        JettyServerEndpoint2 other = (JettyServerEndpoint2) obj;
        return Util.equals(host, other.host) && port == other.port && Util.sameClassAndEquals(sf, other.sf) && Util.sameClassAndEquals(ssf, other.ssf);
    }

    /**
     * Returns a string representation of this <code>HttpServerEndpoint</code>.
     *
     * @return a string representation of this <code>HttpServerEndpoint</code>
     **/
    public String toString() {
        return "HttpServerEndpoint[" + (host != null ? host + ":" : "") + port + (ssf != null ? "," + ssf : "") + (sf != null ? "," + sf : "") + "]";
    }

    /**
     * Attempts to set desired socket options for a connected socket
     * (TCP_NODELAY and SO_KEEPALIVE); ignores SocketException.
     **/
    static void setSocketOptions(Socket socket) {
        try {
            socket.setTcpNoDelay(true);
        } catch (SocketException e) {
            if (logger.isLoggable(Levels.HANDLED)) {
                LogUtil.logThrow(logger, Levels.HANDLED, JettyServerEndpoint2.class, "setSocketOptions", "exception setting TCP_NODELAY on socket {0}", new Object[] { socket }, e);
            }
        }
        try {
            socket.setKeepAlive(true);
        } catch (SocketException e) {
            if (logger.isLoggable(Levels.HANDLED)) {
                LogUtil.logThrow(logger, Levels.HANDLED, JettyServerEndpoint2.class, "setSocketOptions", "exception setting SO_KEEPALIVE on socket {0}", new Object[] { socket }, e);
            }
        }
    }
}
