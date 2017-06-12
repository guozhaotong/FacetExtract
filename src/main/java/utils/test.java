package utils;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        String s = StanfordLemmatizer.traffer("properties women");
        System.out.println(s);
    }
}
