package simple.ThreadsTest;

import java.util.ArrayDeque;
import java.util.Queue;

public class Test {
    public static void main(String[] args){
        System.out.println("START");

        Queue<Integer> q = new ArrayDeque<>();

        q.add(1);
        q.add(2);
        q.add(3);

        System.out.println(q.remove());

    }
}
