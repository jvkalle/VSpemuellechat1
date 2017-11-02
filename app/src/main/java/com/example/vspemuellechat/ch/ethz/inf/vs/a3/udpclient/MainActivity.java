package com.example.vspemuellechat.ch.ethz.inf.vs.a3.udpclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.vspemuellechat.R;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.MessageComparator;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.MessageTypes;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.queue.PriorityQueue;
import com.example.vspemuellechat.ch.ethz.inf.vs.a3.message.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.UUID;


public class MainActivity extends AppCompatActivity  {

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button button1 = (Button) findViewById(R.id.Join);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendTask();

            }
            public void sendTask() {
                try {
                    SendReceiveTask sendReceiveTask = new SendReceiveTask(new SendReceiveTask.OnCompletedListener() {
                        @Override
                        public void OnCompleted(String result, String type) {
                            if (result != null) {
                                try {
                                    JSONObject resultObj = new JSONObject(result);
                                    if (type == MessageTypes.REGISTER && resultObj.getJSONObject("header").get("type").equals(MessageTypes.ACK_MESSAGE)) {
                                        TextView textView = (TextView) findViewById(R.id.textView);
                                        textView.setText("successful");




                                        //output registered
                                    } else if (type == MessageTypes.REGISTER && !resultObj.getJSONObject("header").get("type").equals(MessageTypes.ACK_MESSAGE)) {
                                        TextView textView = (TextView) findViewById(R.id.textView);
                                        textView.setText("failed");
                                        //output register failed
                                    } else if (type == MessageTypes.DEREGISTER) {
                                        //output deregister
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else if (result == null && counter < 5) {
                                sendTask();
                            } else {
                                TextView textView = (TextView) findViewById(R.id.textView);
                                textView.setText("failed");
                            }

                            //else //registration failed
                        }
                    });
                    counter++;
                    String timestamp = "";
                    String uuid = UUID.randomUUID().toString();
                    String type = MessageTypes.REGISTER;
                    EditText editText = (EditText) findViewById(R.id.input);
                    Editable input = editText.getText();
                    String username = input.toString();
                    JSONObject jsonObject = message(username, uuid, timestamp, type);
                    String message1 = jsonObject.toString(2);
                    sendReceiveTask.execute(message1, uuid, type);
                    RegisterCallback(sendReceiveTask, username);
                } catch (Exception e) { //JSONException e
                    e.printStackTrace();
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText("failed");
                    //registerfailed(); //verändern
                }
            }

            JSONObject message(String username, String uuid, String timestamp, String type) throws JSONException {
                JSONObject jsonObject1 = new JSONObject();
                JSONObject header = new JSONObject();
                header.put("username", username);
                header.put("uuid", uuid);
                header.put("timestamp", timestamp);
                header.put("type", type);

                jsonObject1.put("header", header);
                jsonObject1.put("body", new JSONObject());
                return jsonObject1;

            }
        });

    }


    private void RegisterCallback(SendReceiveTask sendReceiveTask, String username)

    {
        try {
            String uuid = sendReceiveTask.mUUID;
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("uuid", uuid); //Ask server for messages
            intent.putExtra("username", username);
            startActivity(intent);
            return;
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), getString(R.string.retry), Toast.LENGTH_LONG).show();
        }





        //helper
        Button button2 = (Button) findViewById(R.id.Settings);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

    }


}