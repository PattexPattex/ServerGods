package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.Main;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MemeCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        String[] memes = {"https://cdn.discordapp.com/attachments/753308348174172331/834501370878361640/2021-03-01_18.52.10.png",
                          "https://cdn.discordapp.com/attachments/834506446954823741/834506703349088297/2021-04-21_18.19.23.png",
                          "https://cdn.discordapp.com/attachments/819579501327417365/845738658363211806/SmartSelect_20210522-210310_Discord.jpg",
                          "https://cdn.discordapp.com/attachments/739809805564379217/809417117669261362/unknown.png",
                          "https://cdn.discordapp.com/attachments/739809805564379217/820692347678359562/unknown.png",
                          "https://cdn.discordapp.com/attachments/739809805564379217/821056634750631987/SPOILER_wat.png",
                          "https://cdn.discordapp.com/attachments/739809805564379217/827503932518825984/unknown.png"};
        String[] titles = {"Here ya go", "Enjoy", "Fresh memez", "Haha dis is a good one"};

        int memeNumber = (int)(Math.random() * memes.length);
        int titleNumber = (int)(Math.random() * titles.length);
        Message message = event.getMessage();

        Message reply = message.reply(MessageUtils.defaultEmbed(titles[titleNumber], null, null, memes[memeNumber]).build()).complete();

        MessageUtils.deleteMessage(reply, 30L);
        MessageUtils.deleteMessage(message, 30L);
    }

    @Override
    public String getHelp() {
        return "Posts a random meme about Alalal SMP";
    }
}
