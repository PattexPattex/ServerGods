package com.pattexpattex.servergods.commands;

import java.util.List;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PlayerCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();
        Role memberRole = event.getGuild().getRolesByName("Member", true).get(0);
        Role playerRole = event.getGuild().getRolesByName("Player", true).get(0);
        Role serverBannedRole = event.getGuild().getRolesByName("Server Banned", true).get(0);
        Role djRole = event.getGuild().getRolesByName("DJ", true).get(0);
        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
        } else {
            Member mentionedMember = message.getMentionedMembers().get(0); //Gets the mentioned members
//Assigns the roles to them
            event.getGuild().removeRoleFromMember(mentionedMember, serverBannedRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, playerRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, djRole).queue();
            
            message.reply(MessageUtils.defaultEmbed(null
            , ":arrow_double_up: Promoted the mentioned " + memberRole.getAsMention() + " to " + playerRole.getAsMention() + "!", false).build()).queue();
        }

    }

    @Override
    public String getHelp() {
        return "Promotes the mentioned members to players";
    }
    
}
