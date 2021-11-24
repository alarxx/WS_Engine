package simple;

import server.HostAddress;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    private ServerSocket serverSocket;
    private Socket client;

    private final int port = 15432;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args) {
        SimpleServer server = new SimpleServer();
        server.startSession();
    }

    private void startSession(){
        try (BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in))) {

            serverSocket = new ServerSocket(port); //Запуск на своем IP
            String hosts[] = HostAddress.getHostAddresses(); //Получаем IP к которому может подключиться клиент
            System.out.println("Server listening on port: "+port+". IPs: ");
            for(int i=0; i< hosts.length; i++) System.out.println("        " + (hosts[i]));

            client = serverSocket.accept();
            System.out.println("client connected.");

            output = new PrintWriter(client.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            System.out.println("Reader and writer created. ");
            System.out.println("Server: " + "welcome");
            output.println("welcome");

            while  (true) {
                if(sysIn.ready()){ //Send to client an input from console
                    String sCmnd = sysIn.readLine();
                    System.out.println("Server: " + sCmnd);
                    output.println(sCmnd);
                    if(sCmnd.equals("stop")) break;
                }
                if (input.ready()) { //get clients request
                    String inString = input.readLine();
                    if(inString.equals("stop")) break;
                    System.out.println("Client: " + inString);
                }
            }
        } catch (Exception e){e.printStackTrace();}
        finally{
            try{
                input.close();
                output.close();
                client.close();
                serverSocket.close();
            }catch(IOException e){e.printStackTrace();}
        }
    }
}
