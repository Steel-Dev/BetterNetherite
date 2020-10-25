package com.github.steeldev.betternetherite.util;

import java.util.logging.Logger;

public class BNLogger extends Logger {

    protected BNLogger(String name, String resourceBundleName) {
        super(name, resourceBundleName);
    }

    public static BNLogger getLogger() {
        return new BNLogger("", null);
    }

    @Override
    public void info(String msg) {
        String prefix = msg.replace("[NBTAPI]", Util.getNbtapiPrefix());
        Util.log(prefix);
    }
}
