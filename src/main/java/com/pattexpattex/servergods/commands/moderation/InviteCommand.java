package com.pattexpattex.servergods.commands.moderation;

import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.InviteAction;

import java.util.concurrent.TimeUnit;

//This class creates a quick invite to the guild,
//the invite has 1 use, and it is valid for 5 minutes by default,
//however, you can specify to reply with a permanent invite (enabled only for the default_guild)
public class InviteCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();

        InviteAction invite = event.getChannel().createInvite();
        invite.setMaxUses(1).queue();
        invite.setMaxAge(5L, TimeUnit.MINUTES).queue();

        Message reply = message.reply(MessageUtils.defaultEmbed(invite.complete().getUrl(),
                "*It has one use and works for 5 minutes*", null, null).build()).complete();

        MessageUtils.deleteMessage(message, 300);
        MessageUtils.deleteMessage(reply, 300);
    }

    @Override
    public String getHelp() {
        return "Posts an invite";
    }

    @Override
    public Object isEnabled() {
        return true;
    }
}
