package me.nootnoot.jackpotmcteams.storage;

import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class TeamTopDatabaseTask extends BukkitRunnable {

	private final Map<String, Team> teams;
	private final SQLManager sql;

	public TeamTopDatabaseTask(Map<String, Team> teams, SQLManager sql){
		this.teams = teams;
		this.sql = sql;
	}

	@Override
	public void run() {
		for(String s : teams.keySet()) {
			try {
				Connection conn = sql.getPool().getConnection();
				PreparedStatement exists = conn.prepareStatement("SELECT * FROM " + JackpotMCTeams.getInstance().getTable() + " WHERE NAME=?");
				exists.setString(1, teams.get(s).getName());
				ResultSet existsSet = exists.executeQuery();
				PreparedStatement insert;
				if(!(existsSet.next())){
					insert = conn
							.prepareStatement("INSERT INTO " + JackpotMCTeams.getInstance().getTable() + " (NAME,KILLS,UUID) VALUES (?,?,?)");
					insert.setString(1, teams.get(s).getName());
					insert.setLong(2, teams.get(s).getKills());
					insert.setString(3, teams.get(s).getOwnerUUID().toString());
					insert.executeUpdate();
				}else{
					insert = conn
							.prepareStatement("UPDATE " + JackpotMCTeams.getInstance().getTable() + " SET KILLS=?, UUID=? WHERE NAME=?");
					insert.setString(3, teams.get(s).getName());
					insert.setLong(1, teams.get(s).getKills());
					insert.setString(2, teams.get(s).getOwnerUUID().toString());
					insert.executeUpdate();
				}
				sql.getPool().close(conn, exists, existsSet);
				sql.getPool().close(null, insert, null);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
