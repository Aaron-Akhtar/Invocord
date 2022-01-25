package me.aaronakhtar.invocord.configuration;

import me.aaronakhtar.blockonomics_wrapper.objects.transaction.CallbackTransaction;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigurationUtilities {

    // returns true if its created a new file, and returns false if the file already existed
    public static boolean checkConfigurationFile(File configurationFile) throws IOException {
        if (!ConfigurationMain.configurationDirectory.isDirectory()) {
            ConfigurationMain.configurationDirectory.mkdirs();
            configurationFile.createNewFile();
            return true;
        }else if (!configurationFile.exists()){
            configurationFile.createNewFile();
            return true;
        }
        return false;
    }

}
