package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

public final class NetworkConsts {


    //public NetworkConsts()

    /**
     * UDP Port
     */
    public static int UDP_PORT = 4446;


    /**
     * Address of the chat server
     *
     * This address is for the emulator.
     */
    //public static String SERVER_ADDRESS = "10.0.2.2";
    public static String SERVER_ADDRESS = "192.168.178.37";

    /**
     * Size of UDP payload in bytes
     */
    public static int PAYLOAD_SIZE = 1024;

    /**
     * Time to wait for a message in ms
     */
    public static int SOCKET_TIMEOUT = 10000;
}
