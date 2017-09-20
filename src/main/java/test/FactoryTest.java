package test;

/**
 * @author 郭朝彤
 * @date 2017/7/6.
 */
public class FactoryTest {
    private String name;
    private String sex;

    private FactoryTest(String name) {
        this.name = name;
    }

    private FactoryTest(String name, String sex) {
        this(name);
        this.sex = sex;
    }

    public static FactoryTest creat() {
        return new FactoryTest();
    }

    public FactoryTest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
