package com.thebrokenrail.jmcpil.proxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Proxy
 */
public class Proxy extends Thread {
    private final String ip;
    private final int port;

    private final AtomicBoolean stopping = new AtomicBoolean(false);

    public Proxy(String ip, int port) {
        super();
        this.ip = ip;
        this.port = port;
    }

    public void stopProxy() {
        stopping.set(true);
        try {
            join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean receivePacket(DatagramSocket socket, DatagramPacket packet) {
        try {
            socket.setSoTimeout(10);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        try {
            socket.receive(packet);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Local Client = Local MCPI Connected To Server
    // Local Server = Local Server MCPI Is Connected To
    // Remote Client = The Target Server That This Is Proxying
    @Override
    public void run() {
        DatagramSocket localServer = null;
        DatagramSocket remoteClient = null;
        try {
            System.out.println("Starting Proxy: " + ip + ':' + port);

            // Bind Address
            InetAddress localServerIP;
            try {
                localServerIP = InetAddress.getByName(ip);
                localServer = new DatagramSocket(19133);
                remoteClient = new DatagramSocket();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // Record Last Client IP+Port (Needed To Send Messages To Local Client)
            InetAddress lastAddress = null;
            int lastPort = -1;

            // Run
            while (!stopping.get()) {
                byte[] data = new byte[4096];
                // Receive Server Packets
                DatagramPacket packetFromLocalServer = new DatagramPacket(data, 0, data.length);
                if (receivePacket(localServer, packetFromLocalServer)) {
                    // Check IP (Only The Original Client is Allowed)
                    if (lastAddress == null || lastAddress.equals(packetFromLocalServer.getAddress())) {
                        // Record Last IP+Port
                        lastPort = packetFromLocalServer.getPort();
                        lastAddress = packetFromLocalServer.getAddress();
                        // Proxy Packet
                        DatagramPacket sendingPacket = new DatagramPacket(packetFromLocalServer.getData(), 0, packetFromLocalServer.getLength(), localServerIP, port);
                        try {
                            // Send To Server
                            remoteClient.send(sendingPacket);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                // Check If Local Client Has Connected To Server
                if (lastPort != -1 && lastAddress != null) {
                    // Receive Client Packets
                    DatagramPacket packetFromRemoteClient = new DatagramPacket(data, 0, data.length);
                    if (receivePacket(remoteClient, packetFromRemoteClient)) {
                        DatagramPacket sendingPacket = new DatagramPacket(packetFromRemoteClient.getData(), 0, packetFromRemoteClient.getLength(), lastAddress, lastPort);
                        try {
                            // Send To Client
                            localServer.send(sendingPacket);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        } finally {
            // Stop Proxy And Close Sockets
            System.out.println("Stopping Proxy");
            if (localServer != null && !localServer.isClosed()) {
                localServer.close();
            }
            if (remoteClient != null && !remoteClient.isClosed()) {
                remoteClient.close();
            }
        }
    }
}
