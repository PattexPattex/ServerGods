package com.pattexpattex.servergods.commands.status;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
//import net.dv8tion.jda.api.entities.VoiceChannel;

public class RestartCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        //VoiceChannel voiceChannel = event.getGuild.getVoiceChannelById("840836187815280640");
        Role player = event.getGuild().getRoleById("819604897363918949");
        TextChannel textChannel = event.getGuild().getTextChannelById("819579501327417365");

        MessageUtils.rolePing(player, textChannel);
        //voiceChannel.getManager().setName("🟠 Restarting...").queue();
        textChannel.sendMessage(MessageUtils.defaultEmbed("Server Status", "The server is **:orange_circle: Restarting**, it should be back soon:tm:.", false).build()).queue();
        event.getMessage().reply(MessageUtils.defaultEmbed(null, "Notified that the server is **:orange_circle: Restarting**", false).build()).queue();
    }

    @Override
    public String getHelp() {
        return "Notifies the discord that the server is restarting";
    }
}
