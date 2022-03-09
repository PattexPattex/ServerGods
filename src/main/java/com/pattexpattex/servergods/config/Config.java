package com.pattexpattex.servergods.config;

import net.dv8tion.jda.api.entities.Activity;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

//This class reads all the bot's configs from the config.json file
public class Config {

    //Let the Main class handle the IOException
    public Config() throws IOException {}

    //Some JSON magic
    private JSONObject readConfig() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("config.json"), StandardCharsets.UTF_8))) {

            JSONTokener tokener = new JSONTokener(br);

            return new JSONObject(tokener);
        }
    }

    //Starts reading the config
    private final JSONObject json = readConfig();

    //Create config JSON blocks
    private final JSONObject basicConfig = json.getJSONObject("basic_config");
    private final JSONObject genericRoles = json.getJSONObject("generic_roles");
    private final JSONObject smpConfig = json.getJSONObject("smp_config");
    private final JSONObject enableCommand = json.getJSONObject("enable_command");

    //Config JSON blocks -> Map
    private final Map<String, Object> basicConfigMap = basicConfig.toMap();
    private final Map<String, Object> genericRoleIdMap = genericRoles.toMap();
    private final Map<String, Object> smpConfigMap = smpConfig.toMap();
    private final Map<String, Object> enableCommandsMap = enableCommand.toMap();

    //Method that returns the value of a specified config value,
    //accessed with the usage of enums
    public Object getConfigValue(Enum e) {
        String key = e.toString().toLowerCase();

        if (basicConfigMap.containsKey(key)) {
            return basicConfigMap.get(key);
        }
        else if (genericRoleIdMap.containsKey(key)) {
            return genericRoleIdMap.get(key);
        }
        else if (smpConfigMap.containsKey(key)) {
            return smpConfigMap.get(key);
        }
        else if (enableCommandsMap.containsKey(key)) {
            return enableCommandsMap.get(key);
        }

        //Oops, no worky for you
        else throw new IllegalArgumentException("The key " + e + " is invalid");
    }

    //Activity config
    private final String activity = basicConfig.getString("activity");
    private final String activityType = basicConfig.getString("activity_type");

    public Activity getActivity() {
        Activity ac;

        switch (activityType) {
            case "PLAYING" -> ac = Activity.playing(activity);
            case "WATCHING" -> ac = Activity.watching(activity);
            case "STREAMING" -> ac = Activity.streaming(activity, "https://www.youtube.com/watch?v=dQw4w9WgXcQ%22");
            case "LISTENING" -> ac = Activity.listening(activity);
            case "COMPETING" -> ac = Activity.competing(activity);

            //Yay, even more no worky
            default -> throw new IllegalStateException("Unexpected value: " + activityType);
        }

        return ac;
    }

    //Config enums for easier accessing of the configs and their respected values
    public enum BasicConfig {
        TOKEN,
        PREFIX,
        BOT_PFP,
        ACTIVITY,
        ACTIVITY_TYPE,
        BOT_OWNER,
        WELCOME_CHANNEL,
        DEFAULT_GUILD
    }

    public enum GenericRoles {
        MEMBER,
        DJ,
        LGBT,
        MUTED,
        CC,
        YT,
        TTV,
        MC,
        GTA
    }

    public enum SmpConfig {
        SMP_ROLE,
        SMP_BANNED_ROLE,
        SMP_1_WARN_ROLE,
        SMP_2_WARN_ROLE,
        SMP_ENABLE
    }

    public enum EnableCommand {
        MEME,
        SHUFFLE,
        GRANT,
        ARCHIVE,
        BAN,
        KICK,
        MUTE
    }
}