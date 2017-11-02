package com.example.vspemuellechat.ch.ethz.inf.vs.a3.message;

import com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient.SendReceiveTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by valentinkunze on 28.10.17.
 */

public class Message {

    long timestamp;
    public SendReceiveTask sendReceiveTask;
    public String text;
    boolean recievedMsg = false;

    public Message(JSONObject o) throws JSONException{
        try {
                String type = o.getJSONObject("header").getString("type");
                if (!(type.equals("message"))) {
                    recievedMsg = false;
                } else {
                    this.timestamp = sendReceiveTask.result.getLong("timestamp");
                    this.sendReceiveTask = sendReceiveTask;
                    this.text = sendReceiveTask.result.getJSONObject("body").getString("content");
                    recievedMsg = true;
                }
            } catch (JSONException e) {
                recievedMsg = false;
                e.printStackTrace();

            }
        }

   public Message(SendReceiveTask sendReceiveTask) throws JSONException {
        try {
            String type = sendReceiveTask.result.getJSONObject("header").getString("type");
            if (!(type.equals("message"))) {
                recievedMsg = false;
            } else {
                this.timestamp = sendReceiveTask.result.getLong("timestamp");
                this.sendReceiveTask = sendReceiveTask;
                this.text = sendReceiveTask.result.getJSONObject("body").getString("content");
                recievedMsg = true;
            }
        } catch (JSONException e) {
                recievedMsg = false;
                e.printStackTrace();

            }
        }
    }

