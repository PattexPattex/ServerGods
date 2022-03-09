package com.pattexpattex.servergods.util;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.pattexpattex.servergods.Main;

import com.pattexpattex.servergods.config.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

//A class with methods that are commonly used by the bot,
//usually for formatting messages, exceptions, etc.
public class MessageUtils {

    static Config CONFIG = Main.CONFIG;

    //Creates the default embed, used everywhere
    public static EmbedBuilder defaultEmbed(@Nullable String title, @Nullable String message,
                                            @Nullable String thumbnail, @Nullable String image) {

        EmbedBuilder eb = new EmbedBuilder();

        eb.setColor(Main.COLOR)
        .setFooter("Powered by Server Gods.", (String) CONFIG.getConfigValue(Config.BasicConfig.BOT_PFP))
        .setTimestamp(OffsetDateTime.now());

        if (message != null) { //Checks if it should add a description
            eb.setDescription(message);
        }
        
        if (title != null) { //Checks if the title parameter is null, if not, adds the parameter to the embed
            eb.setTitle(title);
        }

        if (Objects.equals(thumbnail, "DEFAULT")) { //Checks if it should add a thumbnail
            eb.setThumbnail((String) CONFIG.getConfigValue(Config.BasicConfig.BOT_PFP)); //The bot's profile picture
        } else if (thumbnail != null) {
            eb.setThumbnail(thumbnail);
        }

        if (image != null) {
            eb.setImage(image);
        }

        return eb;
    }

    public static EmbedBuilder ownerOnlyCommandEmbed() { //Pretty self-explanatory
        return defaultEmbed("Oops!",
                ":stop_sign: Bro, I see that you are trying to use an **OWNER ONLY** command and ur not the owner!",
                null, null);
    }

    public static EmbedBuilder errorEmbed(String command, @Nullable Exception e) { //An embed for exceptions and errors
        EmbedBuilder eb;

        eb = defaultEmbed("Oops!",
                "There was an error running the **" + CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + command + "** command!",
                null, null).setColor(Main.ERROR_COLOR);

        if (e != null) { //Appends an exception, if there is any given
            eb.addField("Debug Info", "`" + e.getClass().getSimpleName() + ": " + e.getMessage() + "`", false);
        }

        return eb;
    }

    public static EmbedBuilder welcomeEmbed(Member member) { //Pretty self-explanatory
        return defaultEmbed(member.getEffectiveName() + ", dobrodošel na Alalal SMP Discordu!",
                """
                        **Plz upoštevaj ta pravila:**
                        - Ne bit offensive ker pač ne
                        - Brez neprimernih stvari ker pač ne
                        - Fajn se mej""",
                member.getUser().getAvatarUrl(),
                null);
    }

    //Deletes a given Message object after some time
    public static void deleteMessage(Message message, int deleteAfterSeconds) {
        if (message != null) {
            message.delete().queueAfter(deleteAfterSeconds, TimeUnit.SECONDS);
        }
    }

    public static void userPing(Member member, TextChannel channel) { //Pings a given user in a given channel
        Message m = channel.sendMessage("<:deletdis:853319565995933728> " + member.getAsMention()).complete();
        deleteMessage(m, 5);
    }

    public static void rolePing(Role role, TextChannel channel) { //Pings a given role in a given channel
        Message m = channel.sendMessage("<:aaa:853269682898599986> " + role.getAsMention()).complete();
        deleteMessage(m, 5);
    }

    public static void serverPing(TextChannel channel) { //Pings an entire server in a given channel
        String guildPing = channel.getGuild().getId();
        Message m = channel.sendMessage("<:woke:853319377200611329> " + "<@&" + guildPing + ">").complete();
        deleteMessage(m, 5);
    }

}
