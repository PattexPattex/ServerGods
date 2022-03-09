package com.pattexpattex.servergods.commands.minecraft;

import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//This class is similar to the McBanCommand class,
//it basically just reverses what McBanCommand does
public class McMemberCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        //Getting all the roles
        Message message = event.getMessage();
        Message reply;

        Role memberRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.GenericRoles.MEMBER));
        Role djRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.GenericRoles.DJ));
        Role smpRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_ROLE));
        Role smpbannedRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_BANNED_ROLE));

        Member guildOwner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != guildOwner) { //Checks if the command executor is the guildOwner
            reply = message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).complete();
        } else {
            Member mentionedMember = message.getMentionedMembers().get(0); //Gets the mentioned members
            //Assigns the roles to them
            event.getGuild().removeRoleFromMember(mentionedMember, smpbannedRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, smpRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, djRole).queue();

            reply = message.reply(MessageUtils.defaultEmbed(null, ":arrow_double_up: Promoted the mentioned "
                    + memberRole.getAsMention() + " to " + smpRole.getAsMention() + "!", mentionedMember.getUser().getAvatarUrl(), null)
                    .build()).complete();
        }

        MessageUtils.deleteMessage(message, 30);
        MessageUtils.deleteMessage(reply, 30);

    }

    @Override
    public String getHelp() {
        return "Promotes the mentioned members to players";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.SmpConfig.SMP_ENABLE);
    }

}