package com.pattexpattex.servergods.commands.misc;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//Safely and normally shuts down the bot,
//...or you can use CTRL+C in the cmd, I'm not a cop, do it however you want, I'm just a comment
public class StopCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        Member botOwner = event.getGuild().getMemberById((String) CONFIG.getConfigValue(Config.BasicConfig.BOT_OWNER));
        Member author = event.getMember();

        //Goodbye message
        EmbedBuilder eb = new EmbedBuilder().setColor(Main.COLOR).setDescription("Stopping! :wave:");

        if (author != botOwner) { //Checks if the command executor is the owner
            event.getMessage().reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {
            event.getMessage().reply(eb.build()).queue((success) -> {
                event.getJDA().shutdownNow();
                System.exit(0); //Successful exit
            });
        }
    }

    @Override
    public String getHelp() {
        return "Safely shuts down the bot";
    }

    @Override
    public Object isEnabled() {
        return true;
    }

}
