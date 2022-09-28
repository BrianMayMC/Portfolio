package me.nootnoot.jackpotmcteams.storage;

import lombok.Getter;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLManager {
	@Getter
	private final DatabaseConnectionPoolHandler pool;

	public SQLManager() {
		pool = new DatabaseConnectionPoolHandler();
		makeTable();
	}

	private void makeTable(){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = pool.getConnection();
			ps = conn.prepareStatement("CREATE TABLE IF NOT EXISTS "+ JackpotMCTeams.getInstance().getTable()+" (" +
					"  NAME CHAR(100) NOT NULL," +
					"  KILLS INT NOT NULL," +
					"  UUID CHAR(100) NOT NULL," +
					"  PRIMARY KEY (NAME)" +
					")");
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			pool.close(conn, ps, null);
		}
	}

	public void deleteTeam(Team team){
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = pool.getConnection();
			ps = conn.prepareStatement("DELETE FROM " + JackpotMCTeams.getInstance().getTable() +" WHERE NAME=?");
			ps.setString(1, team.getName());
			ps.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			pool.close(conn, ps, null);
		}
	}


	public void close(){
		pool.closePool();
	}
}
