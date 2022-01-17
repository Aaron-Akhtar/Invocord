package me.aaronakhtar.invocord.configuration;

import com.google.gson.Gson;
import me.aaronakhtar.invocord.Invocord;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigurationMain {

    protected static final Gson gson = new Gson();
    protected static final File configurationDirectory = new File("./" + Invocord.name);
    public static final File mainConfigurationFile = new File(configurationDirectory.getAbsolutePath() + "/config.json");

    // returns true if its created a new file, and returns false if the file already existed
    public static boolean checkConfigurationFile(File configurationFile) throws IOException {
        if (!ConfigurationMain.configurationDirectory.isDirectory()) {
            configurationDirectory.mkdirs();
            configurationFile.createNewFile();
            return true;
        }else if (!configurationFile.exists()){
            configurationFile.createNewFile();
            return true;
        }
        return false;
    }

    public static boolean setupMainConfiguration(){
        try(FileWriter fileWriter = new FileWriter(mainConfigurationFile)){
            gson.toJson(new ConfigurationMain(), fileWriter);
            fileWriter.flush();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static ConfigurationMain getMainConfiguration(){
        try {
            checkConfigurationFile(mainConfigurationFile);
            try (FileReader fileReader = new FileReader(mainConfigurationFile)) {
                return gson.fromJson(fileReader, ConfigurationMain.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String blockonomicsApiKey = "";
    private String discordBotToken = "";

    public String getBlockonomicsApiKey() {
        return blockonomicsApiKey;
    }

    public String getDiscordBotToken() {
        return discordBotToken;
    }
}
