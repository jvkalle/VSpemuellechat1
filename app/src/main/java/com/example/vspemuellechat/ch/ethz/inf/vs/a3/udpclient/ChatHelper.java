package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.MessageTypes;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ChatHelper {
    public static DatagramSocket socket;
    public static InetAddress address;
    public static final int REGISTER_REQUEST = 0;
    public static final int DEREGISTER_REQUEST = 1;

    public static String JSONmessage(String uuid, String username, String timestamp, String type, JSONObject Body) throws JSONException {
        JSONObject header = new JSONObject();
        JSONObject obj = new JSONObject();
        header.put("uuid", uuid);
        header.put("username", username);
        header.put("timestamp", timestamp);
        header.put("type", type);
        obj.put("header", header);
        obj.put("body", Body);
        return obj.toString();
    }


    public static synchronized boolean makeDatagramSocket() {
        if (socket == null) {
            try {
                socket = new DatagramSocket(NetworkConsts.UDP_PORT);
                socket.setSoTimeout(NetworkConsts.SOCKET_TIMEOUT);
                address = InetAddress.getByName(NetworkConsts.SERVER_ADDRESS);
            } catch (SocketException e) {
                e.printStackTrace();
                return false;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String JSONmessage(String uuid, String username, String type) throws JSONException {
        return JSONmessage(uuid, username, new JSONObject().toString(), type, new JSONObject());
    }

    public static void deregister(String username, String uuid, SendReceiveTask.ResponseHandler handler) {
        SendReceiveTask sendReceiveTask = new SendReceiveTask(handler, ChatHelper.DEREGISTER_REQUEST);
        try {
            String message = ChatHelper.JSONmessage(uuid, username, MessageTypes.DEREGISTER);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
