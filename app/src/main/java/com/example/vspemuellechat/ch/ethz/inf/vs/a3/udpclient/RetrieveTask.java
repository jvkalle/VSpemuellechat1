package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;
import android.os.AsyncTask;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.Message;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.MessageComparator;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.queue.PriorityQueue;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.DatagramPacket;


public class RetrieveTask extends AsyncTask<String,Void, PriorityQueue<Message>> {
    @Override
    protected PriorityQueue<Message> doInBackground(String... params) {
        int numtimeouts = MAX_TIMEOUTS;
        String message = params[0];
        Message msgReply = null;
        JSONObject jsonReply;
        byte[] recvBuf = new byte[256];
        DatagramPacket recvPacket = new DatagramPacket(recvBuf, recvBuf.length);
        byte[] sendBuf = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, ChatHelper.address, NetworkConsts.UDP_PORT);
        PriorityQueue<Message> recvQueue = new PriorityQueue<Message>(new MessageComparator());


        try {
            ChatHelper.socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (numtimeouts > 0) {
            try {
                ChatHelper.socket.receive(recvPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Extract message from packet
            String reply = new String(recvPacket.getData());
            try {
                jsonReply = new JSONObject(reply);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            try {
                msgReply = new Message(jsonReply);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            recvQueue.add(msgReply);
        }
        return recvQueue;
    }

    public interface ResponseHandler{
        void HandleResponse(RetrieveTask task);
    }
    private static final int MAX_TIMEOUTS = 1;
    private ResponseHandler callback;
    public PriorityQueue<Message> result;
    public RetrieveTask(ResponseHandler c){
        this.callback = c;
    }
    @Override
    protected void onPostExecute(PriorityQueue<Message> result) {
        this.result = result;
        this.callback.HandleResponse(this);
    }
}

