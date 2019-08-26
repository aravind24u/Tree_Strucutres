<!--Monthly Payment Details of a practice-->
select FOR_THE_YEAR_OF,FOR_THE_MONTH_OF,MONTHLY_CHARGE,PAID_STATUS,Name,COUNT,COST 
	from MonthlyPaymentDetails MPD left join MonthlyModulePricingHistory MPH on MPD.MONTHLY_PYMNT_ID = MPH.MONTHLY_PYMNT_ID 
	left join db15odb.PlanAttributes PA on PA.attribute_id = MPH.attribute_id 
	where service_customer_id = 257000013496047;

<!--Practices In Specific Plan-->
select *,from_unixtime(TIME_OF_CREATION/1000) 
	from db15odb.RecurringServicePlan RSP left join db15odb.ServiceCustomerPlan SCP on RSP.planId = SCP.planId 
	left join db1odb.PracticesList PL on SCP.service_customer_id = PL.practice_id 
	where from_unixtime(TIME_OF_CREATION/1000) > '2019-03-01' and planname like '%FLEXI%' limit 100;

<!--All Plan Configs -->
select PLANNAME , pa.name, rs.*  
	from db15odb.RecurringServicePlan rsp left join  db15odb.RecurringServicePlanConfigs rs on rsp.planid = rs.planid
	left join db15odb.PlanAttributes pa on rs.attribute_id = pa.attribute_id
	where identifier = 'Encounter' limit 100;

	
<!--New Practices Crossing Limits--> 
select PL.PRACTICE_ID, PL.PRACTICE_NAME, IS_TEST_PRACTICE ,PLANNAME, CONSULTATION_FOR_MONTH_1, CONSULTATION_FOR_MONTH_2, CONSULTATION_FOR_MONTH_3,
	PRESCRIBERS_COUNT,OTHER_MEMBERS_COUNT,ACTIVE_PATIENTS, CONTACT_PERSON,EMAILID,from_unixtime(TIME_OF_CREATION/1000)
	from db1odb.PracticesList PL left join db1odb.AccountUsage AU on AU.practice_id = PL.practice_id 
	left join db15odb.ServiceCustomerPlan SCP on SCP.SERVICE_CUSTOMER_ID = PL.PRACTICE_ID 
	left join db15odb.RecurringServicePlan RCP on SCP.planid = RCP.planid
	where from_unixtime(TIME_OF_CREATION/1000) > '2019-03-01' 
	and (CONSULTATION_FOR_MONTH_1 > 50 or CONSULTATION_FOR_MONTH_2 >50 or CONSULTATION_FOR_MONTH_3 > 50 or  OTHER_MEMBERS_COUNT > 5 or ACTIVE_PATIENTS > 100 or PRESCRIBERS_COUNT > 1)
	and RCP.planid != 3211000012543073 limit 100;

<!--PayPal and DB check-->
select PROFILEID,PRACTICE_ID,CUSTOMERNAME,MONTHLY_CHARGE,PAID_STATUS,FOR_THE_MONTH_OF,FOR_THE_YEAR_OF 
	from db15odb.RecurringCustomerProfile left join db15odb.ServiceCustomerPlan on db15odb.ServiceCustomerPlan.PLAN_CUSTOMER_ID=db15odb.RecurringCustomerProfile.PLAN_CUSTOMER_ID 
	left join db1odb.PracticesList on db15odb.ServiceCustomerPlan.SERVICE_CUSTOMER_ID=db1odb.PracticesList.PRACTICE_ID 
	left join db15odb.MonthlyPaymentDetails on db15odb.ServiceCustomerPlan.SERVICE_CUSTOMER_ID=db15odb.MonthlyPaymentDetails.SERVICE_CUSTOMER_ID 
	where PROFILEID IN ("RP0000000391") and FOR_THE_YEAR_OF="2019";
	
<!--Appointments Booked for Other month -->
select YEAR(APPOINTMENT_DATE) , MONTH(APPOINTMENT_DATE),COUNT(CONSULTATION_ID) 
	from ConsultationHistory INNER JOIN AppointmentHistory ON ConsultationHistory.CONSULTATION_ID= AppointmentHistory.APPOINTMENT_ID 
	where CONSULTATION_ID like "3631340%" AND IS_PAPER_RECORD=false AND IS_RENEWAL=false AND (TIME_OF_CREATION/1000) > UNIX_TIMESTAMP("2019-05-01 00:00:01")
	AND TIME_OF_CREATION<UNIX_TIMESTAMP("2019-06-01 00:00:01")*1000 
	GROUP BY YEAR(APPOINTMENT_DATE), MONTH(APPOINTMENT_DATE);
	
<!--Reseller Report -->
select CONCAT(MedicalMineResellers.RESELLER_ID, ',', REPLACE(RESELLER_NAME, ',', '.'), ',', PRACTICE_ID, ',', REPLACE(PRACTICE_NAME, ',', '.'), ',', FROM_UNIXTIME(TIME_OF_CREATION/1000)) 
	from db1odb.PracticesList INNER JOIN db1odb.MedicalMineResellers ON MedicalMineResellers.RESELLER_ID=PracticesList.RESELLER_ID
	
	
	