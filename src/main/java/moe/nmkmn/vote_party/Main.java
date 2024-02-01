package moe.nmkmn.vote_party;

import moe.nmkmn.vote_party.commands.VoteParty;
import moe.nmkmn.vote_party.commands.VotePartyTabComplete;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getCommand("voteparty").setExecutor(new VoteParty(this));
        getCommand("voteparty").setTabCompleter(new VotePartyTabComplete());
    }
}
