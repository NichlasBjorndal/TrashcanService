package mdb.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Nichlas Bjørndal
 */
//https://stackoverflow.com/a/7883580
public class DynamicEndpointHelper {

    public static String LOCAL_END_POINT = "http://localhost:8080/DTUPay-0.5/api";
    public static String HOSTED_END_POINT = "http://159.89.18.108:8080/DTUPay-0.5/api";

    /**
     * @return Either localhost or the ip of the tokyo server, depending on where the server is run.
     */
    public static String getCurrentEndpoint() {
        String currentEndpoint;
        String hostname = "Unknown";

        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        } catch (UnknownHostException ex) {
            System.out.println("Hostname can not be resolved");
        }

        if (hostname.contains("tokyo")) {
            currentEndpoint = HOSTED_END_POINT;
        } else {
            currentEndpoint = LOCAL_END_POINT;
        }
        System.out.println("Current endpoint: " + currentEndpoint);
        System.out.println("Current hostname: " + hostname);

        return currentEndpoint;
    }
}
