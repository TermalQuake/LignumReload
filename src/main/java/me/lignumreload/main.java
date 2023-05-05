package me.lignumreload;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("всё работает!");
        getCommand("ligreload").setExecutor(new logic());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("всё сломалось :с");
    }
    public class logic implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (cmd.getName().equalsIgnoreCase("ligreload")) {
                if(sender instanceof Player) { // Проверяем, что команду вызвал игрок
                    Bukkit.savePlayers();
                    for (World world : Bukkit.getWorlds()) {
                        world.save();
                        System.out.println("out.мир сохранился");
                    }

                    Player player = (Player) sender; // Приводим CommandSender к типу Player
                    sender.sendMessage(ChatColor.GREEN + "Технический перезапуск сервера через:");
                    player.playSound(player.getLocation(), "minecraft:entity.cat.ambient", 1.0f, 1.0f); // Проигрываем звук кота
                    // отсчёт с 5 до 0
                    for (int i = 5; i >= 0; i--) {
                        if (i == 0) {
                            sender.sendMessage(ChatColor.RED + "Пожалуйста не двигайтесь.");
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + String.valueOf(i));
                            player.playSound(player.getLocation(), "minecraft:entity.experience_orb.pickup", 1.0f, 1.0f); // Проигрываем звук лвл
                        }
                        try {
                            Thread.sleep(1000); // ждём 1 сек
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    String title = ChatColor.RED + "Перезапуск..."; // Заголовок сообщения
                    String subtitle = ChatColor.YELLOW.toString() + "Пожалуйста, подождите."; // Подзаголовок сообщения
                    int fadeIn = 10; // Время появления заголовка (в тиках)
                    int stay = 70; // Время отображения заголовка (в тиках)
                    int fadeOut = 20; // Время исчезновения заголовка (в тиках)
                    player.sendTitle(title, subtitle, fadeIn, stay, fadeOut); // Отправляем заголовок игроку
                }
                Bukkit.getServer().reload(); // Перезагружаем сервер
                System.out.println("out.перезапуск успешен");
                Player player = (Player) sender; // Переводим CommandSender в Player
                player.playSound(player.getLocation(), "minecraft:entity.player.levelup", 1.0f, 1.0f); // Проигрываем звук лвл апа
                sender.sendMessage(ChatColor.GREEN + "Перезагрузка прошла успешно.");
                return true;
            }
            return false;
        }
    }
}
