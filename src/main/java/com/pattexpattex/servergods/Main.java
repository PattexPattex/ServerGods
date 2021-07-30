package com.pattexpattex.servergods;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.awt.Color;

import javax.security.auth.login.LoginException;

import com.pattexpattex.servergods.events.MemberJoinEvent;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

public class Main {
	
	private static Logger LOG=LoggerFactory.getLogger(Main.class);

	public static final Color color = new Color(84, 130, 53);
	
	public static void main(String[] args) {
		try {
			JSONObject json=readConfig();
			start(json.getString("token"), json.getString("prefix"));
		} catch (IOException e) {
			LOG.error("Cannot read config.json",e);
		} catch (JSONException e) {
			LOG.error("The file config.json does not contain the necessary elements 'token' and 'prefix'.");
		} catch (LoginException e) {
			LOG.error("Cannot login - make sure the token is correct");
		} catch (IllegalArgumentException e) {
			LOG.error("No token was entered.");
		} 
	}
	
	private static JSONObject readConfig() throws IOException {
		try(BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream("config.json"),StandardCharsets.UTF_8))){
			JSONTokener tokener=new JSONTokener(br);
			return new JSONObject(tokener);
		} 
	}
	
	private static void start(String token,String prefix) throws LoginException, IllegalArgumentException {
		DefaultShardManagerBuilder.createDefault(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
				.addEventListeners(new CommandListener(prefix), new MemberJoinEvent())
				.disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
				.setActivity(Activity.playing("Server Gods 1.1.0-SNAPSHOT"))
				.setStatus(OnlineStatus.IDLE)
				.build();
		
	}
}
