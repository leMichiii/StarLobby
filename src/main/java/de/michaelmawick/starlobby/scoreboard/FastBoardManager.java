package de.michaelmawick.starlobby.scoreboard;

import de.michaelmawick.starlobby.StarLobby;
import de.michaelmawick.starlobby.scoreboard.animate.FrameAnimatedString;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class FastBoardManager {

    public static List<FastBoard> boards = new ArrayList<>();
    private static HashMap<UUID, BukkitTask> tasks = new HashMap<>();

    private static final HashMap<UUID, Integer> friendsOnline = new HashMap<>();

    public static void createScoreBoard(Player player) {

        FastBoard fastBoard = new FastBoard(player);
        boards.add(fastBoard);
        List<String> titleList = StarLobby.getInstance().getConfig().getStringList("scoreboard.title");
        FrameAnimatedString fas = new FrameAnimatedString(titleList);

        tasks.put(player.getUniqueId(), new BukkitRunnable() {
            @Override
            public void run() {
                if (fastBoard.isDeleted()) {
                    cancel();
                    return;
                }
                fastBoard.updateTitle(fas.next());

                List<String> lines = new ArrayList<>();


                List<String> linelist = StarLobby.getInstance().getConfig().getStringList("scoreboard.content");

                lines.addAll(linelist);

                fastBoard.updateLines(lines);

            }
        }.runTaskTimer(StarLobby.getInstance(), 0, 5));
    }

    public static void handleQuit(UUID uuid) {
        BukkitTask t = tasks.getOrDefault(uuid, null);
        if (t == null) return;
        t.cancel();
    }

    public static void stop() {
        for (UUID u : tasks.keySet()) {
            tasks.get(u).cancel();
            tasks.remove(u);
        }
        for (FastBoard b : boards) b.delete();
    }

    public static void restart() {
        stop();

        for (Player p : Bukkit.getOnlinePlayers()) {
            createScoreBoard(p);
        }
    }

}
