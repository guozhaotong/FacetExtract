package utils;

import java.util.Properties;

/**
 * @author scriptshi
 * 2018/5/2
 */
public class Proxy {
    public static void start(String ip, String port) {
        Properties prop = System.getProperties();
        prop.put("proxySet", true);
        prop.setProperty("socksProxyHost", ip);
        prop.setProperty("socksProxyPort", port);
        System.setProperty("https.proxyHost", ip);
        System.setProperty("https.proxyPort", port);
        System.setProperty("http.proxyHost", ip);
        System.setProperty("http.proxyPort", port);
    }

    public static void start() {
        Properties prop = System.getProperties();
        prop.put("proxySet", true);
        prop.setProperty("socksProxyHost", "127.0.0.1");
        prop.setProperty("socksProxyPort", "1080");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "1080");
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "1080");
    }
}
