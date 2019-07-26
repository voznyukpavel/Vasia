package com.lux.study.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextChecker {
	
	private TextChecker() {

	}

	private static final String NAME_REGULAR_EXPRESSION = "[A-Za-z ]+";
	private static final String GROUP_REGULAR_EXPRESSION = "[A-Za-z0-9_.]+";

	public static boolean checker(String name, String group) {
		if (checkTextFild(name, NAME_REGULAR_EXPRESSION) && checkTextFild(group, GROUP_REGULAR_EXPRESSION)) {
			return true;
		}
		return false;
	}

	private static boolean checkTextFild(String value, String expresion) {
		Pattern p = Pattern.compile(expresion);
		Matcher m = p.matcher(value);
		if (m.find() && m.group().equals(value))
			return true;
		else
			return false;
	}

}
