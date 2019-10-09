package com.medicalmine.charm.store.chase.bean.request.wrapper;

import com.medicalmine.charm.store.chase.bean.request.ValidIndustryTypes;

public interface ChaseMerchantCredentials {

	public void setOrbitalConnectionUsername(String connectionUserName);// ("TESTZOHO1074");//CharmPropertiesUtil.getValue("CHASE_CONNECTION_USERNAME");

	public void setOrbitalConnectionPassword(String connectionPassword);// ("4Vm74Xv9vFlP");//CharmPropertiesUtil.getValue("CHASE_CONNECTION_PASSWORD");

	public void setBIN(String bin);// ("000001");//CharmPropertiesUtil.getValue("CHASE_BIN");

	public void setMerchantID(String merchantId);// ("371074");//CharmPropertiesUtil.getValue("CHASE_MERCHANT_ID");

	public void setTerminalID(String terminalId);// ("001");//CharmPropertiesUtil.getValue("CHASE_TERMINAL_ID");

	public void setIndustryType(ValidIndustryTypes type);// (ValidIndustryTypes.RC);

	public void setCurrencyCode(String currencyCode);// ("840");

	public void setCurrencyExponent(String currencyExponent);// ("2");

}
