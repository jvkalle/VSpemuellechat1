package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

/**
 * Created by valentinkunze on 25.10.17.
 */

public class SendReceiveTask extends AsyncTask<String, Void, String> {

    public interface OnCompletedListener {
        void OnCompleted(String result, String type);
    }

    public interface ResponseHandler {
        void HandleResponse(SendReceiveTask task, Object... passthrough);
    }

    public Object[] passthrough;
    private ResponseHandler callback;

    public SendReceiveTask(ResponseHandler callback, Object... passthrough) {
        super();
        this.passthrough = passthrough;
        this.callback = callback;
    }

    private OnCompletedListener listener;

    public SendReceiveTask(OnCompletedListener listener) {
        this.listener = listener;
    }

    public JSONObject result = null;
    String type1;
    long timestamp;
    boolean timedOut = false;
    String mUUID =  UUID.randomUUID().toString();

    @Override
    protected String doInBackground(String... params) {
        try{

        timestamp = currentTimeMillis();
            /*
            NetworkConsts networkConsts = new NetworkConsts();
            SettingsActivity.Settings settings = new Settings();
            */

        String message = String.format(params[0], params[1]);
        type1 = params[2];
        byte[] sendBuffer = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length,  InetAddress.getByName(NetworkConsts.SERVER_ADDRESS), NetworkConsts.UDP_PORT);
        byte[] receiveBuffer = new byte[256];
        DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            DatagramSocket datagramSocket = new DatagramSocket(NetworkConsts.UDP_PORT);
            datagramSocket.setSoTimeout(NetworkConsts.SOCKET_TIMEOUT);
            datagramSocket.send(sendPacket);
            datagramSocket.receive(receivePacket);
            String received = new String(receivePacket.getData());
            datagramSocket.close();
            return received;
        } catch (SocketTimeoutException se) {
            se.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) {
        this.listener.OnCompleted(result, type1);
    }

    }





