package com.pattexpattex.servergods;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.config.MemesConfig;
import com.pattexpattex.servergods.events.MemberJoinEvent;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.Color;

public class Main {

	public static final Logger LOG = LoggerFactory.getLogger(Main.class);
	public static Config CONFIG;
	public static MemesConfig MEMES_CONFIG;
	public static JDA jda;

	public static Color COLOR = new Color(0, 90, 112);
	public static final Color ERROR_COLOR = new Color(250, 75, 75);
	
	public static void main(String[] args) {

		//Start the JDA client
		try {
			CONFIG = new Config();
			MEMES_CONFIG = new MemesConfig();

		jda = JDABuilder.createDefault((String) CONFIG.getConfigValue(Config.BasicConfig.TOKEN)) //Bot token
				.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
				.addEventListeners(new EventListener((String) CONFIG.getConfigValue(Config.BasicConfig.PREFIX)), //Bot prefix
						new MemberJoinEvent())
				.disableCache(CacheFlag.VOICE_STATE, CacheFlag.EMOTE)
				.setActivity(CONFIG.getActivity())
				.setStatus(OnlineStatus.IDLE)
				.build();

		} catch (Exception e) {LOG.error(null, e);}
	}
}
