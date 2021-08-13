package com.pattexpattex.servergods.commands.status;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OnlineCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();
        TextChannel textChannel = event.getGuild().getTextChannelsByName("📣-announcements", true).get(0);
        Role player = event.getGuild().getRolesByName("Player", true).get(0);
        VoiceChannel voiceChannel = event.getGuild().getVoiceChannels().get(0);
        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
            message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {
            MessageUtils.rolePing(player, textChannel);
            voiceChannel.getManager().setName("🟢 Online").queue();

            message.reply(MessageUtils.defaultEmbed(null
            , ":pencil2: Changed the status name to **:green_circle: Online**", null, null).build()).queue();

            textChannel.sendMessage(MessageUtils.defaultEmbed("Server Status"
            , "The server is back **:green_circle: Online**! :partying_face:", null, null).build()).queue();
        }
    }
    
    @Override
    public String getHelp() {
        return "Sets the status channel to online";
    }
}