package com.pattexpattex.servergods.commands;

import java.time.OffsetDateTime;
import java.util.Map;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class HelpCommand implements BotCommand{
	
	private Map<String,BotCommand> commands;
	
	public HelpCommand(Map<String,BotCommand> commands) {
		this.commands=commands;
	}
	
	@Override
	public void run(GuildMessageReceivedEvent event, String[] args) {
		EmbedBuilder eb=new EmbedBuilder();
		eb.setFooter("Powered by Server Gods.", Main.pfp)
		.setTimestamp(OffsetDateTime.now())
		.setColor(Main.color)
		.setTitle("Help page")
		.setThumbnail(Main.pfp);
		commands.forEach((name,cmd)->eb.addField(name, cmd.getHelp(), false));
		event.getMessage().reply(eb.build()).queue();
	}

	@Override
	public String getHelp() {
		return "Displays a list of all commands and their help messages";
	}

}
