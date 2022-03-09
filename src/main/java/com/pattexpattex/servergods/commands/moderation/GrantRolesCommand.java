package com.pattexpattex.servergods.commands.moderation;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

//In early development, probably to be modified or removed entirely
//Good luck reading the code
public class GrantRolesCommand implements BotCommand {

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {

        Message message = event.getMessage();
        Member author = event.getMember();
        Member guildOwner = event.getGuild().getOwner();
        Message reply;

        try {
            if (message.getMentionedMembers().size() == 0) {

                if (args.length >= 1) {

                    for (int i = 0; i < args.length; i++) {
                        event.getGuild().addRoleToMember(author, event.getGuild().getRolesByName(Arrays.stream(args).toList().get(i),
                                true).get(0)).queue();
                    }

                    reply = message.reply(MessageUtils.defaultEmbed("Here you go", "Successfully granted you the selected roles!",
                            null, null).build()).complete();

                } else throw new IllegalArgumentException("Invalid command arguments");

            }
            else {

                if (args.length >= 2) {
                    if (author == guildOwner) {

                        Member mentionedMember = message.getMentionedMembers().get(0);

                        for (int i = 1; i < args.length; i++) {
                            event.getGuild().addRoleToMember(mentionedMember, event.getGuild().getRolesByName(Arrays.stream(args).toList().get(i),
                                    true).get(0)).queue();
                        }

                        reply = message.reply(MessageUtils.defaultEmbed("Here you go", "Successfully granted you the selected roles!",
                                null, null).build()).complete();
                    }
                    else reply = message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).complete();

                } else throw new IllegalArgumentException("Invalid command arguments");
            }

        }
        catch (Exception e) {reply = message.reply(MessageUtils.errorEmbed((String) CONFIG.getConfigValue(Config.BasicConfig.PREFIX) + "grant", e)
                .build()).complete();}

        MessageUtils.deleteMessage(message, 30);
        MessageUtils.deleteMessage(reply, 30);
    }

    @Override
    public String getHelp() {
        return "Assigns the given roles";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.GRANT);
    }
}
