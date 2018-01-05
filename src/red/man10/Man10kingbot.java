package red.man10;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Man10kingbot extends JavaPlugin {
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player)sender;
        if(!p.hasPermission("man10.bot.reload")){
            p.sendMessage(prefix + "§c§lあなたにbotの設定をreloadする権限はありません！");
            return true;
        }
		switch(args.length) {
		 case 1:
			 if(args[0].equalsIgnoreCase("reload")) {
					map.clear();
					reloadConfig();
					map = new HashMap<String, String>() {};
				    saveDefaultConfig();
				    FileConfiguration config = getConfig();
			        config1 = config;
					for (String key : config1.getConfigurationSection("bot").getKeys(false)) {
						String a = config1.getString("bot."+key+".targetword");
						String b = config1.getString("bot."+key+".message");
						String c = config1.getString("bot."+key+".runcommand");
						String d = config1.getString("bot."+key+".global?");
						String e = config1.getString("bot."+key+".batword?");
					          map.put(a, b);
					          map.put(a+"1", c);
					          map.put(a+"2", d);
					          map.put(a+"3", e);
					}
					p.sendMessage(prefix + "§aリロードが完了しました。");
				 return true;
			 }
		}
		return true;
	}
	public static FileConfiguration config1;
	Plugin pl = Man10kingbot.this;
	String prefix = "§6[§aM§fa§dn§r§rbot§6]§r";
	private HashMap<String, String> map;
	@Override
	public void onDisable() {
		map.clear();
		reloadConfig();
		super.onDisable();
	}

	@Override
	public void onEnable() {
		new bot(this);
		map = new HashMap<String, String>() {};
		    saveDefaultConfig();
		    FileConfiguration config = getConfig();
	        config1 = config;
			for (String key : config1.getConfigurationSection("bot").getKeys(false)) {
				String a = config1.getString("bot."+key+".targetword");
				String b = config1.getString("bot."+key+".message");
				String c = config1.getString("bot."+key+".runcommand");
				String d = config1.getString("bot."+key+".global?");
				String e = config1.getString("bot."+key+".batword?");
			          map.put(a, b);
			          map.put(a+"1", c);
			          map.put(a+"2", d);
			          map.put(a+"3", e);
			}
		getCommand("mbot").setExecutor(this);
		super.onEnable();
	}
	public class bot implements Listener {
	    public bot(Man10kingbot plugin) {
	        plugin.getServer().getPluginManager().registerEvents(this, plugin);
	    }
		@EventHandler
	    public void onChat(AsyncPlayerChatEvent e) {
	    	String m = e.getMessage();
	        for (String nKey : map.keySet()){
		    	if(m.contains(nKey)) { 
				    String s = map.get(nKey);
				    String s1 = map.get(nKey+"1");
				    String s3 = s.replace("&", "§");
							String s5 = map.get(nKey+"3");
							if(s5.equals("yes")==true) {
							e.setCancelled(true);
							String newMessage = e.getMessage().replace(nKey, "§f§l [botにより規制] §r");
							e.getPlayer().chat(newMessage);
							}else {
							e.getPlayer().chat(m);
							}
							String s4 = map.get(nKey+"2");
							if(s4.equals("yes")==true) {
							Bukkit.broadcastMessage(prefix+s3);
							}else{
							e.getPlayer().sendMessage(prefix+s3);
							}
							if(s1 != null) {
							String s2 = s1.replace("<player_name>", e.getPlayer().getName().toString());
								ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
			   	            	String command = s2;
			   	            	Bukkit.dispatchCommand(console, command);
							}
						}
	    		 }
	    }
		
	}
}
