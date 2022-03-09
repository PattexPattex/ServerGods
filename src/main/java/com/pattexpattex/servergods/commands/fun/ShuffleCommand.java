package com.pattexpattex.servergods.commands.fun;

import com.pattexpattex.servergods.config.Config;
import com.pattexpattex.servergods.util.BotCommand;
import com.pattexpattex.servergods.util.MessageUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

//This class randomly generates an algorithm for shuffling a Rubik's 3x3 cube
//
//Don't actually look at this code, it's totally cursed
//In fact it is so cursed, I don't recommend you enabling this in the config.json
public class ShuffleCommand implements BotCommand {

    //All the possible permuts for a 3x3
    String[] standardPermuts = {"U", "D", "L", "R", "F", "B",
            "U'", "D'", "L'", "R'", "F'", "B'",
            "U2", "D2", "L2", "R2", "F2", "B2"};

    //All the possible permuts for a 4x4
    //Not actually implemented yet
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

    //I won't even comment the code sections, it is that cursed and should never be used
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

            Message reply = message.reply(MessageUtils.defaultEmbed("Shuffled!", prettyAlgorithm,
                    null, null).build()).complete();
            MessageUtils.deleteMessage(reply, 120);
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

                Message reply = message.reply(MessageUtils.defaultEmbed("Shuffled!", prettyAlgorithm,
                        null, null).build()).complete();
                MessageUtils.deleteMessage(reply, 120);

            } catch (NumberFormatException e) {
                Message reply = message.reply(MessageUtils.errorEmbed("shuffle", e).build()).complete();
                MessageUtils.deleteMessage(reply, 120);
            }
        }

        MessageUtils.deleteMessage(message, 120);

        //I warned ya, it is cursed :)
    }

    @Override
    public String getHelp() {
        return "Gives you a random algorithm to shuffle a Rubik's cube";
    }

    @Override
    public Object isEnabled() {
        return CONFIG.getConfigValue(Config.EnableCommand.SHUFFLE);
    }
}
