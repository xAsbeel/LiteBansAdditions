package pl.minecodes.litebansadditions.util;

import org.bukkit.Bukkit;
import pl.minecodes.litebansadditions.LiteBansAdditions;

/**
 * Util class to run tasks.
 * Owning plugin of every task scheduled here is "LiteBansAdditions".
 */
public final class SchedulerUtil {

    private static final LiteBansAdditions PLUGIN = LiteBansAdditions.getInstance();

    private SchedulerUtil() {}

    /**
     * Runs task asynchronously.
     *
     * @param runnable - task to run
     * @return task id
     */
    public static int runAsynchronously(Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(PLUGIN, runnable).getTaskId();
    }

    /**
     * Runs task on the main thread.
     *
     * @param runnable - task to run
     * @return task id
     */
    public static int run(Runnable runnable) {
        return Bukkit.getScheduler().runTask(PLUGIN, runnable).getTaskId();
    }

    /**
     * Runs task on the main thread with given delay.
     *
     * @param task - task to run
     * @param delay - delay in ticks
     * @return task id
     */
    public static int runTaskLater(Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLater(PLUGIN, task, delay).getTaskId();
    }

    /**
     * Runs task asynchronously with given delay.
     *
     * @param task - task to run
     * @param delay - delay in ticks
     * @return task id
     */
    public static int runTaskLaterAsync(Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(PLUGIN, task, delay).getTaskId();
    }

    /**
     * Runs task in repeat on the main thread.
     *
     * @param task - task to run
     * @param delay - initial delay in ticks
     * @param period - period in ticks
     * @return task id
     */
    public static int runTaskTimer(Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(PLUGIN, task, delay, period).getTaskId();
    }

    /**
     * Runs task in repeat asynchronously.
     *
     * @param task - task to run
     * @param delay - initial delay in ticks
     * @param period - period in ticks
     * @return task id
     */
    public static int runTaskTimerAsync(Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(PLUGIN, task, delay, period).getTaskId();
    }

}
