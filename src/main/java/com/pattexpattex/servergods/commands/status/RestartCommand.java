package com.pattexpattex.servergods.commands.status;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RestartCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();
        Role player = event.getGuild().getRolesByName("Player", true).get(0);
        TextChannel textChannel = event.getGuild().getTextChannelsByName("📣-announcements", true).get(0);
        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
            message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {
            MessageUtils.rolePing(player, textChannel);

            textChannel.sendMessage(MessageUtils.defaultEmbed("Server Status"
            , "The server is **:orange_circle: Restarting**, it should be back soon:tm:.", null, null).build()).queue();

            message.reply(MessageUtils.defaultEmbed(null
            , ":bell: Notified that the server is **:orange_circle: Restarting**", null, null).build()).queue();
        }
    }

    @Override
    public String getHelp() {
        return "Notifies the discord that the server is restarting";
    }
}
