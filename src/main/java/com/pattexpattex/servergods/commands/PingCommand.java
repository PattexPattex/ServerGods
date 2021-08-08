package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PingCommand implements BotCommand{

	@Override
	public void run(GuildMessageReceivedEvent event, String[] args) {
		event.getMessage().reply(":clock8: My ping: "+event.getJDA().getGatewayPing()).queue();
	}

	@Override
	public String getHelp() {
		return "Displays the gateway ping";
	}
	
}
