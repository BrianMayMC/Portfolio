package me.nootnoot.headhunting.data;

import nootnoot.spigotlib.data.SQLUtils;
import nootnoot.spigotlib.data.SQLiteHandler;
import me.nootnoot.headhunting.HeadHunting;
import me.nootnoot.headhunting.entities.HHPlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerSQLLiteHandler extends SQLiteHandler {

	private static PlayerSQLLiteHandler instance;

	private static final String tableName = "player_data";

	public PlayerSQLLiteHandler() {
		super("players", "data", HeadHunting.getInstance());
	}

	public static PlayerSQLLiteHandler getInstance(){
		if(instance != null)
			return instance;
		return instance = new PlayerSQLLiteHandler();
	}

	@Override
	protected void setupTables() throws SQLException {
		if(!super.getConnection().getMetaData().getTables(null, null, tableName, null).next()){
			super.write(String.format("CREATE TABLE %s (uuid VARCHAR(36), name VARCHAR(36), level INT, " +
					"soldheads INT, souls INT);", tableName));
			super.getConnection().close();
		}
	}

	public boolean isRegistered(UUID uuid){
		ResultSet rs = super.read(String.format("SELECT * FROM %s WHERE UUID='&s';", tableName, uuid.toString()));
		return SQLUtils.hasNext(rs);
	}

	public void registerPlayer(UUID uuid, String name){
		super.write(String.format("INSERT INTO %s (uuid, name, level, soldheads, souls) VALUES " +
				"('%s', '%s', 1, 0, 0);", tableName, uuid.toString(), name));
	}

	public String getName(UUID uuid){
		return SQLUtils.getStringFromSet(super.read(String.format("SELECT name FROM %s WHERE uuid='%s';", tableName, uuid.toString())),
				"name");
	}

	public void setName(UUID uuid, String name){
		super.write(String.format("UPDATE %s SET name='%s' WHERE uuid='%s';", tableName, name, uuid.toString()));
	}

	public HHPlayer loadPlayer(UUID uuid){
		ResultSet rs = read(String.format("SELECT * FROM %s where uuid='%s'", tableName, uuid.toString()));
		String name = SQLUtils.getObjectFromSet(rs, "name", String.class, false, true);
		int level = SQLUtils.getObjectFromSet(rs, "level", Integer.class, true, true);
		int soldheads = SQLUtils.getObjectFromSet(rs, "soldheads", Integer.class, true, true);
		int souls = SQLUtils.getObjectFromSet(rs, "souls", Integer.class, true, false);

		return new HHPlayer(uuid, name, level, soldheads, souls);
	}

	public void savePlayer(HHPlayer p){
		super.write(String.format("UPDATE %s SET level=%s, soldheads=%s, souls=%s WHERE uuid='%s';", tableName, p.getLevel(),
				p.getSoldHeads(), p.getSouls(), p.getUuid()));
	}
}
