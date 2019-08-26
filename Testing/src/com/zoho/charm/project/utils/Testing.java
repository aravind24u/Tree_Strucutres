package com.zoho.charm.project.utils;

import java.time.LocalDate;

public class Testing {
	public static void main(String[] args) throws Exception {
		LocalDate date = LocalDate.now();
		LocalDate previousMonth = date.minusMonths(1);
		System.out.println(previousMonth.getMonth().toString());
		
	}
}
