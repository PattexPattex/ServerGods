package com.pattexpattex.servergods.commands.moderation;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//This command simply gives the mentioned member a "Muted" role,
//the role's permissions still must be set manually in the server settings
public class MuteCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        Message message = event.getMessage();
        Message reply;

        Member author = event.getMember();
        Member guildOwner = event.getGuild().getOwner();

        try {
            //Checks for any arguments, if there are none, throws an IllegalArgumentException
            if (args.length != 0) {
                Member mutedMember = message.getMentionedMembers().get(0);
                Role role;

                //Checks if the event is triggered in the default_guild
                if (event.getGuild().getId() == CONFIG.getConfigValue(Config.BasicConfig.DEFAULT_GUILD)) {
                    role = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.GenericRoles.MUTED));
                }
                else {
                    //The muted role in other guilds must be named "Muted"
                    role = event.getGuild().getRolesByName("Muted", true).get(0);
                }

                //Checks if the guildOwner is the event's author
                if (author != guildOwner) {
                    reply = message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).complete();
                }
                else {
                    //Actually mutes the mutedMember
                    event.getGuild().addRoleToMember(mutedMember, role).queue();

                    //Ooh, pretty text
                    reply = message.reply(MessageUtils.defaultEmbed("Successfully shut-ed the dude!",
                            "Bro he can't even talk rn", null, null).build()).complete();
                }
            }
            else throw new IllegalArgumentException("Invalid command arguments"); //Yay exceptions

        } catch (Exception e) {
            reply = message.reply(MessageUtils.errorEmbed("mute", e).build()).complete();
        }

        MessageUtils.deleteMessage(reply, 30);
        MessageUtils.deleteMessage(message, 30);
    }

    @Override
    public String getHelp() {
        return "Mutes the specified member";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.MUTE);
    }
}
