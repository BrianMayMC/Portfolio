package me.nootnoot.jackpotmcteams.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import me.nootnoot.jackpotmcteams.JackpotMCTeams;
import me.nootnoot.jackpotmcteams.enums.TeamRoles;
import me.nootnoot.jackpotmcteams.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
	@Getter
	private final String teamID;
	@Getter@Setter
	private String name;
	@Getter@Setter
	private ArrayList<TeamPlayer> players;
	@Getter@Setter(AccessLevel.PRIVATE)
	private long kills;
	@Getter@Setter
	private Location teamHome;

	@Getter @Setter private UUID ownerUUID;

	private transient OfflinePlayer owner;
	@Getter@Setter
	private String description = "&7Default Description";
	@Getter@Setter
	private boolean open = false;
	@Getter@Setter
	private int rank;
	@Getter@Setter
	private double balance = 0;

	@Getter
	private final ArrayList<UUID> pendingInvites;

	public Team(String id, String name, UUID owner, int rank){
		this.teamID = id;
		this.name = name;
		this.players = new ArrayList<>();
		this.ownerUUID = owner;
		this.owner = Bukkit.getOfflinePlayer(owner);
		this.players.add(new TeamPlayer(owner, TeamRoles.OWNER, Bukkit.getOfflinePlayer(owner).getName()));
		this.kills = 0;
		this.rank = rank;

		pendingInvites = new ArrayList<>();
	}

	public OfflinePlayer getOfflineOwner(){
		if(ownerUUID != null){
			this.owner = Bukkit.getOfflinePlayer(ownerUUID);
		}
		return owner;
	}

	public Player getOwner(){
		if(ownerUUID != null){
			this.owner = Bukkit.getOfflinePlayer(ownerUUID);
		}
		if(owner != null){
			return owner.getPlayer();
		}
		return null;
	}

	public TeamPlayer getPlayer(Player p){
		for(TeamPlayer player : players){
			if(player.getPlayerUUID() != null) {
				if (player.getPlayerUUID().equals(p.getUniqueId())) {
					return player;
				}
			}
		}
		return null;
	}

	public void promote(TeamPlayer p){
		if(p.getRole() == TeamRoles.MEMBER){
			p.setRole(TeamRoles.OFFICER);
		}
		for(TeamPlayer player : players){
			if(player.getPlayer() != null){
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + " &a&n" + p.getPlayer().getName() + "&a has been promoted to &NOFFICER!"));
			}
		}
	}

	public void demote(TeamPlayer p){
		if(p.getRole() == TeamRoles.OFFICER){
			p.setRole(TeamRoles.MEMBER);
		}
		for(TeamPlayer player : players){
			if(player.getPlayer() != null){
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + " &c&n" + p.getPlayer().getName() + "&c has been demoted to &NMEMBER!"));
			}
		}
	}

	public void makeLeader(TeamPlayer p, Player owner){
		this.owner = Bukkit.getOfflinePlayer(owner.getUniqueId());
		TeamPlayer teamOwner = getPlayer(owner.getPlayer());
		teamOwner.setRole(TeamRoles.OFFICER);

		p.setRole(TeamRoles.OWNER);
		setOwnerUUID(p.getPlayer().getUniqueId());
		this.owner = Bukkit.getOfflinePlayer(p.getPlayer().getUniqueId());

		p.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a You have been promoted to Owner!"));
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a &l" + p.getPlayer().getName() + "&a has been promoted to Owner!"));
		}
	}

	public void acceptInvite(TeamPlayer p){
		players.add(p);
	}

	public TeamRoles getRole(Player p){
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayerUUID().equals(p.getUniqueId())){
				return player.getRole();
			}
		}
		return null;
	}

	public TeamRoles getRole(String name){
		for(TeamPlayer player : getPlayers()) {
			if (player.getOfflinePlayer() != null) {
				if (player.getOfflinePlayer().getName().equalsIgnoreCase(name)) {
					return player.getRole();
				}
			}
		}
		return null;
	}

	public void JoinTeam(Player p){
		getPlayers().add(new TeamPlayer(p.getUniqueId(), TeamRoles.MEMBER, p.getName()));
		getPendingInvites().remove(p.getUniqueId());
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully joined &l" + getName()));
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null){
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a &l" + p.getName() + "&a has joined the team!"));
			}
		}
	}

	public void LeaveTeam(Player p){
		getPlayers().removeIf(player -> player.getPlayerUUID().equals(p.getUniqueId()));
		p.sendMessage(Utils.c(Utils.getPREFIX() + "&a You have successfully left &l" + getName()));
		JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(p);
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null){
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&c &l" + p.getName() + "&c has left the team!"));
			}

		}
	}

	public boolean KickPlayer(String name){
		if(getPlayers().removeIf(player -> player.getOfflinePlayer().getName().equalsIgnoreCase(name))){
			Player p = Bukkit.getPlayer(name);
			if(p != null){
				p.sendMessage(Utils.c(Utils.getPREFIX() + "&c You have been kicked from &l" + getName()));
			}
			JackpotMCTeams.getInstance().getTeamManager().getPlayersInTeamChat().remove(p);
			for(TeamPlayer player : getPlayers()){
				if(player.getPlayer() != null)
					player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&c &l" + name + "&c has been kicked from the team!"));
			}
			return true;
		}
		return false;
	}

	public ArrayList<String> GetOnlinePlayers() {
		ArrayList<String> names = new ArrayList<>();
		for (TeamPlayer player : getPlayers()) {
			if (player.getPlayer() != null) {
				if (player.getOfflinePlayer().isOnline()) {
					names.add(player.getPlayer().getName());
				}
			}
		}
		return names;
	}


	public String GetOfficers(){
		StringBuilder names = new StringBuilder();
		for(TeamPlayer player : getPlayers()){
			if(player.getRole() == TeamRoles.OFFICER){
				if(player.getOfflinePlayer().isOnline()) {
					names.append(ChatColor.GREEN).append(player.getOfflinePlayer().getName()).append(ChatColor.GRAY).append("&f, ");
				}else{
					names.append(ChatColor.RED).append(player.getOfflinePlayer().getName()).append(ChatColor.GRAY).append("&f, ");
				}
			}
		}
		return names.toString();
	}

	public String GetMembers(){
		StringBuilder names = new StringBuilder();
		for(TeamPlayer player : getPlayers()){
			if(player.getRole() == TeamRoles.MEMBER){
				if(player.getOfflinePlayer().isOnline()) {
					names.append(ChatColor.GREEN).append(player.getOfflinePlayer().getName()).append(ChatColor.GRAY).append("&f, ");
				}else{
					names.append(ChatColor.RED).append(player.getOfflinePlayer().getName()).append(ChatColor.GRAY).append("&f, ");
				}
			}
		}
		return names.toString();
	}

	public String getOwnerString(){
		if(getOfflineOwner().isOnline()){
			return ChatColor.GREEN + getOfflineOwner().getName() + ChatColor.GRAY;
		}else{
			return ChatColor.RED + getOfflineOwner().getName() + ChatColor.GRAY;
		}
	}

	public void UpdateDescription(String description, Player p){
		setDescription(description);
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a&l " + p.getName() + " &ahas updated your teams description to: &l" + description));
		}
	}

	public void AddKills(int amount){
		this.kills = (getKills() + amount);
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a ❖" + amount + " has been added to your teams kills"));
		}
	}

	public void RemoveKills(int amount){
		this.kills = (getKills() - amount);
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a ❖" + amount + " has been removed from your teams kills"));
		}
	}

	public void SetKills(int amount){
		this.kills = (amount);
		for(TeamPlayer player : getPlayers()){
			if(player.getPlayer() != null)
				player.getPlayer().sendMessage(Utils.c(Utils.getPREFIX() + "&a Your team points has been set to " + getKills()));
		}
	}

	public ArrayList<String> GetWho(){
		ArrayList<String> who = new ArrayList<>();
		who.add("                     ");
		who.add("");
		who.add("&7⛨ &6" + getName());
		who.add("&c❖ &fTeam Ranking: &6" + getRank());
		who.add("");
		who.add("&fOwner: &7" + getOwnerString());
		who.add("&fOfficers: &7" + GetOfficers());
		who.add("&fMembers: &7" + GetMembers());
		who.add("");
		who.add("&fDescription: &f" + getDescription());
		who.add("                      ");
		return who;
	}
}
