package com.pattexpattex.servergods.util;

import net.dv8tion.jda.api.entities.Activity;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Config {

    public Config() throws IOException {}

    //private Logger LOG = LoggerFactory.getLogger(Config.class);

    JSONObject json = readConfig();
    String token = json.getString("token");
    String prefix = json.getString("prefix");
    String activity = json.getString("activity");
    String activityType = json.getString("activityType");

    private JSONObject readConfig() throws IOException {
        try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("config.json"), StandardCharsets.UTF_8))){
            JSONTokener tokener=new JSONTokener(br);
            return new JSONObject(tokener);
        }
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }
    public Activity getActivity() {
        Activity ac;

        if (Objects.equals(activityType, "PLAYING")) {
            ac = Activity.playing(activity);
        } else if (Objects.equals(activityType, "WATCHING")) {
            ac = Activity.watching(activity);
        } else if (Objects.equals(activityType, "LISTENING")) {
            ac = Activity.listening(activity);
        } else if (Objects.equals(activityType, "COMPETING")) {
            ac = Activity.competing(activity);
        } else if (Objects.equals(activityType, "STREAMING")) {
            ac = Activity.streaming(activity, "");
        } else {
            ac = Activity.playing(activity);
        }

        return ac;
    }
}
