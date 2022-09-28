package me.nootnoot.jackpotmcteams.storage;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.entities.Team;
import me.nootnoot.jackpotmcteams.utils.GsonFactory;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TeamStorage {

	@Getter
	public ConcurrentHashMap<String, Team> teams = new ConcurrentHashMap<>();
	private final File fileName;
	private int nextID;

	private final SQLManager sql;

	@Getter private LinkedHashMap<String, Team> orderedTeams = new LinkedHashMap<>();
	@Getter private LinkedHashMap<String, Team> orderedByOnlinePlayers = new LinkedHashMap<>();

	public TeamStorage(SQLManager sql){
		this.fileName = new File(JackpotMCTeams.getInstance().getDataFolder(), "teams.json");
		this.nextID = 1;
		this.sql = sql;

		try{
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load() throws IOException {
		if (!fileName.exists()) {
			fileName.getParentFile().mkdirs();
			fileName.createNewFile();
		} else try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
			ConcurrentHashMap<String, Team> objectMap = GsonFactory.getCompactGson().fromJson(fileReader, new TypeToken<ConcurrentHashMap<String, Team>>() {
			}.getType());

			if (objectMap == null) {
				teams = new ConcurrentHashMap<>();
			} else {
				teams = objectMap;
			}
		}
	}

	public void save() {
		try (FileWriter fileWriter = new FileWriter(fileName)) {
			fileWriter.write(GsonFactory.getPrettyGson().toJson(teams));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveToDatabase(){
		new TeamTopDatabaseTask(teams, sql).runTaskTimerAsynchronously(JackpotMCTeams.getInstance(), JackpotMCTeams.getInstance().getConfig().getLong("team-top-save-delay"), JackpotMCTeams.getInstance().getConfig().getLong("team-top-save-delay"));
	}
	public void deleteTeam(Team team){
		sql.deleteTeam(team);
	}

	public void createTeam(String name, Player owner, int rank) {
		String teamID = getNextID();
		Team team = new Team(teamID, name, owner.getUniqueId(), rank);
		teams.put(teamID, team);
	}

	public void RemoveTeam(Team team){
		for(Team findTeam : teams.values()){
			if(team.getTeamID().equalsIgnoreCase(findTeam.getTeamID())){
				teams.remove(findTeam.getTeamID());
			}
		}
	}

	public void updateOrderedTeams() {
		List<Map.Entry<String, Team>> list = new LinkedList<>(teams.entrySet());
		list.sort((((o1, o2) -> Long.compare(o2.getValue().getKills(), o1.getValue().getKills()))));
		LinkedHashMap<String, Team> sortedTeams = new LinkedHashMap<>();
		for(Map.Entry<String, Team> entry : list) {
			sortedTeams.put(entry.getKey(), entry.getValue());
		}
		//

		this.orderedTeams = sortedTeams;
	}

	public void updateOrderedOnlineTeams(){
		List<Map.Entry<String, Team>> list = new LinkedList<>(teams.entrySet());

		list.sort((((o1, o2) -> Integer.compare(o2.getValue().GetOnlinePlayers().size(), o1.getValue().GetOnlinePlayers().size()))));
		LinkedHashMap<String, Team> sortedTeams = new LinkedHashMap<>();
		for(Map.Entry<String, Team> entry : list) {
			sortedTeams.put(entry.getKey(), entry.getValue());
		}

		this.orderedByOnlinePlayers = sortedTeams;
	}

	/**
	 * ID Management
	 */

	public String getNextID() {
		// O(n) -> Iterates 60 times to find ID
		// O(1) -> Is this ID Free? Yes, return
		while(!checkID(this.nextID)) {
			this.nextID++;
		}
		return Integer.toString(nextID);
	}

	public boolean checkID(String id) {
		return !this.teams.containsKey(id);
	}

	public boolean checkID(int id) {
		return this.checkID(Integer.toString(id));
	}
}
