package server;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientsList {

    private List<Client> clients;
    private List<Integer> freePlaces;

    public ClientsList(){
        clients = new ArrayList<>();
        freePlaces = new ArrayList<>();
    }

    public List<Client> getClients(){
        return clients;
    }

    public Client getClient(int id){
        return clients.get(id);
    }

    public int addClient(Socket socket){
        Client client;

        int id = clients.size();

        if(freePlaces.size() > 0){
            id = freePlaces.remove(0);
            client = clients.get(id);
            client.on(socket);
        }
        else {
            client = new Client(this, id, socket);
            clients.add(client);
        }
        return id;
    }
    public void disconnectClient(int id){
        freePlaces.add(id);
    }
}