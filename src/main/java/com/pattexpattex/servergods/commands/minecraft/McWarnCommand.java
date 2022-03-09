package com.pattexpattex.servergods.commands.minecraft;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//This class, again, just like McMemberCommand and McBanCommand move a bunch of roles up and down
public class McWarnCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        //If you've read the comments from the other two classes in this package,
        //I hope you would get how this works by now
        Message message = event.getMessage();
        Role warnOneRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_1_WARN_ROLE));
        Role warnTwoRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_2_WARN_ROLE));
        Role bannedRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_BANNED_ROLE));
        Role playerRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_ROLE));
        Role djRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.GenericRoles.DJ));

        Member owner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != owner) { //Checks if the command executor is the owner
            message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {

            Member mentionedMember = message.getMentionedMembers().get(0);

            if (mentionedMember.getRoles().contains(warnTwoRole)) { //3rd warn
                event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue(); //Remove the previous warn roles
                event.getGuild().removeRoleFromMember(mentionedMember, warnTwoRole).queue();
                event.getGuild().removeRoleFromMember(mentionedMember, playerRole).queue(); //And the Player role
                event.getGuild().removeRoleFromMember(mentionedMember, djRole).queue(); //And the DJ role (bruh)
                event.getGuild().addRoleToMember(mentionedMember, bannedRole).queue(); //Add the Server Banned role

                Message reply = message.reply(MessageUtils.defaultEmbed("Bro u literally killed him"
                , "Succesfully banned " + mentionedMember.getAsMention() + " from the MC server! Better u get a ban revoke at "
                                + owner.getAsMention() + " if u can bro", mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30);
                MessageUtils.deleteMessage(message, 30);

            } else if (mentionedMember.getRoles().contains(warnOneRole)) { //2nd warn
                event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue(); //Remove the previous warn roles
                event.getGuild().addRoleToMember(mentionedMember, warnTwoRole).queue(); //Add the 1. warn role

                Message reply =message.reply(MessageUtils.defaultEmbed("What have you done this time?"
                , "Succesfully warned " + mentionedMember.getAsMention() + "!\nWoah thats the 2nd warn, bro be careful with what ur doing!",
                        mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30);
                MessageUtils.deleteMessage(message, 30);

            } else { //1st warn
                event.getGuild().addRoleToMember(mentionedMember, warnOneRole).queue(); //Add the 1. warn role

                Message reply = message.reply(MessageUtils.defaultEmbed("Got'em"
                , "Succesfully warned " + mentionedMember.getAsMention() + "!",
                        mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

                MessageUtils.deleteMessage(reply, 30);
                MessageUtils.deleteMessage(message, 30);
            }

            //As you can see, this code is too, slightly cursed
        } 
    }

    @Override
    public String getHelp() {
        return "Gives the player 2 warnings, then the Server Banned role";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.SmpConfig.SMP_ENABLE);
    }

}
