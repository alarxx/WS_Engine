package client_android.server;/*
package com.rat6.ict_messenger_android.server;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection implements Runnable{
    private final long oneSecNano = 1000000000l;

    private static boolean running;
    private static boolean inputIsReady;
    private boolean access;

    private String IP;
    private int port;

    private PrintWriter output;
    private BufferedReader input;

    private Socket socket;

    private Thread t;

    private long lastActionTime;

    private List<String> queueRead;
    private List<String> queueWrite;

    synchronized void addQueueWrite(String str){
        queueWrite.add(str);
    }

    public Connection(String IP, int port){
        this.IP = IP;
        this.port = port;

        running = true;
        access = false;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run(){
        queueRead = new ArrayList<>();
        queueWrite = new ArrayList<>();

        do{
            access = initIO();
        }while(!access);

        inputThread();
        executeReadThread(this);
        action();

        long askedTime = 0;

        while(running) {
            //Здесь надо переподключать.
            if (access && System.nanoTime() - lastActionTime > 5 * oneSecNano) {
                addQueueWrite("here?"); //Сервер должен откликнуться, если приходит ответ, то action(), а с тем, что пришло ничего не делаем
                lastActionTime = System.nanoTime();
                askedTime = System.nanoTime();
                access = false;
            }
            if (!access && System.nanoTime() - askedTime > 2 * oneSecNano) {
                closeIO();
                access = initIO();
            }

            updateOutput();
        }
        closeIO();
    }

    private void updateOutput(){
        for(int i=0; i<queueWrite.size(); i++) {
            String str;
            synchronized (queueWrite) {
                str = queueWrite.remove(0);
            }
            output.println(str);
            Log.d("SERVER", "send: " + str);
        }
    }

    public void serverConfirmation(){
        access = true;
    }

    public void turnOff(){
        running = false;
    }

    public void action(){
        lastActionTime = System.nanoTime();
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
            inputIsReady = true;
            return true;
        } catch (Exception e){
            e.printStackTrace();
            closeIO();
            return false;
        }
    }

    private void closeIO(){
        try {
            if(output != null){
                output.println("stop");
                output.close();
            }
            if(input!= null) {
                inputIsReady = false;
                input.close();
            }
            if(socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //In general, you can make separate classes
    //Читает сообщения(команды) с сервера и кладет их очередь на исполнение
    private void inputThread(){
        new Thread( new Runnable() {
            @Override
            public void run() {
                while(running){
                    try {
                        if(!inputIsReady) continue;
                        if(input.ready()){
                            String serverStr = input.readLine();
                            synchronized (queueRead) {
                                queueRead.add(serverStr);
                            }
                            Log.d("SERVER", "Answer: " + serverStr);
                            action();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        ).start();
    }

    //исполняет очередь сообещений(команд)
    private void executeReadThread(Connection connection){
        new Thread( new Runnable() {
            CommandHandler handler = new CommandHandler(connection);
            @Override
            public void run() {
                while(running){
                    for(int i=0; i<queueRead.size(); i++) {
                        String command = "";
                        synchronized (queueRead){
                            command = queueRead.remove(0);
                        }
                        handler.process(command);
                    }
                }
            }
        }
        ).start();
    }
}

 */

