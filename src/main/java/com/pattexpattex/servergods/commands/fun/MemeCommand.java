package com.pattexpattex.servergods.commands.fun;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static com.pattexpattex.servergods.Main.MEMES_CONFIG;

//This class randomly picks a link from memes.json
//and displays it in a reply to the command
public class MemeCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        //Chooses a random meme and a title
        int memeNumber = (int)(Math.random() * MEMES_CONFIG.getMemes().size());
        int titleNumber = (int)(Math.random() * MEMES_CONFIG.getTitles().size());
        Message message = event.getMessage();

        //Actually sends the meme
        Message reply = message.reply(MessageUtils.defaultEmbed(MEMES_CONFIG.getTitle(titleNumber),
                null, null, MEMES_CONFIG.getMeme(memeNumber)).build()).complete();

        //Delete after some time for cleanness
        MessageUtils.deleteMessage(reply, 30);
        MessageUtils.deleteMessage(message, 30);
    }

    @Override
    public String getHelp() {
        return "Posts a random meme about Alalal SMP";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.MEME);
    }
}
