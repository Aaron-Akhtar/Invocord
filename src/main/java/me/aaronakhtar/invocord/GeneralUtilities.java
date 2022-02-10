package me.aaronakhtar.invocord;

import java.text.SimpleDateFormat;

public class GeneralUtilities {

    public static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static void log(String s){
        System.out.println("["+Invocord.name+"] " + s);
    }

    public static void handleException(Exception e){
        e.printStackTrace();
    }

}
