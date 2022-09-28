package me.nootnoot.jackpotmcteams;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcteams.commands.AdminCommandManager;
import me.nootnoot.jackpotmcteams.commands.CommandManager;
import me.nootnoot.jackpotmcteams.listeners.*;
import me.nootnoot.jackpotmcteams.managers.TeamManager;
import me.nootnoot.jackpotmcteams.placeholders.Placeholders;
import me.nootnoot.jackpotmcteams.storage.SQLManager;
import me.nootnoot.jackpotmcteams.storage.TeamStorage;
import me.nootnoot.jackpotmcteams.tasks.TeamStorageTask;
import me.nootnoot.jackpotmcteams.tasks.UpdateRankTask;
import me.nootnoot.jackpotmcteams.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getScheduler;

public final class JackpotMCTeams extends JavaPlugin {

	@Getter
	private static JackpotMCTeams instance;
	@Getter
	private TeamManager teamManager;
	@Getter
	private CommandManager commandManager;
	@Getter
	private AdminCommandManager adminCommandManager;
	private SQLManager sqlManager;


	@Getter private final List<Player> playerTeleportQueue = new ArrayList<>();

	@Getter
	private Economy eco;

	@Getter@Setter
	private Connection connection;
	@Getter
	private String table;

	@Getter private boolean homes;
	@Getter private boolean balance;



	@Override
	public void onEnable() {
		instance = this;

		if(!setupEconomy()){
			System.out.println(ChatColor.RED + "You must have VAULT and an Economy plugin installed.");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}


		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders().register();
		}

		setupListeners();
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		table = getConfig().getString("database-table");

		sqlManager = new SQLManager();
		setupManagers();


		if(getConfig().getBoolean("database-support-enabled")){
//			mysqlSetup();
//			createTable();
			teamStorage.saveToDatabase();
		}


		homes = getConfig().getBoolean("homes-enabled");
		balance = getConfig().getBoolean("balance-enabled");
		setupCommands();

		(this.teamStorageTask = new TeamStorageTask()).runTaskTimerAsynchronously(this, 20L, 20 * 60);

	}

	@Override
	public void onDisable() {
		teamStorage.save();
		teamManager.clear();
		if(sqlManager != null) sqlManager.close();
		getScheduler().cancelTasks(this);
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		playerTeleportQueue.clear();
	}


	public void setupManagers(){
		teamStorage = new TeamStorage(sqlManager);
		teamManager = new TeamManager();
		commandManager = new CommandManager();
		adminCommandManager = new AdminCommandManager();
	}

	public void setupListeners(){
		getServer().getPluginManager().registerEvents(new TeamChatListener(), this);
		getServer().getPluginManager().registerEvents(new PvpListener(), this);
		getServer().getPluginManager().registerEvents(new KillListener(), this);
		getServer().getPluginManager().registerEvents(new JoinListener(), this);
		getServer().getPluginManager().registerEvents(new MoveListener(), this);
	}

	public boolean setupEconomy(){
		RegisteredServiceProvider<Economy> economy = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if(economy != null){
			eco = economy.getProvider();
		}
		return (eco != null);
	}

	public void setupCommands(){
		getCommand("teams").setExecutor(new CommandManager());
		getCommand("teamsadmin").setExecutor(new AdminCommandManager());
	}

	public void reload(){
		this.reloadConfig();
		this.saveConfig();
		Utils.reload();
	}

//	public void mysqlSetup(){
//		table = getConfig().getString("database-table");
//
//		try{
//			synchronized (this) {
//				if (getConnection() != null && !getConnection().isClosed()) {
//					return;
//				}
//				Class.forName("com.mysql.jdbc.Driver");
//				setConnection(
//						DriverManager.getConnection("jdbc:mysql://10.0.0.1:3306/s2_nootteams", "u2_kInB4qccmp", "Ry.^j3fVKIangu!V5J6vrit1"));
//				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MYSQL CONNECTED");
//			}
//		} catch (SQLException | ClassNotFoundException e) {
//			e.printStackTrace();
//		}
//	}

//	public void createTable() {
//		try {
//			PreparedStatement create = getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS "+JackpotMCTeams.getInstance().getTable()+" (" +
//					"  NAME CHAR(100) NOT NULL," +
//					"  KILLS INT NOT NULL," +
//					"  UUID CHAR(100) NOT NULL," +
//					"  PRIMARY KEY (NAME)" +
//					")");
//			create.execute();
//			System.out.println("table created!");
//		}catch(SQLException e){
//			e.printStackTrace();
//		}
//	}

	@Getter
	private TeamStorage teamStorage;
	private TeamStorageTask teamStorageTask;
	private UpdateRankTask updateRankTask;
}
