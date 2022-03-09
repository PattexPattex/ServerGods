package com.pattexpattex.servergods.commands.misc;

import java.util.Map;

import com.pattexpattex.servergods.util.BotCommand;

import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//You get all the help here
public class HelpCommand implements BotCommand {
	
	private Map<String,BotCommand> commands;
	
	public HelpCommand(Map<String,BotCommand> commands) {
		this.commands = commands;
	}
	
	@Override
	public void run(GuildMessageReceivedEvent event, String[] args) {

		User author = event.getAuthor();
		Message message = event.getMessage();

		EmbedBuilder eb = MessageUtils.defaultEmbed("Help page", null, "DEFAULT", null);

		//Lists all the enabled commands in a fancy array in an embed
		commands.forEach((name,cmd) -> eb.addField(name, cmd.getHelp(), false));

		//DMs the message author the help page
		author.openPrivateChannel().queue((channel) -> channel.sendMessage(eb.build()).queue());
		Message reply = message.reply(MessageUtils.defaultEmbed("Help page", ":mailbox_with_mail: Sent you the commands!",
				null, null).build()).complete();

		MessageUtils.deleteMessage(message, 30);
		MessageUtils.deleteMessage(reply, 30);
	}

	@Override
	public String getHelp() {
		return "Displays a list of all commands and their help messages";
	}

	@Override
	public Object isEnabled() {
		return true;
	}
}
