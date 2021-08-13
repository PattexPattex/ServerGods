package com.pattexpattex.servergods;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.pattexpattex.servergods.commands.*;
import com.pattexpattex.servergods.commands.status.*;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
	private static final Pattern SPACE_PATTERN=Pattern.compile("\\s");
	
	private final String prefix;
	
	private Map<String, BotCommand> commands = new HashMap<>();
	
	public CommandListener(String prefix) {
		this.prefix = prefix;
		commands.put("ping", new PingCommand());
		commands.put("help", new HelpCommand(commands));
		commands.put("offline", new OfflineCommand());
		commands.put("online", new OnlineCommand());
		commands.put("maintenance", new MaintenanceCommand());
		commands.put("restarting", new RestartCommand());
		commands.put("player", new PlayerCommand());
		commands.put("stop", new StopCommand());
		commands.put("warn", new WarnCommand());
		commands.put("meme", new MemeCommand());
		commands.put("archive", new ArchiveCommand());
		commands.put("invite", new InviteCommand());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();
		if(msg.getAuthor().isBot()) {
			return;
		}
		String raw = msg.getContentRaw();
		if(!raw.startsWith(prefix)) {
			return;
		}
		String messageWithoutPrefix=raw.substring(prefix.length());
		String[] split=SPACE_PATTERN.split(messageWithoutPrefix);
		String commandName=split[0];
		BotCommand cmd = commands.get(commandName);
		if(cmd==null) {
			msg.reply(MessageUtils.defaultEmbed(null, ":noo: Command not found!", null, null).build()).queue();
		}else {
			String[] args=Stream.of(split).skip(1).toArray(String[]::new);
			cmd.run(event, args);
		}
		
	}
}
