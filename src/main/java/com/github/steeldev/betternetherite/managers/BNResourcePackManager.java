package com.github.steeldev.betternetherite.managers;

import com.github.steeldev.betternetherite.util.Message;

import java.io.IOException;

import static com.github.steeldev.betternetherite.util.Util.getMain;

public class BNResourcePackManager {
    static final String latestResourcePackURL = "https://www.dropbox.com/s/hty7va5p8ghqt1b/BetterNetherite-ResourcePack-v3.zip?dl=1";

    public static void checkResourcePack() throws IOException {
        if (getMain().config.RESOURCE_PACK_ENABLED &&
                getMain().config.RESOURCE_PACK_AUTO_UPDATE) {
            Message.COMPARING_RESOURCE_PACK.log();
            if (!getMain().config.RESOURCE_PACK_URL.equals(latestResourcePackURL)) {
                Message.RESOURCE_PACK_LINKS_DONT_MATCH.log();
                getMain().config.setString("ResourcePack.URL", latestResourcePackURL);
                getMain().loadConfigurations();
                return;
            }
            Message.RESOURCE_PACK_LINKS_MATCH.log();
        }
    }
}
