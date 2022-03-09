package com.pattexpattex.servergods.commands.misc;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

//This class replies with a list of links with world downloads, videos, resource packs, etc.
//Basically more Minecraft SMP stuff
public class ArchiveCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        //The map of all the entries, I will later make use of
        //the same principle as seen in memes.json and config.json
        Map<String, String> archive = new HashMap<>();
        archive.put("S1 WDL", "https://drive.google.com/file/d/1zunqZqCcMR8KQy3GHb7NK_aq6Ea4dwbC/view?usp=sharing");
        archive.put("S1 Cinematic", "https://www.youtube.com/watch?v=zCFvghvV-j8");
        archive.put("First Cinematic", "https://www.youtube.com/watch?v=SY4bs4DbldQ");
        archive.put("AlalalPack 1", "https://cdn.discordapp.com/attachments/819579501327417365/851443276548145172/AlalalPack-1.zip");
        archive.put("Better FPS Tutorial (1.16)", "https://youtu.be/oVSoJLtCOz0");
        archive.put("FastBamboo Lite", "https://cdn.discordapp.com/attachments/819579501327417365/829683165659529216/FastBamboo_Lite.zip");
        archive.put("FastBamboo Ultra", "https://cdn.discordapp.com/attachments/819579501327417365/829683191153557524/FastBamboo_Ultra.zip");

        Message message = event.getMessage();
        EmbedBuilder eb = MessageUtils.defaultEmbed("Alalal SMP Archive", null, "DEFAULT", null);

        for (String i : archive.keySet()) {
            eb.addField(i, archive.get(i), false);
        }

        Message reply = message.reply(eb.build()).complete();

        MessageUtils.deleteMessage(message, 120);
        MessageUtils.deleteMessage(reply, 120);
    }

    @Override
    public String getHelp() {
        return "Posts the Alalal SMP archive";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.ARCHIVE);
    }
}
