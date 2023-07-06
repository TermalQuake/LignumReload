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
        getCommand("ligreload").setExecutor(new Logic());
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("всё сломалось :с");
    }
    public class Logic implements CommandExecutor {
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
            if (cmd.getName().equalsIgnoreCase("ligreload")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (!player.isOp()) { // Проверяем, является ли игрок администратором
                        player.sendMessage(ChatColor.RED + "У вас нет прав для выполнения этой команды.");
                        return true;
                    }
                }
                Bukkit.savePlayers();
                for (World world : Bukkit.getWorlds()) {
                    world.save();
                    System.out.println("out.мир сохранился");
                }
                Player player = (Player) sender;
                sender.sendMessage(ChatColor.GREEN + "Технический перезапуск сервера через:");
                player.playSound(player.getLocation(), "minecraft:entity.cat.ambient", 1.0f, 1.0f);
                for (int i = 5; i >= 0; i--) {
                    if (i == 0) {
                        sender.sendMessage(ChatColor.RED + "Пожалуйста не двигайтесь.");
                    } else {
                        sender.sendMessage(ChatColor.YELLOW + String.valueOf(i));
                        player.playSound(player.getLocation(), "minecraft:entity.experience_orb.pickup", 1.0f, 1.0f);
                    }
                    try {
                        Thread.sleep(1000); // ждём 1сек
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                String title = ChatColor.RED + "Перезапуск...";
                String subtitle = ChatColor.YELLOW.toString() + "Пожалуйста, подождите.";
                int fadeIn = 10; // в тиках
                int stay = 70; // в тиках
                int fadeOut = 20; // в тиках
                player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
                Bukkit.getServer().reload();
                System.out.println("out.перезапуск успешен");
                player.playSound(player.getLocation(), "minecraft:entity.player.levelup", 1.0f, 1.0f);
                sender.sendMessage(ChatColor.GREEN + "Перезагрузка прошла успешно.");
                return true;
            }
            return false;
        }
    }
}
