package me.nootnoot.albioncore.BLL.ecosystem.interfaces;


public interface Manager<T> {
	T GetLevel(int level);

	void AddLevel(T level);

	void RemoveLevel(T level);
}
