package com.pattexpattex.servergods.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class MemesConfig {

    public MemesConfig() throws IOException {}

    //Some JSON magic
    private JSONObject readConfig() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("memes.json"), StandardCharsets.UTF_8))) {

            JSONTokener tokener = new JSONTokener(br);
            return new JSONObject(tokener);
        }
    }

    //Creates the JSONObject to read the config from
    JSONObject json = readConfig();

    //The array of links to the memes
    public JSONArray memesJsonArray = json.getJSONArray("memes");

    public ArrayList<String> getMemes() {
        ArrayList<String> memes = new ArrayList<String>();

        for (int i = 0; i < memesJsonArray.length(); i++) {
            String meme = memesJsonArray.getString(i);
            memes.add(meme);
        }
        return memes;
    }

    public String getMeme(int i) {
        return getMemes().get(i);
    }

    //Array of all the titles for the memes
    public JSONArray titlesJsonArray = json.getJSONArray("titles");

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<String>();

        for (int i = 0; i < titlesJsonArray.length(); i++) {
            String meme = titlesJsonArray.getString(i);
            titles.add(meme);
        }
        return titles;
    }

    public String getTitle(int i) {
        return getTitles().get(i);
    }
}
