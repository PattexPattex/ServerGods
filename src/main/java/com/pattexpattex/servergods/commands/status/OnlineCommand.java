package com.pattexpattex.servergods.commands.status;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OnlineCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        TextChannel textChannel = event.getGuild().getTextChannelById("819579501327417365");
        Role player = event.getGuild().getRoleById("819604897363918949");
        VoiceChannel voiceChannel = event.getGuild().getVoiceChannelById("840836187815280640");

        MessageUtils.rolePing(player, textChannel);
        voiceChannel.getManager().setName("🟢 Online").queue();
        event.getMessage().reply(MessageUtils.defaultEmbed(null, "Changed the status name to **:green_circle: Online**", false).build()).queue();
        textChannel.sendMessage(MessageUtils.defaultEmbed("Server Status", "The server is back **:green_circle: Online!", false).build()).queue();
    }
    
    @Override
    public String getHelp() {
        return "Sets the status channel to online";
    }
}