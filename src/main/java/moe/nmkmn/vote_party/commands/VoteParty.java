package moe.nmkmn.vote_party.commands;

import moe.nmkmn.vote_party.Main;
import moe.nmkmn.vote_party.utilts.Reward;
import moe.nmkmn.vote_party.utilts.RewardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class VoteParty implements CommandExecutor {
    public Main plugin;

    public VoteParty (Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        if (command.getName().equalsIgnoreCase("voteparty")) {
            if (args.length == 0) {
                sender.sendMessage("voteparty <help|start [num]|reload|");
                return true;
            } else {
                if (args[0].equalsIgnoreCase("start")) {
                    RewardManager rewardManager = RewardManager.loadFromYaml(plugin.getConfig());
                    Reward reward;

                    if (args.length == 2) {
                        reward = rewardManager.getReward(Integer.parseInt(args[1]));

                        if (reward == null) {
                            sender.sendMessage("対応する報酬が見つかりませんでした");
                            return true;
                        }
                    } else {
                        reward = rewardManager.getRandomReward();

                        if (reward == null) {
                            sender.sendMessage("対応する報酬が見つかりませんでした");
                            return true;
                        }
                    }

                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&6&lVoteParty&r] " + "選ばれた数字は" + reward.getNumber() + "でした！"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&6&lVoteParty&r]" + "[" + reward.getRarity() + "&r] " + reward.getMessage()));
                    for (String cmd : reward.getCommand()) {
                        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);
                    }

                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, 0.3f, 1f);
                        player.playSound(player.getLocation(), "custom.bonus", SoundCategory.RECORDS, 0.2f, 1f);
                        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "&6&l投票パーティー発動！"), "");
                    }

                    return true;
                } else if (args[0].equalsIgnoreCase("reload")) {
                    plugin.reloadConfig();
                    sender.sendMessage("コンフィグをリロードしました！");
                }
            }
        }

        return false;
    }
}
