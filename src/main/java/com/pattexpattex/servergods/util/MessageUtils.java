package com.pattexpattex.servergods.util;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.pattexpattex.servergods.Main;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.annotation.Nullable;

public class MessageUtils {
    
    public static EmbedBuilder defaultEmbed(@Nullable String title, @Nullable String message, @Nullable String thumbnail, @Nullable String image) { //Creates the default embed, used everywhere
        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Main.COLOR)
        .setFooter("Powered by Server Gods.", Main.PFP)
        .setTimestamp(OffsetDateTime.now());

        if (message != null) { //Checks if it should add a description
            eb.setDescription(message);
        }
        
        if (title != null) { //Checks if the title parameter is null, if not, adds the parameter to the embed
            eb.setTitle(title);
        }

        if (Objects.equals(thumbnail, "DEFAULT")) { //Checks if it should add a thumbnail
            eb.setThumbnail(Main.PFP); //The bot's profile pic
        } else if (thumbnail != null) {
            eb.setThumbnail(thumbnail);
        }

        if (image != null) {
            eb.setImage(image);
        }

        return eb;
    }

    public static EmbedBuilder ownerOnlyCommandEmbed() {
        return defaultEmbed("WHOA DUDE"
        , ":stop_sign: Yo, I see that you are trying to use an **OWNER ONLY** command, chill bro!", null, null);
    }

    public static EmbedBuilder welcomeEmbed(Member member) { //Creates and returns the welcome embed
        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle(member.getEffectiveName() + ", dobrodošel na Alalal SMP 2!");
        eb.setDescription("<:pain:853267819885494282> **Najprej par pravil:**\n-Ne trollaj in griefaj, grief = ban <:wither:853319610799358005>\n-Brez neprimernih stvari ker pač ne <:uwu:853560167760789544>\n-Ostani varen <:mlady:853317048151179265>");
        eb.setColor(Main.COLOR);
        eb.setFooter("Powered by Server Gods.", Main.PFP);
        eb.setThumbnail(member.getUser().getAvatarUrl());
        eb.setTimestamp(OffsetDateTime.now());

        return eb;
    }

    public static void deleteMessage(Message message, Long deleteAfterSeconds) {
        if (message != null) {
            message.delete().queueAfter(deleteAfterSeconds, TimeUnit.SECONDS);
        }
    }

    public static void userPing(Member member, TextChannel channel) { //Pings the specified user in the specified channel
        Message m = channel.sendMessage("<:deletdis:853319565995933728> " + member.getAsMention()).complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public static void rolePing(Role role, TextChannel channel) { //Pings a specified role in the specified channel
        Message m = channel.sendMessage("<:aaa:853269682898599986> " + role.getAsMention()).complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    public static void serverPing(TextChannel channel) { //Pings the entire server in the specified channel
        String guildPing = channel.getGuild().getId();
        Message m = channel.sendMessage("<:woke:853319377200611329> " + "<@&" + guildPing + ">").complete();
        m.delete().queueAfter(5, TimeUnit.SECONDS);
    }

}
