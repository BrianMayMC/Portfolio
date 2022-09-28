package me.nootnoot.jackpotmcteams.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnectionPoolHandler {

	private HikariDataSource dataSource;

	public DatabaseConnectionPoolHandler() {
		setupPool();
	}

	private void setupPool(){
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://10.0.0.1:3306/s2_nootteams");
		config.setDriverClassName("com.mysql.jdbc.Driver");
		config.setUsername("u2_kInB4qccmp");
		config.setPassword("Ry.^j3fVKIangu!V5J6vrit1");
		config.setMinimumIdle(5);
		config.setMaximumPoolSize(32);
		config.setMaxLifetime(3600000);

		dataSource = new HikariDataSource(config);
	}



	public void close(Connection conn, PreparedStatement ps, ResultSet res) {
		if (conn != null) try { conn.close(); } catch (SQLException ignored) {}
		if (ps != null) try { ps.close(); } catch (SQLException ignored) {}
		if (res != null) try { res.close(); } catch (SQLException ignored) {}
	}

	public Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}

	public void closePool() {
		if (dataSource != null && !dataSource.isClosed()) {
			dataSource.close();
		}
	}
}
