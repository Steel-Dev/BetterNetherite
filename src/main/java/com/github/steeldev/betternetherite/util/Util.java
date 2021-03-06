package com.github.steeldev.betternetherite.util;

import com.github.steeldev.betternetherite.BetterNetherite;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.md_5.bungee.api.chat.TextComponent.fromLegacyText;

public class Util {
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f0-9]){6}>");
    private static final String PREFIX = "&7[&6Better&cNetherite&7] ";
    private static final String NBTAPI_PREFIX = "&7[&6NBT&cAPI&7]";
    public static Random rand = new Random();
    public static String[] version;
    static BetterNetherite main;

    public static BetterNetherite getMain() {
        if (main == null) main = BetterNetherite.getInstance();
        return main;
    }

    public static String colorize(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        while (matcher.find()) {
            final net.md_5.bungee.api.ChatColor hexColor = net.md_5.bungee.api.ChatColor.of(matcher.group().substring(1, matcher.group().length() - 1));
            final String before = string.substring(0, matcher.start());
            final String after = string.substring(matcher.end());
            string = before + hexColor + after;
            matcher = HEX_PATTERN.matcher(string);
        }
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static boolean chanceOf(int chance) {
        return rand.nextInt(100) < chance;
    }

    public static boolean chanceOf(float chance) {
        return rand.nextFloat() < chance;
    }

    // Just to make, for example; 'DIAMOND_SWORD' look nicer in a string
    //  it'll look like 'Diamond Sword' instead. This is mainly used
    //  for printing an item in chat, and have it not look so
    //  ugly and such for users.
    public static String formalizedString(String string) {
        String[] itemSplit = string.toLowerCase().split("_");
        StringBuilder finalIt = new StringBuilder();
        for (int i = 0; i < itemSplit.length; i++) {
            finalIt.append(itemSplit[i].substring(0, 1).toUpperCase() + itemSplit[i].substring(1));
            if (i < itemSplit.length - 1)
                finalIt.append(" ");
        }
        return finalIt.toString();
    }

    public static String getNbtapiPrefix() {
        return NBTAPI_PREFIX;
    }

    public static String getUncoloredItemName(ItemStack item) {
        String name = (item.getItemMeta() == null) ? formalizedString(item.getType().toString()) : item.getItemMeta().getDisplayName();
        return ChatColor.stripColor(name);
    }

    public static void log(String log) {
        if (main == null) main = BetterNetherite.getInstance();
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + log));
    }

    private static void send(CommandSender receiver, String format, Object... objects) {
        receiver.sendMessage(colorize(String.format(format, objects)));
    }

    public static void log(String format, Object... objects) {
        if (main == null) main = BetterNetherite.getInstance();
        Bukkit.getConsoleSender().sendMessage(colorize(PREFIX + String.format(format, objects)));
    }

    public static void sendMessage(CommandSender receiver, String format, Object... objects) {
        if (receiver == null || receiver instanceof ConsoleCommandSender) {
            log(format, objects);
        } else {
            send(receiver, format, objects);
        }
    }

    public static void sendActionBar(Player receiver, String format, Object... objects) {
        if (receiver == null) return;
        receiver.spigot().sendMessage(ChatMessageType.ACTION_BAR, fromLegacyText(colorize(String.format(format, objects))));
    }

    public static void sendTitle(Player receiver, String title, String format, Object... objects) {
        if (receiver == null) return;
        receiver.sendTitle(colorize(title), colorize(String.format(format, objects)));
    }

    public static void broadcast(String format, Object... params) {
        Bukkit.getServer().broadcastMessage(colorize(String.format(format, params)));
    }

    public static void unregisterEvents(Listener listener) {
        HandlerList.unregisterAll(listener);
    }

    public static void registerEvent(Listener listener) {
        main.getServer().getPluginManager().registerEvents(listener, main);
    }

    public static void registerCommand(String command, CommandExecutor commandExecutor) {
        main.getCommand(command).setExecutor(commandExecutor);
    }

    public static boolean monstrorvmEnabled(){
        return getMain().monstrorvmPlugin != null && getMain().monstrorvmPlugin.isEnabled();
    }

    // Credit to : CoKoC on spigotmc for this snippet (https://www.spigotmc.org/threads/how-to-rotate-mobs-around-one-location.80498/)
    public static Location getLocationAroundCircle(Location center, double radius, double angleInRadian) {
        double x = center.getX() + radius * Math.cos(angleInRadian);
        double z = center.getZ() + radius * Math.sin(angleInRadian);
        double y = center.getY();

        Location loc = new Location(center.getWorld(), x, y, z);
        Vector difference = center.toVector().clone().subtract(loc.toVector()); // this sets the returned location's direction toward the center of the circle
        loc.setDirection(difference);

        return loc;
    }

    // Thank you Big_Bad_E for helping me with this <3
    public static boolean isRingValid(int ring, Block centerBlock, Material matchType, Material offsetType) {
        for (int x = -ring; x <= ring; x++) {
            for (int z = -ring; z <= ring; z++) {
                if (x > -ring && x < ring && z > -ring && z < ring)
                    continue;
                Block block = centerBlock.getRelative(x, 0, z);

                boolean xEven = (x % 2 == 0);
                boolean zEven = (z % 2 == 0);

                boolean blocksEven = xEven == zEven;
                if (blocksEven) {
                    if (!block.getType().equals(matchType))
                        return false;
                } else if (!block.getType().equals(offsetType))
                    return false;
            }
        }
        return true;
    }

    // Thanks to ShaneBee for providing these functions

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major Major version to check (Most likely just going to be 1)
     * @param minor Minor version to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor) {
        return isRunningMinecraft(major, minor, 0);
    }

    /**
     * Check if server is running a minimum Minecraft version
     *
     * @param major    Major version to check (Most likely just going to be 1)
     * @param minor    Minor version to check
     * @param revision Revision to check
     * @return True if running this version or higher
     */
    public static boolean isRunningMinecraft(int major, int minor, int revision) {
        if (version == null) version = Bukkit.getServer().getBukkitVersion().split("-")[0].split("\\.");
        int maj = Integer.parseInt(version[0]);
        int min = Integer.parseInt(version[1]);
        int rev;
        try {
            rev = Integer.parseInt(version[2]);
        } catch (Exception ignore) {
            rev = 0;
        }
        return maj > major || min > minor || (min == minor && rev >= revision);
    }

    public static String getVersionString() {
        return version[0] + "." + version[1] + "." + version[2];
    }
}
