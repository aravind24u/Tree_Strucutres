package payments;




public class GenerateData {
	
	public static void main(String[] args) throws Exception {
		//
		System.out.println("----------------------------");		
		//ZInvoices.checkPaymentData();
		//ZInvoices.outData();		

		//ZInvoices.getTotal(false);
		//ZInvoices.cleanup();
		//System.out.println("----------------------------");		
		//ZInvoices.getTotal(Boolean.FALSE);
		
		//ZInvoices.getNewCustomers();
		
		//Call the following function to generate Payments in Zoho Invoice.
		//10682.34
		ZInvoices.generatePayments();
		
		//Call the following function to get the new customers customer_id from Zoho Invoice.
		//ZInvoices.generateInvoiceData();
		
		//Call the following function to generate Invoices in Zoho Invoice.
		//ZInvoices.generateInvoices();
		
		
		//ZInvoices.checkInvoices();
		//ZInvoices.updateInvoices();
		//ZInvoices.syncData();
		
		
		//ZInvoices.checkEncounterCharge();
		//ZInvoices.checkInvoiceTotal();
		
		
		//ZInvoices.generateCreditNotes();
		
		/*Customers cus = new Customers();
		System.out.println(cus.getSize());		
		cus.addTestCustomer("257000007937085");
		cus.writeCustomersToFile();
		System.out.println(cus.getSize());*/
		//cus.constructProfiles();
		/*Customers cus = new Customers();
		System.out.println("Customers: " + cus.getSize());*/
		//cus.writeCustomersToFile();
		//System.out.println(ZInvoices.getInvoiceData("MM-INV-1478001-052015-01").toString());;
		
		/*Customers cus = new Customers();
		cus.printTestCustomers();
		cus.printCustomers();*/
		System.out.println("----------------------------");

	}
	
}