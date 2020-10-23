package com.github.steeldev.betternetherite.util;

import com.github.steeldev.betternetherite.BetterNetherite;
import com.github.steeldev.betternetherite.config.Lang;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

//https://www.spigotmc.org/resources/better-netherite.84526
//https://api.spigotmc.org/legacy/update.php?resource=84526
public class UpdateChecker {
    static BetterNetherite main = BetterNetherite.getInstance();
    JavaPlugin plugin;
    int resourceID;

    public UpdateChecker(JavaPlugin plugin,
                         int resourceID) {
        this.plugin = plugin;
        this.resourceID = resourceID;
    }

    public static void sendNewUpdateMessageToPlayer(Player player) {
        if (!player.isOp() && !player.hasPermission("betternetherite.admin")) return;

        if (!main.outdated) return;

        player.sendMessage(main.colorize(String.format("%s&a&oA new version of &6&oBetter &7&oNetherite&a&o is available! &7&o(Current: %s, Latest: %s)", Lang.PREFIX, main.getDescription().getVersion(), main.newVersion)));
        TextComponent link = new TextComponent(main.colorize("&6&lClick here to update"));
        link.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/better-netherite.84526"));
        player.spigot().sendMessage(link);
    }

    public void getVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL(String.format("https://api.spigotmc.org/legacy/update.php?resource=%d", this.resourceID)).openStream(); Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    consumer.accept(scanner.next());
                }
            } catch (IOException exception) {
                this.plugin.getLogger().info(String.format("Cannot look for updates: %s", exception.getMessage()));
            }
        });
    }
}
