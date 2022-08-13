package tech.inudev.profundus.profundusLib.scheduler;

import org.bukkit.scheduler.BukkitRunnable;
import tech.inudev.profundus.profundusLib.database.BaseDatabaseUtil;

/**
 * DatabaseのConnectionが切断されないように定期的にPingを送るためのクラス
 *
 * @author tererun
 */
public class DatabasePingRunnable extends BukkitRunnable {

    @Override
    public void run() {
        BaseDatabaseUtil.ping();
    }

}
