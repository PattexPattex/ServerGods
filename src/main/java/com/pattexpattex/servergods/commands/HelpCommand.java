package com.pattexpattex.servergods.commands;

import java.time.OffsetDateTime;
import java.util.Map;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;

import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class HelpCommand implements BotCommand{
	
	private Map<String,BotCommand> commands;
	
	public HelpCommand(Map<String,BotCommand> commands) {
		this.commands=commands;
	}
	
	@Override
	public void run(GuildMessageReceivedEvent event, String[] args) {
		User author = event.getAuthor();
		Message message = event.getMessage();

		EmbedBuilder heb = MessageUtils.defaultEmbed("Help page", null, "DEFAULT", null);
		commands.forEach((name,cmd) -> heb.addField(name, cmd.getHelp(), false));

		author.openPrivateChannel().queue((channel) -> channel.sendMessage(heb.build()).queue());
		Message reply = message.reply(MessageUtils.defaultEmbed("Help page", ":mailbox_with_mail: Sent you the commands!", null, null).build()).complete();

		MessageUtils.deleteMessage(message, 30L);
		MessageUtils.deleteMessage(reply, 30L);
	}

	@Override
	public String getHelp() {
		return "Displays a list of all commands and their help messages";
	}
}
