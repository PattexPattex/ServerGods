package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class McBanCommand implements BotCommand {

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

            event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, warnTwoRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, playerRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, djRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, bannedRole).queue();

            Message reply = message.reply(MessageUtils.defaultEmbed("Bro u literally killed him"
                    , "<:catLoading:853319548874129488> Succesfully banned " + mentionedMember.getAsMention() + " from the MC server! Better u get a ban revoke at " + owner.getAsMention() + " if u can bro", mentionedMember.getUser().getAvatarUrl(), null).build()).complete();
        }
    }

    @Override
    public String getHelp() {
        return "Adds the mentioned member the Server Banned role";
    }
}
