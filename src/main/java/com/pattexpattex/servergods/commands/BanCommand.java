package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BanCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        if (args.length != 0) {
            Member bannedMember = event.getMessage().getMentionedMembers().get(0);
            Member owner = event.getGuild().getOwner();
            Member author = event.getMember();
            Message message = event.getMessage();
            String reason;
            String bannedMemberName;

            if (author != owner) { //Checks if the command executor is the owner
                message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
            } else {
                bannedMemberName = bannedMember.getEffectiveName();
                reason = message.getContentDisplay().replace("$ban ", "").replace("@" + bannedMemberName, "");

                bannedMember.ban(0, reason).queue((success) -> {
                    EmbedBuilder eb = MessageUtils.defaultEmbed("um ok your da boss", "<:catLoading:853319548874129488> Successfully banned **" + bannedMemberName + "**! What now?", null, null);
                    message.reply(eb.build()).queue();
                });

            }
        }
    }

    @Override
    public String getHelp() {
        return "Bans the mentioned user from the discord server with the optional reason";
    }
}
