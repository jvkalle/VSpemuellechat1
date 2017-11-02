package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vspemuellechat.R;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.Message;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.MessageTypes;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.queue.PriorityQueue;

import org.json.JSONException;

public class ChatActivity extends AppCompatActivity implements SendReceiveTask.ResponseHandler, RetrieveTask.ResponseHandler {
    private String username;
    private String uuid;
    @Override
    protected void onCreate(Bundle instanceState){
        super.onCreate(instanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        uuid = intent.getStringExtra("uuid");
        TextView textView = (TextView) findViewById(R.id.ChatName);
        textView.setText("User: ".concat(username));
    }

    public void onRetrieveClick(View view){
        TextView textView = (TextView) findViewById(R.id.ChatWindow);
        textView.setText("Connecting to the server...");
        RetrieveTask retrieveTask = new RetrieveTask(this);
        try{
            String retrieveMsg = ChatHelper.JSONmessage(uuid,username, MessageTypes.RETRIEVE_CHAT_LOG);
            retrieveTask.execute(retrieveMsg);
        } catch(JSONException e){
            e.printStackTrace();
            textView.setText("Retrieval failed");
            return;
        }
    }
    @Override
    public void HandleResponse(RetrieveTask retrieveTask) {
        TextView textView = (TextView) findViewById(R.id.ChatWindow);
        textView.setText("     \n");
        PriorityQueue<Message> queue = retrieveTask.result;
        if(queue == null) {
            textView.setText("No messages retrieved");
        } else {
            while(queue.peek() != null){
                textView.append(queue.poll().text + "\n     \n");
            }
        }
    }

    private void deregisterCallback(SendReceiveTask sendReceiveTask) {
        Toast.makeText(getApplicationContext(),getString(R.string.deregistered), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        ChatHelper.deregister(username, uuid,this);
        super.onBackPressed();
    }

    @Override
    public void HandleResponse(SendReceiveTask sendReceiveTask, Object... passthrough) {
        if ((int) sendReceiveTask.passthrough[0] == ChatHelper.DEREGISTER_REQUEST)
            deregisterCallback(sendReceiveTask);
        else
            throw new UnknownError();
    }
}
