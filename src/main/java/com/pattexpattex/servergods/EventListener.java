package com.pattexpattex.servergods;

import com.pattexpattex.servergods.commands.fun.MemeCommand;
import com.pattexpattex.servergods.commands.fun.ShuffleCommand;
import com.pattexpattex.servergods.commands.minecraft.McBanCommand;
import com.pattexpattex.servergods.commands.minecraft.McMemberCommand;
import com.pattexpattex.servergods.commands.minecraft.McWarnCommand;
import com.pattexpattex.servergods.commands.misc.ArchiveCommand;
import com.pattexpattex.servergods.commands.misc.HelpCommand;
import com.pattexpattex.servergods.commands.misc.PingCommand;
import com.pattexpattex.servergods.commands.misc.StopCommand;
import com.pattexpattex.servergods.commands.moderation.*;
import com.pattexpattex.servergods.commands.status.MaintenanceCommand;
import com.pattexpattex.servergods.commands.status.OfflineCommand;
import com.pattexpattex.servergods.commands.status.OnlineCommand;
import com.pattexpattex.servergods.commands.status.RestartCommand;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Emoji;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.interaction.ButtonClickEvent;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.components.Button;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class EventListener extends ListenerAdapter {

	private static final Pattern SPACE_PATTERN = Pattern.compile("\\s");
	
	private final String prefix;
	
	private static final Map<String, BotCommand> commands = new HashMap<>();
	private static final Map<String, BotCommand> enabledCommands = new HashMap<>();
	
	public EventListener(String prefix) {
		this.prefix = prefix;

		//Map of all of the bot's commands
		commands.put("ping", new PingCommand());
		commands.put("help", new HelpCommand(enabledCommands));
		commands.put("offline", new OfflineCommand());
		commands.put("online", new OnlineCommand());
		commands.put("maintenance", new MaintenanceCommand());
		commands.put("restarting", new RestartCommand());
		commands.put("player", new McMemberCommand());
		commands.put("stop", new StopCommand());
		commands.put("mcwarn", new McWarnCommand());
		commands.put("meme", new MemeCommand());
		commands.put("archive", new ArchiveCommand());
		commands.put("invite", new InviteCommand());
		commands.put("shuffle", new ShuffleCommand());
		commands.put("mcban", new McBanCommand());
		commands.put("ban", new BanCommand());
		commands.put("kick", new KickCommand());
		commands.put("grant", new GrantRolesCommand());
		commands.put("mute", new MuteCommand());

		//pass only the enabled commands to the HelpCommand constructor
		for (int i = 0; i < commands.size(); i++) {
			commands.forEach((name, cmd) -> {
				if ((boolean) cmd.isEnabled()) enabledCommands.put(name, cmd);
			});
		}
	}

	//The code that executes the run() method
	//in classes implementing the BotCommand interface
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		Message msg = event.getMessage();
		String raw = msg.getContentRaw();

		//Some checks before continuing
		if (msg.getAuthor().isBot()) {
			return;
		}
		if (!raw.startsWith(prefix)) {
			return;
		}

		//Some formatting magic
		String messageWithoutPrefix = raw.substring(prefix.length());
		String[] split = SPACE_PATTERN.split(messageWithoutPrefix);
		String commandName = split[0];
		BotCommand cmd = enabledCommands.get(commandName);

		//The command was not found
		if (cmd == null) {
			Message reply = msg.reply(MessageUtils.errorEmbed(commandName, new IllegalArgumentException(
					"Command " + commandName + " not found")).build()).complete();
			MessageUtils.deleteMessage(reply, 30);
		}

		//Proceeds to executing the run() method
		else {
			//Creates the String[] args used by the commands
			String[] args = Stream.of(split).skip(1).toArray(String[]::new);

			//Actually executes the run() method
			cmd.run(event, args);
		}
	}

	//A nice welcome message
	@Override
	public void onGuildJoin(GuildJoinEvent event) {

		//Finds the first TextChannel in which the bot can talk in
		for (int i = 0; i < event.getGuild().getTextChannels().size(); i++) {
			if (event.getGuild().getTextChannels().get(i).canTalk()) {

				//The welcome message
				event.getGuild().getTextChannels().get(i).sendMessage(MessageUtils.defaultEmbed("Hello, I'm " +
								event.getJDA().getSelfUser().getName() + "!",
						"""
								I'm a generic Discord bot developed by **PattexPattex**! Also check out my repo at\040
								*https://github.com/PattexPattex/ServerGods*! Use **""" +
								Main.CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + "help** to get started!",
						"DEFAULT", null).build()).queue();

				//Breaks the for loop
				break;
			}
		}
	}
}
