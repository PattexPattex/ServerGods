package com.pattexpattex.servergods.commands;

import com.pattexpattex.servergods.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class ShuffleCommand implements BotCommand {
    String[] standardPermuts = {"U", "D", "L", "R", "F", "B",
            "U'", "D'", "L'", "R'", "F'", "B'",
            "U2", "D2", "L2", "R2", "F2", "B2"};
    String[] fourByFourPermuts = {"U", "D", "L", "R", "F", "B",
            "U'", "D'", "L'", "R'", "F'", "B'",
            "U2", "D2", "L2", "R2", "F2", "B2",
            "u", "d", "l", "r", "f", "b",
            "u'", "d'", "l'", "r'", "f'", "b'",
            "u2", "d2", "l2", "r2", "f2", "b2",
            "Uw", "Dw", "Lw", "Rw", "Fw", "Bw",
            "Uw'", "Dw'", "Lw'", "Rw'", "Fw'", "Bw'",
            "Uw2", "Dw2", "Lw2", "Rw2", "Fw2", "Bw2"};

    List<String> algorithm = new ArrayList<>();

    @Override
    public void run(GuildMessageReceivedEvent event, String[] args) {
        int algorithmLength = 10;
        algorithm.clear();

        Message message = event.getMessage();

        if (args.length == 0) {
            for (int i = 0; i < algorithmLength; i++) {
                algorithm.add(standardPermuts[(int) (Math.random() * standardPermuts.length)]);
            }

            String prettyAlgorithm = algorithm.toString()
                    .replace("[", "")
                    .replace(",", "")
                    .replace("]", "");

            Message reply = message.reply(MessageUtils.defaultEmbed("Shuffled!", "<:allowed:853559823618015252> " + prettyAlgorithm, null, null).build()).complete();
            MessageUtils.deleteMessage(reply, 120L);
        }
        else {
            try {
                algorithmLength = Integer.parseInt(args[0]);

                for (int i = 0; i < algorithmLength; i++) {
                    algorithm.add(standardPermuts[(int) (Math.random() * standardPermuts.length)]);
                }

                String prettyAlgorithm = algorithm.toString()
                        .replace("[", "")
                        .replace(",", "")
                        .replace("]", "");

                Message reply = message.reply(MessageUtils.defaultEmbed("Shuffled!", "<:allowed:853559823618015252> " + prettyAlgorithm, null, null).build()).complete();
                MessageUtils.deleteMessage(reply, 120L);

            } catch (NumberFormatException e) {
                Message reply = message.reply(MessageUtils.defaultEmbed("Oops!", ":warning: The argument must be a number!", null, null).build()).complete();
                MessageUtils.deleteMessage(reply, 120L);
            }
        }

        MessageUtils.deleteMessage(message, 120L);
    }

    @Override
    public String getHelp() {
        return "Gives you a random algorithm to shuffle a Rubik's cube";
    }
}
