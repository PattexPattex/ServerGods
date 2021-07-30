package com.pattexpattex.servergods.events;

import java.util.concurrent.TimeUnit;

import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MemberJoinEvent extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Member member = event.getMember();
        TextChannel channel = event.getGuild().getTextChannelById("819581122974974002");

        Message m = channel.sendMessage(member.getAsMention()).complete(); //ping the user
        m.delete().queueAfter(5, TimeUnit.SECONDS); //Delete the ping

        channel.sendMessage(MessageUtils.welcomeEmbed(member).build()).queue(); //Send the embed
    }
}
