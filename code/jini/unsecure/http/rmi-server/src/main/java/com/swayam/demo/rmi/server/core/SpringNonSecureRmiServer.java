package com.swayam.demo.rmi.server.core;

import java.util.concurrent.CountDownLatch;

import com.swayam.demo.rmi.server.core.http.JettyServerStarter;
import com.swayam.demo.rmi.server.core.reggie.ReggieStarter;
import com.swayam.demo.rmi.server.core.rmi.RMIServerStarter;

public class SpringNonSecureRmiServer {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch signalToStartReggie = new CountDownLatch(1);

        Thread jettyStarterThread = new Thread(new JettyServerStarter(signalToStartReggie));
        jettyStarterThread.setName(JettyServerStarter.class.getSimpleName());
        jettyStarterThread.start();

        CountDownLatch signalToStartRmiServer = new CountDownLatch(1);

        Thread reggieStarterThread = new Thread(new ReggieStarter(signalToStartReggie, signalToStartRmiServer));
        reggieStarterThread.setName(ReggieStarter.class.getSimpleName());
        reggieStarterThread.start();

        Thread rmiServerStarterThread = new Thread(new RMIServerStarter(signalToStartRmiServer));
        rmiServerStarterThread.setName(RMIServerStarter.class.getSimpleName());
        rmiServerStarterThread.start();

    }

}
