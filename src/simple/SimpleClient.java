package simple;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args){
        SimpleClient sc = new SimpleClient("{IP}", 15432);
    }

    public SimpleClient(String id, int port){
        try(BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("Connecting. ");

            InetAddress address = InetAddress.getByName(id);
            Socket socket = new Socket(address, port);
            System.out.print("Connection. ");

            //opens a PrintWriter on the socket input autoflush mode
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //opens a BufferedReader on the socket
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Requesting output to: " + address.getHostAddress());
            output.println("Thanks. ");


            while (true) {

                if(sysIn.ready()){
                    String sCmnd = sysIn.readLine();

                    output.println(sCmnd);
                    System.out.println("Client: " + sCmnd);

                    if(sCmnd.equals("stop")) break;

                }

                if(input.ready()){
                    String serverStr = input.readLine();
                    System.out.println("Server: " + serverStr);
                    if(serverStr.equals("stop")) break;
                }

            }

            input.close();
            output.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
