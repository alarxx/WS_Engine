package simple.ThreadsTest;

public class MultiThreadR implements Runnable{
    Thread thread;
    public MultiThreadR(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {
        while(true) {
            try {
                System.out.println("hello");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
