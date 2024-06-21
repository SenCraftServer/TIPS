package sensen1234.sencraft.tips;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TIPS extends JavaPlugin {

    private List<String> tips;
    private String tipstext;
    private String tipstextcolor;
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
        tipstext = config.getString("tipstext", "");
        tipstextcolor = config.getString("tipstextcolor", "");
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

    private void sendFormattedTip(String message) {
        int maxLineLength = 60; // 定义最大行长度
        List<String> lines = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        // 智能分割消息
        for (String word : message.split(" ")) {
            if (currentLine.length() + word.length() <= maxLineLength) {
                currentLine.append(word).append(" ");
            } else {
                lines.add(currentLine.toString().trim());
                currentLine = new StringBuilder(word + " ");
            }
        }

        // 添加最后一行
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString().trim());
        }

        // 发送消息到公屏
        for (String line : lines) {
            Bukkit.broadcastMessage(line);
        }
    }

    private void sendRandomTip() {
        if (tips != null && !tips.isEmpty()) {
            String tip = tips.get(random.nextInt(tips.size()));
            String formattedTip = tipstextcolor + tipstext + tip;
            sendFormattedTip(formattedTip);
        }
    }
}
