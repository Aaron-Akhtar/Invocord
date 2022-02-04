package me.aaronakhtar.invocord.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aaronakhtar.invocord.GeneralUtilities;
import me.aaronakhtar.invocord.Invocord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationMain {

    //todo - create persistent file writer objects rather than creating new ones.

    public static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static final File configurationDirectory = new File("./" + Invocord.name);
    public static final File mainConfigurationFile = new File(configurationDirectory.getAbsolutePath() + "/config.json");

    public static boolean setupMainConfiguration(){
        try(FileWriter fileWriter = new FileWriter(mainConfigurationFile)){
            gson.toJson(new ConfigurationMain(), fileWriter);
            fileWriter.flush();
            return true;
        }catch (Exception e){
            GeneralUtilities.handleException(e);
        }
        return false;
    }

    public static ConfigurationMain getMainConfiguration(){
        try {
            ConfigurationUtilities.checkConfigurationFile(mainConfigurationFile);
            try (FileReader fileReader = new FileReader(mainConfigurationFile)) {
                return gson.fromJson(fileReader, ConfigurationMain.class);
            } catch (Exception e) {
                GeneralUtilities.handleException(e);
            }
        }catch (Exception e){
            GeneralUtilities.handleException(e);
        }
        return null;
    }

    private String blockonomicsApiKey = "";
    private String discordBotToken = "";
    private String secretKey = "";
    private int port = 23019;


    public String getSecretKey() {
        return secretKey;
    }

    public int getPort() {
        return port;
    }

    public String getBlockonomicsApiKey() {
        return blockonomicsApiKey;
    }

    public String getDiscordBotToken() {
        return discordBotToken;
    }
}
