package client_android;/*package com.rat6.ict_messenger_android;

import static com.rat6.ict_messenger_android.MainActivity.IP;
//import static com.rat6.ict_messenger_android.MainActivity.online;
import static com.rat6.ict_messenger_android.MainActivity.port;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleClient extends Thread{

    private boolean online = false;

    private PrintWriter output;
    private BufferedReader input;

    private Socket socket;

    private List<String> queue;

    private long lastTime;
    private boolean isConnected = false;
    private long secNano = 1000000000l;
    private long secMilli = 1000;

    public SimpleClient(){
        super();
        queue = new ArrayList<>();
        lastTime = System.nanoTime();
        start();
    }

    @Override
    public void run(){
        try{
            isConnected = initIO();
            Log.d("SERVER", "initialized " + isConnected);

            while (online) {
                if(!online) disconnect();

                checkConnection(); //Если соединение прервано, переподключаемся
                confirmTimeConnection(); //Каждые 10 секунд убеждаемся в подключении
                read(); //Читаем данные сервера
                execute(); //Выполнение команд из очереди
                sleep(secMilli / 60);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
            close();
        }
    }

    private synchronized void checkConnection(){
        long lastT;

        if(isConnected) return;

        lastT = System.nanoTime() - secNano;
        while(!isConnected && System.nanoTime() - lastT > secNano) {
            isConnected = initIO();
            lastT = System.nanoTime();
        }
    }

    private void confirmTimeConnection(){
        if(System.nanoTime() - lastTime > 10l * secNano){//10 sec
            output.println("here?");
            waitForConnection();
        }
    }
    private void waitForConnection(){
        long lastT = System.nanoTime();
        while(System.nanoTime() - lastT < 5l * secNano){  //30 sec
            if(read()){
                if(queue.get(queue.size()-1).equals("here")){
                    queue.remove(queue.size()-1);
                    return;
                }
            }
        }
        disconnect();
    }

    public boolean read(){
        try {
            while(input.ready()) {
                String serverStr = input.readLine();
                Log.d("SERVER", "Server: " + serverStr);
                if(serverStr.equals("stop"))
                    break;
                queue.add(serverStr);
                lastTime = System.nanoTime();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void execute(){
        for(int i = 0; i < queue.size(); i++) {
            String command = queue.remove(0);
            //if(command.equals("stop")){}
        }
    }

    private void disconnect(){
        //queue.add("stop");
        Log.d("SERVER", "DISCONNECT");
        isConnected = false;
        output.println("stop");
        close();
    }

    public boolean initIO(){
        try {
            InetAddress address = InetAddress.getByName(IP);
            socket = new Socket(address, port);
            Log.d("SERVER", "Requesting output to: " + address.getHostAddress());
            //opens a PrintWriter on the socket input autoflush mode
            output = new PrintWriter(socket.getOutputStream(), true);

            //opens a BufferedReader on the socket
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e){
            e.printStackTrace();
            close();
            return false;
        }
    }

    private void close(){
        try {
            if(output != null) output.close();
            if(input!= null) input.close();
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

 */
