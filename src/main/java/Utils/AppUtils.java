package Utils;

import Client.Client;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class AppUtils {
    public static Logger initLogger(Class c, boolean isClient) throws IOException {
        Properties props = new Properties();
        props.load(c.getClassLoader().getResourceAsStream("project.properties"));
        Logger LOGGER=Logger.getLogger(c.getName());
        FileHandler fh = new FileHandler(isClient ? props.getProperty("LOG.CLIENT") : props.getProperty("LOG.SERVER"));
        LOGGER.addHandler(fh);
        LOGGER.setUseParentHandlers(false);
        return LOGGER;
    }
}
