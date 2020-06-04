package main;

import java.io.FileReader;
import java.util.Properties;
import app.LoginFrame;
public class Main {
    public static String serverIP;
    public static int port;
    private static void loadConf()throws Exception{
        Properties pps=new Properties();
        pps.load(new FileReader("net.conf"));
        serverIP=pps.getProperty("serverIP");
        port=Integer.parseInt(pps.getProperty("port"));
    }
    public static void main(String[] args) throws Exception{
        loadConf();
        new LoginFrame();
    }
}
