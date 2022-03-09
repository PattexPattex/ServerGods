package com.pattexpattex.servergods.util;

import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.config.Config;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface BotCommand {
	Config CONFIG = Main.CONFIG;

	void run(GuildMessageReceivedEvent event, String[] args);
	String getHelp();
	Object isEnabled();
}
