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

    /**
     * configがあるプラグイン
     */
    protected final Plugin plugin;

    /**
     * configの型
     */
    protected final FileConfiguration config;

    /**
     * configのバージョン
     */
    @Getter
    protected int configVersion;

    /**
     * データベースURL
     */
    @Getter
    protected String databaseAddress;

    /**
     * データベース名
     */
    @Getter
    protected String databaseName;

    /**
     * データベースユーザー名
     */
    @Getter
    protected String databaseUsername;

    /**
     * データベースパスワード
     */
    @Getter
    protected String databasePassword;

    /**
     * データベースタイプ(mysql or sqlite)
     */
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

    /**
     * 独自のconfigの初期化処理を記述する関数。
     */
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
