package test;

/**
 * @author 郭朝彤
 * @date 2017/7/5.
 */
public class ThreadTest extends Thread {

    public ThreadTest(String a) {
        this.a = a;
    }

    public String a;

    private static Integer count = 0;


    public void run() {
        synchronized (ThreadTest.class) {
            for (int i = 0; i < 100000; i++) {

                count = count + 1;
            }
        }


        System.out.println(count);
    }

    public static void main(String[] aaa) {
        new ThreadTest("校徽").start();
        new ThreadTest("小哥").start();
        System.out.println("done");
    }
}
