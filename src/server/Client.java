package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client extends Thread{

    int id;
    boolean isConnected = false;
    private ClientsList clientList;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    private long lastTime;
    private long sec = 1000000000l;

    public Client(ClientsList clientsList, int id, Socket socket){
        super();
        this.id = id;
        this.clientList = clientsList;
        this.socket = socket;
        this.start();
    }

    @Override
    public void run() {
        isConnected = initIO(socket);
        lastTime = System.nanoTime();

        while (true) {
            checkConnection();
            read();
            updateTime();
            try {
                sleep(1000/120);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateTime(){
        if(System.nanoTime() - lastTime > sec * 20l) off();
    }

    private synchronized void checkConnection(){
        while(!isConnected) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void read(){
        try {
            while(input.ready()) {
                lastTime = System.nanoTime(); //Фиксируем время последнего действия
                String inString = input.readLine(); // read the command from the client
                System.out.println("Client: " + inString);
                if(inString.equals("stop")) {
                    off();
                    break;
                }
                else if(inString.equals("here?")){
                    out("here");
                    //System.out.println("here");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void out(String message){
        lastTime = System.nanoTime(); //Фиксируем время последнего действия
        output.println(message);
    }

    public boolean initIO(Socket socket) {
        try {
            this.socket = socket;
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch(Exception e){
            e.printStackTrace();
            off();
            return false;
        }
    }

    public synchronized void on(Socket socket){ //Restore execution (thread)
        isConnected = initIO(socket);
        lastTime = System.nanoTime(); //Фиксируем время последнего действия
        notify();
    }

    public void off(){ //Pause execution (thread)
        System.out.println("client " + id + " disconnected");
        //Нужно дать знать в ClientList, что мы закрываемся
        clientList.disconnectClient(id);
        close();
        isConnected = false;
    }

    public void close(){
        try{
            if(socket != null) socket.close();
            if(input != null) input.close();
            if(output != null) output.close();
        }catch (IOException e){e.printStackTrace();}
    }
}
