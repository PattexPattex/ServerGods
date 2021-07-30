package com.pattexpattex.servergods.commands.status;

import java.time.OffsetDateTime;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RestartCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        TextChannel channel = event.getGuild().getTextChannelById("819579501327417365");
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Main.color)
        .setTitle("Server restarting...")
        .appendDescription("The server is restarting, it should be back soon.")
        .setFooter("Powered by Server Gods.", Main.pfp)
        .setTimestamp(OffsetDateTime.now());

        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Notifies the discord that the server is restarting";
    }
}
