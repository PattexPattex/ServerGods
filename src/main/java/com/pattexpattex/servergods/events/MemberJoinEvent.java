package com.pattexpattex.servergods.events;

import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinEvent extends ListenerAdapter {

    private Config CONFIG = Main.CONFIG;

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member newMember = event.getMember();
        Role role = event.getGuild().getRoleById((long) CONFIG.getConfigValue(Config.GenericRoles.MEMBER));
        TextChannel channel = event.getGuild().getTextChannelById((long) CONFIG.getConfigValue(Config.BasicConfig.WELCOME_CHANNEL));

        MessageUtils.userPing(newMember, channel); //Pings the new user
        channel.sendMessage(MessageUtils.welcomeEmbed(newMember).build()).queue(); //Send the embed
        event.getGuild().addRoleToMember(newMember, role).queue(); //Adds the newly joined member the "Member" role
    }
}
