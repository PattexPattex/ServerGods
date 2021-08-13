package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InviteCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();

        Message reply = message.reply(MessageUtils.defaultEmbed("The invite", Main.INVITE, null, null).build()).complete();

        MessageUtils.deleteMessage(message, 30L);
        MessageUtils.deleteMessage(reply, 30L);
    }

    @Override
    public String getHelp() {
        return "Posts an invite";
    }
}
