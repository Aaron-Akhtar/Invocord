package me.aaronakhtar.invocord;

public class GeneralUtilities {

    public static void log(String s){
        System.out.println("["+Invocord.name+"] " + s);
    }

    public static void handleException(Exception e){
        e.printStackTrace();
    }

}
