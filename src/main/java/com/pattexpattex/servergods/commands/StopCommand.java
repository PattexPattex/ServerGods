package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class StopCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();
        EmbedBuilder eb = new EmbedBuilder().setColor(Main.COLOR).setDescription("Stopping! :wave:");

        if (author != owner) { //Checks if the command executor is the owner
            event.getMessage().reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {
            event.getMessage().reply(eb.build()).queue();

            event.getJDA().shutdownNow();
            System.exit(5); //Successful exit
        }
    }

    @Override
    public String getHelp() {
        return "Safely shuts down the bot";
    }
    
}
