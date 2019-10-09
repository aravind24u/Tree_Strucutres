package com.medicalmine.charm.store.chase.bean.request.wrapper;

import com.medicalmine.charm.store.chase.bean.request.ReversalType;
import com.medicalmine.charm.store.chase.bean.request.ValidIndustryTypes;

public class ChaseOrderReversal extends ReversalType implements ChaseMerchantCredentials {

	/**
	 * We will not be implementing any of these setters as Account update does not
	 * need these values, Since we had to add them in the Interface we are creating
	 * dummy setters
	 */
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
