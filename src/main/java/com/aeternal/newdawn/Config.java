package com.aeternal.newdawn;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public final class Config {



    public static void loadNormalConfig(final File configFile) {
        Core.LOGGER.info("Loading NewDawn Config from " + configFile.getAbsolutePath());

        final Configuration config = new Configuration(configFile);
        try {

        } catch (Exception e) {
            Core.LOGGER.fatal("Fatal error reading config file.", e);
            throw new RuntimeException(e);
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }

    }
}





