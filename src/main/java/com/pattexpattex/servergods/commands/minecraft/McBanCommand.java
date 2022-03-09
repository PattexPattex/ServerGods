package com.pattexpattex.servergods.commands.minecraft;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

//This class is for managing roles in case if you have a Minecraft SMP or something
//It removes a bunch of roles and gives a "Banned from server" type of role
public class McBanCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        //Get all the roles and other things
        Message message = event.getMessage();
        Role warnOneRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_1_WARN_ROLE));
        Role warnTwoRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_2_WARN_ROLE));
        Role bannedRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_BANNED_ROLE));
        Role playerRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.SmpConfig.SMP_ROLE));
        Role djRole = event.getGuild().getRoleById((String) CONFIG.getConfigValue(Config.GenericRoles.DJ));

        Member guildOwner = event.getGuild().getOwner();
        Member author = event.getMember();

        if (author != guildOwner) { //Checks if the command executor is the guildOwner
            message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
        } else {

            Member mentionedMember = message.getMentionedMembers().get(0);

            //The role-ing action
            event.getGuild().removeRoleFromMember(mentionedMember, warnOneRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, warnTwoRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, playerRole).queue();
            event.getGuild().removeRoleFromMember(mentionedMember, djRole).queue();
            event.getGuild().addRoleToMember(mentionedMember, bannedRole).queue();

            Message reply = message.reply(MessageUtils.defaultEmbed("Bro u literally killed him"
                    , "<:catLoading:853319548874129488> Succesfully banned " + mentionedMember.getAsMention()
                            + " from the MC server! Better u get a ban revoke at " + guildOwner.getAsMention() + " if u can bro",
                    mentionedMember.getUser().getAvatarUrl(), null).build()).complete();

            //Did you notice there is no MessageUtils.deleteMessage()?
            //I like leaving scary ban actions in chats just to scare people lol
        }
    }

    @Override
    public String getHelp() {
        return "Adds the mentioned member the Server Banned role";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.SmpConfig.SMP_ENABLE);
    }
}
