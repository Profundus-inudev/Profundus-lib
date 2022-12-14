package tech.inudev.profundus.profundusLib.interfaces;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import tech.inudev.profundus.profundusLib.config.BaseConfigHandler;

/**
 * configHandlerが記述されているJavaPluginを表現するclass
 */
public class JavaPluginWithConfigHandler extends JavaPlugin {
    @Getter
    private BaseConfigHandler configHandler;
}
