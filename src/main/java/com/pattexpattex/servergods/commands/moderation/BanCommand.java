package com.pattexpattex.servergods.commands.moderation;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//Bans the mentioned user, simple stuff
public class BanCommand implements BotCommand {
    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        if (args.length != 0) {
            Member bannedMember = event.getMessage().getMentionedMembers().get(0);
            Member guildOwner = event.getGuild().getOwner();
            Member author = event.getMember();

            Message message = event.getMessage();

            String reason;
            Message reply;

            if (author != guildOwner) { //Standard permission checks
                message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
            }
            else {
                reason = message.getContentDisplay().replace(CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + "ban", "").replace("@" +
                        bannedMember.getEffectiveName(), "");

                message.reply(MessageUtils.defaultEmbed("Banned!", "Successfully banned **"
                        + bannedMember.getAsMention() + "**! What now?", null, null).build()).queue();

                //Actually bans the member
                bannedMember.ban(0, reason).queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "Bans the mentioned user from the discord server with the optional reason";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.BAN);
    }
}
