package com.pattexpattex.servergods.util;

import java.time.OffsetDateTime;
import java.util.concurrent.TimeUnit;

import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class MessageUtils {
    
    public static EmbedBuilder defaultEmbed(String title, String message, Boolean thumbnail) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Main.color)
        .setFooter("Powered by Server Gods.", Main.pfp)
        .setTimestamp(OffsetDateTime.now());

        eb.setDescription(message);
        
        if (title != null) {
            eb.setTitle(title);
        }
        if (thumbnail == true) {
            eb.setThumbnail(Main.pfp);
        }

        return eb;
    }

    public static EmbedBuilder welcomeEmbed(Member member) {
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(member.getEffectiveName() + ", dobrodošel na Alalal SMP 2!");
        eb.setDescription("Najprej par pravil:\n-Ne trollaj in griefaj, grief = ban (oof)\n-Brez neprimernih stvari ker pač ne\n-Ostani varen!");
        eb.setColor(Main.color);
        eb.setFooter("Powered by Server Gods.", Main.pfp);
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setTimestamp(OffsetDateTime.now());

        return eb;
    }

    public static void userPing(Member member, TextChannel channel) {
        Message m = channel.sendMessage(member.getAsMention()).complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public static void rolePing(Role role, TextChannel channel) {
        Message m = channel.sendMessage(role.getAsMention()).complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public static void serverPing(TextChannel channel) {
        Message m = channel.sendMessage("<@&819571455846514718>").complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

}
