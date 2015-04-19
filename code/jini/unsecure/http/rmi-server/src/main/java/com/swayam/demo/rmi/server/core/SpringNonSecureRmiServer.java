package com.swayam.demo.rmi.server.core;

import java.util.concurrent.CountDownLatch;

import com.swayam.demo.rmi.server.core.reggie.ReggieStarter;

public class SpringNonSecureRmiServer {

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch signalToStartRmiServer = new CountDownLatch(1);

        Thread reggieStarterThread = new Thread(new ReggieStarter(signalToStartRmiServer));
        reggieStarterThread.setName(ReggieStarter.class.getSimpleName());
        reggieStarterThread.start();

        // Thread rmiServerStarterThread = new Thread(new
        // RMIServerStarter(signalToStartRmiServer));
        // rmiServerStarterThread.setName(RMIServerStarter.class.getSimpleName());
        // rmiServerStarterThread.start();

    }

}
