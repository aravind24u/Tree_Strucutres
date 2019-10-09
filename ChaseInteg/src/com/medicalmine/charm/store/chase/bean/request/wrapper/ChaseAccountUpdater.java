package com.medicalmine.charm.store.chase.bean.request.wrapper;

import com.medicalmine.charm.store.chase.bean.request.AccountUpdaterType;
import com.medicalmine.charm.store.chase.bean.request.ValidIndustryTypes;

public class ChaseAccountUpdater extends AccountUpdaterType implements ChaseMerchantCredentials {

	/**
	 * We will not be implementing any of these setters as Account update does not
	 * need these values, Since we had to add them in the Interface we are creating
	 * dummy setters
	 */

	@Override
	public void setBIN(String bin) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMerchantID(String merchantId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTerminalID(String terminalId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setIndustryType(ValidIndustryTypes type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrencyCode(String currencyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCurrencyExponent(String currencyExponent) {
		// TODO Auto-generated method stub

	}

}
