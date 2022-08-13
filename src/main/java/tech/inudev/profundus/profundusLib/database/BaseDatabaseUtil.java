package tech.inudev.profundus.profundusLib.database;

import tech.inudev.profundus.profundusLib.config.BaseConfigHandler;
import tech.inudev.profundus.profundusLib.interfaces.JavaPluginWithConfigHandler;

import java.sql.*;
import java.util.logging.Level;

/**
 * Databaseを管理するためのクラス
 * 絶対にConfigHandler初期化後に使用すること
 *
 * @author tererun
 */

public class BaseDatabaseUtil {

    private static Connection connection;
    private static String databaseUrl;

	private static JavaPluginWithConfigHandler plugin;

	public static void init(JavaPluginWithConfigHandler plugin) {
        BaseDatabaseUtil.plugin = plugin;
		BaseConfigHandler configHandler = plugin.getConfigHandler();
		if(configHandler.getDatabaseType().equals("mysql")) {
			databaseUrl = "jdbc:mysql://$address/$name?useUnicode=true&characterEncoding=utf8&autoReconnect=true&maxReconnects=10&useSSL=false"
					.replace("$address", configHandler.getDatabaseAddress())
					.replace("$name", configHandler.getDatabaseName());
		} else if (configHandler.getDatabaseType().equals("sqlite")) {
			databaseUrl = "jdbc:sqlite:$path".replace("$path", plugin.getDataFolder().getPath() + "/database.db");
		} else {
			throw new IllegalArgumentException("Invalid database type");
		}
	}

    /**
     * Databaseに接続する
     */
    public static void connect() {
		BaseConfigHandler configHandler = plugin.getConfigHandler();
        try {
            connection = DriverManager.getConnection(databaseUrl, configHandler.getDatabaseUsername(), configHandler.getDatabasePassword());
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Couldn't connect DB");
            throw new RuntimeException(e);
        }
    }

    /**
     * Databaseから切断する
     */
    public static void disconnect() {
        try {
            connection.close();
            connection = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * DatabaseにPingを送信する
     * Connectionが切断されないように送信する用
     */
    public static void ping() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
            VALUES('ping', current_timestamp)
            """);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
/**
 * connection.commitするだけ
 */
    protected static void commitTransaction() {
        try {
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e2) {
                throw new RuntimeException(e2);
            }
        }
    }
/**
 * 確実にconnectedなインスタンスを取得する。
 * @return
 */
    static protected Connection getConnection(){
    	try {
			if (connection == null || connection.isClosed()) {connect();}
    	} catch(SQLException e) {
    		plugin.getLogger().log(Level.WARNING,"getConnection:" + e);
    	}
    	return connection;
    }
}
