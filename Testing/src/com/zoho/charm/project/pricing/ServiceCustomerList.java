package com.zoho.charm.project.pricing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.zoho.charm.project.utils.CommonUtils;
import com.zoho.charm.project.utils.encoder.EncodingConstants;

public class ServiceCustomerList {
	public static void main(String[] args) throws Exception {
		List<String> practicesList = CommonUtils.loadFile(CommonUtils.INVOICE_HOME_DIR.concat("PracticesList.txt"));
		List<String> serviceCustomerIds = CommonUtils.loadFile(CommonUtils.INVOICE_HOME_DIR.concat("ServiceCustomerPlan.txt"));
		practicesList.forEach(practiceId -> {
			if (!serviceCustomerIds.contains(practiceId)) {
				System.out.println(practiceId);
			}
		});
	}
}
