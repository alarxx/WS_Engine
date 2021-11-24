package server;

import Constants.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MTServer {

    public static void main(String args[]){
        new MTServer();
    }

    public ClientsList clientsList;
    public ConsoleReader consoleReader;

    private final int port = Constants.port;

    public MTServer(){
        clientsList = new ClientsList();
        consoleReader = new ConsoleReader();
        startSession();
    }

    private ServerSocket serverSocket;

    private Socket socket;

    private void startSession(){
        try {
            serverSocket = new ServerSocket(Constants.port);

            String hosts[] = HostAddress.getHostAddresses();
            System.out.println("Server listening on port: " + port + ". IPs: ");
            for(int i=0; i< hosts.length; i++)
                System.out.println("        " + (hosts[i]));

            while(true){//ловим клиентов и отправляем в новый поток
                socket = serverSocket.accept();

                //clients.add(new Client(clients.size(), client));
                int id = clientsList.addClient(socket);

                System.out.println("client " + id +  " connected");
            }
        } catch (Exception e){e.printStackTrace();}
        finally{
            try{
                serverSocket.close();
                consoleReader.close();
            }catch(IOException e){e.printStackTrace();}
        }
    }
}
