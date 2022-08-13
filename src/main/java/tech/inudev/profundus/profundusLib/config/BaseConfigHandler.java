package tech.inudev.profundus.profundusLib.config;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

/**
 * tech.inudev.profundus.config.ymlファイルを扱いやすくするために作られたHandler
 *
 * @author tererun
 */
abstract public class BaseConfigHandler {

    protected final Plugin plugin;
    protected final FileConfiguration config;

    @Getter
    protected int configVersion;
    @Getter
    protected String databaseAddress;
    @Getter
    protected String databaseName;
    @Getter
    protected String databaseUsername;
    @Getter
    protected String databasePassword;
    @Getter
    protected String databaseType;

    /**
     * コンストラクタ
     *
     * @param plugin プラグイン
     */
    public BaseConfigHandler(Plugin plugin) {
        plugin.saveDefaultConfig();
        this.plugin = plugin;
        this.config = plugin.getConfig();

        configVersion = config.getInt("config_version");

        String databasePath = "database.";
        databaseType = config.getString(databasePath + "type");
        databaseAddress = config.getString(databasePath + "address");
        databaseName = config.getString(databasePath + "database");
        databaseUsername = config.getString(databasePath + "username");
        databasePassword = config.getString(databasePath + "password");

        this.init();
    }

    abstract protected void init();

    /**
     * コンフィグを保存する
     */
    public void saveConfig() {
        plugin.saveConfig();
    }

    /**
     * コンフィグをリロードする
     */
    public void reloadConfig() {
        plugin.reloadConfig();
    }

}
