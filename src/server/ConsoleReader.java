package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/***
 * Отдельный поток проверяеющий ввод с консоли на стороне сервера
 */
public class ConsoleReader implements Runnable{
    Thread thread;
    boolean ex = false;

    public ConsoleReader(){
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run(){
        ex = true;
        try (BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in))) {
            // read the command from the client
            while  (ex) {
                if(sysIn.ready()){
                    String sCmnd = sysIn.readLine();
                    //Нужно что-то делать с этим значением
                    if(sCmnd.equals("stop")) break;
                }
            }
        } catch (Exception e){e.printStackTrace();}
        finally{
            //try{}catch(IOException e){e.printStackTrace();}
        }
    }

    public void close(){
        ex = false;
    }
}
