package com.pattexpattex.servergods.commands.moderation;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//Kicks the mentioned user from the guild
public class KickCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Member guildOwner = event.getGuild().getOwner();
        Member author = event.getMember();

        Message message = event.getMessage();
        Message reply;
        String reason;

        try {
            if (args.length != 0) {

                Member kickedMember = event.getMessage().getMentionedMembers().get(0);

                if (author != guildOwner) {
                    reply = message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).complete();
                } else {
                    reason = message.getContentDisplay().replace(CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + "kick", "").replace("@" +
                            kickedMember.getEffectiveName(), "");

                    reply = message.reply(MessageUtils.defaultEmbed("Kicked!", "Successfully kicked **"
                            + kickedMember.getAsMention() + "**! What now?", null, null).build()).complete();

                    kickedMember.kick(reason).queue();
                }
            }
            else throw new IllegalArgumentException("Invalid command arguments");

        } catch (Exception e) {
            reply = message.reply(MessageUtils.errorEmbed(CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + "kick", e)
                .build()).complete();
        }

        MessageUtils.deleteMessage(message, 30);
        MessageUtils.deleteMessage(reply, 30);
    }

    @Override
    public String getHelp() {
        return "Kicks the mentioned member with an optional reason";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.KICK);
    }
}
