package model;

import java.util.ArrayList;

/**
 * 这个类用来存储一个领域下，所有主题之间的上下位关系。
 *
 * @author 郭朝彤
 * @date 2017/4/19.
 */
public class AllHyponymy {
    private ArrayList<String> upLocation;
    private ArrayList<String> dnLocation;

    public AllHyponymy(ArrayList<String> upLocation, ArrayList<String> dnLocation) {
        this.upLocation = upLocation;
        this.dnLocation = dnLocation;
    }

    public ArrayList<String> getUpLocation() {
        return upLocation;
    }

    public void setUpLocation(ArrayList<String> upLocation) {
        this.upLocation = upLocation;
    }

    public ArrayList<String> getDnLocation() {
        return dnLocation;
    }

    public void setDnLocation(ArrayList<String> dnLocation) {
        this.dnLocation = dnLocation;
    }

    public AllHyponymy() {
        super();
    }
}
