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

            if (author != owner) {
                message.reply(MessageUtils.ownerOnlyCommandEmbed().build()).queue();
            } else {
                bannedMemberName = bannedMember.getEffectiveName();
                reason = message.getContentDisplay().replace("$ban ", "").replace("@" + bannedMemberName, "");

                message.reply(MessageUtils.defaultEmbed("Banned!", "<:catLoading:853319548874129488> Successfully banned **" + bannedMember.getAsMention() + "**! What now?", null, null).build()).queue();

                /*bannedMember.getUser().openPrivateChannel().complete().sendMessage(MessageUtils.defaultEmbed("Pomembno obvestilo", "Člani Alalal SMP-ja in tretje osebe so se odločile, da tebe, **" +
                        bannedMemberName + "** banamo zaradi:\n**- Greed/Cheezanje sistema**\n**- Neupoštevanje pravil**\n**- Griefanje** [Alalal SMP 1]\n**- Predstavlja neposredno grožnjo zdravju serverja**\n" +
                        "**-** (Občasne) **neverjetne količine laga**\n\nTa ban je končen in veljaven za nedoločen čas, revoke ni možen. *Možne alternative so t. i. Matija SMP, SSP ali nekaj tretjega.* Prošnje za vrnitev bodo bile ignorirane.\n\n- **Server Gods**", null, null).build()).queue();

                //owner.getUser().openPrivateChannel().complete().sendMessage(MessageUtils.defaultEmbed("Pomembno obvestilo", "Člani Alalal SMP-ja in tretje osebe so se odločile, da tebe, **" +
                        bannedMemberName + "** banamo zaradi:\n**- Greed/Cheezanje sistema**\n**- Neupoštevanje pravil**\n**- Griefanje** [Alalal SMP 1]\n**- Predstavlja neposredno grožnjo zdravju serverja**\n" +
                        "**-** (Občasne) **neverjetne količine laga**\n\nTa ban je končen in veljaven za nedoločen čas, revoke ni možen. *Možne alternative so t. i. Matija SMP, SSP ali nekaj tretjega.* Prošnje za vrnitev bodo bile ignorirane.\n\n- **Server Gods**", null, null).build()).queue();
                 */

                bannedMember.ban(0, reason).queue();
            }
        }
    }

    @Override
    public String getHelp() {
        return "Bans the mentioned user from the discord server with the optional reason";
    }
}
