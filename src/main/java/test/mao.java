package test;

/**
 * @author 郭朝彤
 * @date 2017/6/21.
 */
class mao implements dog, animal {
    int a = 222;

    String xxx = "x是xxx";

    public void print() {
        System.out.println(111);
    }

    public void p222rint() {
        System.out.println(111);
    }

    public mao() {
        System.out.println("调用默认的");
    }


    public mao(int a) {
        this.a = a;
        System.out.println("调用一个参数的" + a);
    }

    public mao(int a, String xxx) {
        this.a = a;
        this.xxx = xxx;
        System.out.println("调用2个参数的");

    }

    @Override
    public void gou() {

    }

    @Override
    public void eat() {

    }
}
