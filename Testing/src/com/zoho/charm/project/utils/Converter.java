package com.zoho.charm.project.utils;

public class Converter {
	public static void main(String[] args) {
		System.out.println(fromBase10(new Long("1234123412341234123")));
		System.out.println(toBase10("1TAIUkOaQ9L"));
	}

	public static final char[] BASE62CHARACTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C',
			'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };

	public static final String BASE62STRING = new String(BASE62CHARACTERS);

	public static String base62converter(Long number) {

		StringBuilder answer = new StringBuilder();

		while (number != 0) {

			answer.append(BASE62CHARACTERS[Integer.parseInt(((Long) (number % BASE62STRING.length())).toString())]);

			number /= BASE62STRING.length();

		}

		return answer.reverse().toString();
	}

	public static Long base10converter(String number) {
		Long sum = new Long(0L);

		for (Integer itr = 0; itr < number.length(); itr++) {
			Character character = number.charAt(itr);
			Integer index = BASE62STRING.indexOf(character);
			Double num = (index * Math.pow(BASE62STRING.length(), number.length() - itr - 1));
			sum += num.longValue();
		}
		return sum;
	}

	public static String fromBase10(Long number) {
		StringBuilder result = new StringBuilder("");
		if (number == 0) {
			return "0";
		}
		while (number > 0) {
			number = fromBase10(number, result);
		}
		return result.reverse().toString();
	}

	private static Long fromBase10(Long number, final StringBuilder result) {
		int rem = Integer.parseInt(((Long) (number % BASE62STRING.length())).toString());
		result.append(BASE62STRING.charAt(rem));
		return number / BASE62STRING.length();
	}

	public static Long toBase10(String number) {
		return toBase10(new StringBuilder(number).reverse().toString().toCharArray());
	}

	private static Long toBase10(char[] numbers) {
		Long result = new Long("0");
		for (int i = numbers.length - 1; i >= 0; i--) {
			result += toBase10(BASE62STRING.indexOf(numbers[i]), i);
		}
		return result;
	}

	private static Long toBase10(int number, int pow) {
		Double result = Math.pow(BASE62STRING.length(), pow);
		return number * result.longValue();
	}

}
