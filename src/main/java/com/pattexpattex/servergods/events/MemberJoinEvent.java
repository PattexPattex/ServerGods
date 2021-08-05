package com.pattexpattex.servergods.events;

import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        TextChannel channel = event.getGuild().getTextChannelById("819581122974974002");

        MessageUtils.userPing(member, channel); //Pings the new user
        channel.sendMessage(MessageUtils.welcomeEmbed(member).build()).queue(); //Send the embed
    }
}
