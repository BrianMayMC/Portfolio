package me.nootnoot.framework.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyUtils {


	public static String prettyMoney(Double balance, boolean removeSign, boolean longName) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);

		int i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign);

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Thousand" : "K");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Million" : "M");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Billion" : "B");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Trillion" : "T");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Quadrillion" : "Q");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Quintillion" : "Qt");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Sextillion" : "SX");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Septillion" : "SP");

		balance = Math.floor(balance / 100) / 10.0;

		i = balance.intValue() / 1000;

		if (i == 0)
			return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Octillion" : "O");

		balance = Math.floor(balance / 100) / 10.0;
		return trimLastDigit(formatter.format(balance), removeSign) + (longName ? " Nonillion" : "N");
	}

	public static String trimLastDigit(String s, boolean removeSign) {
		String[] split = s.split("\\.", 2);
		String decimal = split[1].substring(0, Math.min(split[1].length(), 1));
		if (decimal.length() == 0) decimal += "0";

		return removeSign(split[0] + "." + decimal, removeSign);
	}

	public static String removeSign(String s, boolean value) {
		if (value)
			s = s.replaceAll("\\$", "");
		return s;
	}
}
