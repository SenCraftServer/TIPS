package sensen1234.sencraft.tips;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class TIPS extends JavaPlugin {

    private List<String> tips;
    private Random random;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // 保存默认配置文件
        loadConfig(); // 加载配置文件
        startTipTask(); // 启动定时任务
    }

    private void loadConfig() {
        FileConfiguration config = getConfig();
        tips = config.getStringList("tips");
        random = new Random();
    }

    private void startTipTask() {
        int interval = getConfig().getInt("tipInterval", 600); // 获取间隔时间，默认为600秒（10分钟）
        new BukkitRunnable() {
            @Override
            public void run() {
                sendRandomTip();
            }
        }.runTaskTimer(this, interval * 20, interval * 20); // 以ticks为单位，每个tick为1/20秒
    }

    private void sendRandomTip() {
        if (!tips.isEmpty()) {
            String tip = tips.get(random.nextInt(tips.size()));
            Bukkit.broadcastMessage("[TIPS] " + tip); // 发送小提示到公屏
        }
    }
}
