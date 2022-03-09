package com.pattexpattex.servergods.commands.misc;

import com.pattexpattex.servergods.util.BotCommand;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//Gateway ping, simple business
public class PingCommand implements BotCommand {

	@Override
	public void run(GuildMessageReceivedEvent event, String[] args) {
		event.getMessage().reply(":clock8: My ping: " + event.getJDA().getGatewayPing()).queue();
	}

	@Override
	public String getHelp() {
		return "Displays the gateway ping";
	}

	@Override
	public Object isEnabled() {
		return true;
	}

}
