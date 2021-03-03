package com.thebrokenrail.mcpil.proxy;

/**
 * Global Proxy Instance
 */
public class GlobalProxy {
    private static Proxy proxy = null;

    /**
     * Start Global Proxy Instance
     * @param ip IP Address
     * @param port Port
     */
    public static void start(String ip, int port) {
        if (proxy != null) {
            proxy.stopProxy();
        } else {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (proxy != null) {
                    proxy.stopProxy();
                }
            }));
        }
        proxy = new Proxy(ip, port);
        proxy.start();
    }
}
