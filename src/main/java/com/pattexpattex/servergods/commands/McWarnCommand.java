package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class McWarnCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();
        Role warnOneRole = event.getGuild().getRolesByName("1. KIKS", true).get(0);
        Role warnTwoRole = event.getGuild().getRolesByName("2. KIKS", true).get(0);
        Role bannedRole = event.getGuild().getRolesByName("Server Banned", true).get(0);
        Role playerRole = event.getGuild().getRolesByName("Player", true).get(0);
        Role djRole = event.getGuild().getRolesByName("DJ", true).get(0);

        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
            message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {

            Member mentionedMember = message.getMentionedMembers().get(0);

            if (mentionedMember.getRoles().contains(warnTwoRole)) { //3. Kiks
                event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue(); //Remove the previous warn roles
                event.getGuild().removeRoleFromMember(mentionedMember, warnTwoRole).queue();
                event.getGuild().removeRoleFromMember(mentionedMember, playerRole).queue(); //And the Player role
                event.getGuild().removeRoleFromMember(mentionedMember, djRole).queue(); //And the DJ role (bruh)
                event.getGuild().addRoleToMember(mentionedMember, bannedRole).queue(); //Add the Server Banned role

                Message reply = message.reply(MessageUtils.defaultEmbed("Bro u literally killed him"
                , "<:catLoading:853319548874129488> Succesfully banned " + mentionedMember.getAsMention() + " from the MC server! Better u get a ban revoke at " + owner.getAsMention() + " if u can bro", mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30L);
                MessageUtils.deleteMessage(message, 30L);

            } else if (mentionedMember.getRoles().contains(warnOneRole)) { //2. Kiks
                event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue(); //Remove the previous warn roles
                event.getGuild().addRoleToMember(mentionedMember, warnTwoRole).queue(); //Add the 2. KIKS role

                Message reply =message.reply(MessageUtils.defaultEmbed("What have you done this time?"
                , "<:lmao:853559874390720532> Succesfully warned " + mentionedMember.getAsMention() + "!\nWoah thats the 2nd warn, bro be careful with what ur doing!", mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30L);
                MessageUtils.deleteMessage(message, 30L);

            } else { //1. Kiks
                event.getGuild().addRoleToMember(mentionedMember, warnOneRole).queue(); //Add the 1. KIKS role

                Message reply = message.reply(MessageUtils.defaultEmbed("Got'em"
                , "<:lol:853271360884047873> Succesfully warned " + mentionedMember.getAsMention() + "!", mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30L);
                MessageUtils.deleteMessage(message, 30L);
            }
        } 
    }

    @Override
    public String getHelp() {
        return "Gives the player 2 kiks roles, then the Server Banned role";
    }
    
}
