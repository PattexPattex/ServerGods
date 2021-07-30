package com.pattexpattex.servergods.events;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
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

        if (event.getGuild().getDefaultChannel() != null) {
            EmbedBuilder eb = new EmbedBuilder();

            eb.setTitle(member.getEffectiveName() + ", dobrodošel na Alalal SMP 2!");
            eb.setDescription("Najprej par pravil:\n\t-Ne trollaj in griefaj, grief == ban (oof)\n\t-Brez neprimernih stvari ker pač ne\n\t-Ostani varen!");
            eb.setColor(Main.color);
            eb.setFooter("Powered by Server Gods.");
            eb.setThumbnail(member.getUser().getAvatarUrl());
            eb.setTimestamp(OffsetDateTime.now());


            Message m = channel.sendMessage(member.getAsMention()).complete(); //ping the user
            m.delete().queueAfter(5, TimeUnit.SECONDS); //Delete the ping
            channel.sendMessage(eb.build()).queue(); //Send the embed
        }
    }
}
