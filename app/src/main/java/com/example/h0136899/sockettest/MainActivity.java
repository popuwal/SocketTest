package com.example.h0136899.sockettest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    EditText editText = null;
    Button button = null;
    TextView textView = null;
    Socket socket = null;
    private static final int PORT = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // send msg to server
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            Log.e("WPWPWPWP", "go here");
                            socket = new Socket("192.168.43.23", 10089);
                            socket.setSoTimeout(10000);
                            Log.e("WPWPWPWP", "socket is:"+socket.isConnected()+" "+socket.getLocalAddress().toString());
                            OutputStream outputStream = socket.getOutputStream();
                            outputStream.write(editText.getText().toString().getBytes());
                            outputStream.flush();

                            socket.shutdownOutput();

                            InputStreamReader InputStreamReader = new InputStreamReader(socket.getInputStream());
                            BufferedReader BufferedReader = new BufferedReader(InputStreamReader);
                            String s = "";
                            StringBuffer StringBuffer = new StringBuffer();
                            while ((s = BufferedReader.readLine())!= null) {
                                StringBuffer.append(s);
                            }

                            textView.setText(StringBuffer);

                            socket.shutdownInput();
                            BufferedReader.close();
                            InputStreamReader.close();
                            outputStream.close();
                            socket.close();
                        } catch (IOException e) {
                            Log.e("WPWPWPWP", "e is:"+e.toString());
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        textView = findViewById(R.id.textView);
    }
}
