package com.pattexpattex.servergods.commands.status;

import java.time.OffsetDateTime;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OnlineCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        VoiceChannel channel = event.getGuild().getVoiceChannelById("840836187815280640");
    
        eb.appendDescription("Changed the status name to **:green_circle: Online**")
        .setColor(Main.color)
        .setTimestamp(OffsetDateTime.now())
        .setFooter("Powered by Server Gods.", Main.pfp);
   
        channel.getManager().setName("🟢 Online").queue();
        event.getMessage().reply(eb.build()).queue();
    }
    
    @Override
    public String getHelp() {
        return "Sets the status channel to online";
    }
}