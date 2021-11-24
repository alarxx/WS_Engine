package simple.ThreadsTest;

public class MultiThreadT extends Thread{
    String s;
    public MultiThreadT(String s){
        super();
        this.s = s;
        start();
    }

    @Override
    public void run(){
        while(true) {
            try {
                System.out.println(s);
                this.sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
