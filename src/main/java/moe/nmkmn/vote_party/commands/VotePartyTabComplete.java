package moe.nmkmn.vote_party.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class VotePartyTabComplete implements TabCompleter {
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> sub = new ArrayList<>();
            sub.add("help");
            sub.add("start");
            sub.add("reload");

            return sub;
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("start")) {
                List<String> sub = new ArrayList<>();
                sub.add("<数字を指定>");

                return sub;
            }
        }

        return  null;
    }
}
