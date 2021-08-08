package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class StopCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
            event.getMessage().reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {
            event.getMessage().reply(MessageUtils.defaultEmbed(null
            , "Stopping, bye! :wave:", false).build()).queue();

            event.getJDA().shutdown();
        }
    }

    @Override
    public String getHelp() {
        return "Safely shuts down the bot";
    }
    
}
