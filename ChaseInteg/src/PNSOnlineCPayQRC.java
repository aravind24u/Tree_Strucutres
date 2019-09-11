import com.jpmc.cms.sdk.common.Utils;
import com.jpmc.cms.sdk.framework.Dispatcher;
import com.jpmc.cms.sdk.framework.PNS;
import com.jpmc.cms.sdk.framework.PNSResp;
import com.jpmc.cms.sdk.framework.interfaces.DispatcherIF;
import com.jpmc.cms.sdk.framework.interfaces.RequestIF;
import com.jpmc.cms.sdk.framework.interfaces.ResponseIF;

public class PNSOnlineCPayQRC
{
	// In practice, you'll probably want to pull these values from a per
	// environment property file
	String merchantId;
	String terminalId;

	public PNSOnlineCPayQRC( String merchantId, String terminalId )
	{
		this.merchantId = merchantId;
		this.terminalId = terminalId;
	}

	private void createAndSendRequest(
			GetCheckoutDataPOS2ResponseElement getCheckoutDataPOS2ResponseElement )
			throws Exception
	{
		/*
		 * Sample data for a PNS ISO 1200 request for a Chase Pay transaction
		 * 
		 * Given the below POJO exists: - getCheckoutDataPOS2ResponseElement -
		 * representing a response from CSo Orbital's GetCheckoutDataPOS2
		 * interface - applicationID - tokenRequestorID - track2Equivalent -
		 * paymentInfo2
		 * 
		 * NOTE: The content below is for illustrative purposes only. For the
		 * sake of brevity/clarity, we have left out null checks, exception
		 * handling and other defensive practices as well any attempt at
		 * optimization.
		 */

		// Create the dispatcher
		// Dispatcher is thread safe and can be shared across requests
		DispatcherIF dispatcher = new Dispatcher();

		// Create a new transaction request object
		RequestIF request = dispatcher.createRequest( "PNSNewTransaction", "PNSConnect" );

		// Setting the "Processing System" allows the SDK to make assumptions
		// and
		// set default values for certain protocol specific fields
		request.getConfig().setField( "HostProcessingSystem", "TCS" );

		// Configure headers 
		request.setField( PNS.MessageHeader.RoutingInformation, "L.PTIISO" );
		request.setField( PNS.MessageHeader.MultitranIndicator, "N" );
		request.setField( PNS.MessageHeader.MultiBatchFlag, "N" );
		request.setField( PNS.MessageHeader.MessageType, "1200" );

		/*
		 * Set Bit2 - primary account number Take the portion of
		 * track2Equivalent before the separator 'D' ex: track2Equivalent:
		 * 1234567890123456D1234567 dpan: 1234567890123456
		 */
		String[] track2Parts = getCheckoutDataPOS2ResponseElement
				.getTrack2Equivalent().split( "D" );
		String dpan = track2Parts[0];
		request.setField( PNS.Bit2.PrimaryAccountNumber, dpan );

		/*
		 * Set Bit3 - processing code ex: TransactionType: 00 - Goods / Services
		 * AccountTypeFrom: 91 - TCS query batch upload request / HCS batch
		 * inquiry AccountTypeTo: 00 - Goods / Services
		 */
		request.setField( PNS.Bit3.TransactionType, "00" );
		request.setField( PNS.Bit3.AccountTypeFrom, "91" );
		request.setField( PNS.Bit3.AccountTypeTo, "00" );

		/*
		 * The transaction amount should be reported in cents ex: $5.00 - > 500
		 */
		request.setField( PNS.Bit4.TransactionAmount, "500" );

		/*
		 * The local time in HHmmss format ex: 12:21:18p -> 122118
		 */
		request.setField( PNS.Bit12.TimeLocalTrans, "122118" );

		//
		/*
		 * Transaction date in MMddyyyy format ex: 04/25/2017 -> 04252017
		 */
		request.setField( PNS.Bit13.DateLocalTrans, "04252017" );

		/*
		 * Indicates how the account number was read ex: 030 - Barcode read
		 */
		request.setField( PNS.Bit22.POSEntryMode, "030" );

		/*
		 * Describes the basic POS environment that initiated the transaction
		 */
		request.setField( PNS.Bit25.POSConditionCode, "00" );

		/*
		 * Set Bit35 - track 2 data Replace the separator 'D' in
		 * track2Equivalent with '=' ex: track2Equivalent:
		 * 1234567890123456D1234567 track2Data: 1234567890123456=1234567
		 */
		request.setField( PNS.Bit35.Track2Data,
				getCheckoutDataPOS2ResponseElement.getTrack2Equivalent().replace( 'D', '=' ) );

		/*
		 * Generate a unique id for this transaction
		 */
		request.setField( PNS.Bit37.RetrievalReferenceNumber,
				createRetrievalReferenceNumber() );

		/*
		 * Bit 41 - Card Acquirer Terminal Id ex: 001 - indicates no batch
		 * management is required Otherwise this should reflect a unique
		 * terminal id 
		 */
		request.setField( PNS.Bit41.CardAcquirerTerminalId, terminalId );

		/*
		 * Merchant id as provided by Commerce Solutions
		 */
		request.setField( PNS.Bit42.CardAcquirerId, this.merchantId );

		/*
		 * Set Bit48.A7 EMVContactlessAID EMVContactlessAID is obtained from
		 * GetCheckoutDataPOS2 response's ApplicationAID field
		 */
		String emvContactlessAID = getCheckoutDataPOS2ResponseElement
				.getApplicationID();
		request.setField( PNS.Bit48.A7.EMVContactlessAID, emvContactlessAID );

		/*
		 * Indicates how PAN was initially provided; might be static for Chase
		 * Pay transactions? ex: 58
		 */
		request.setField( PNS.Bit48.D1.DataEntrySource, "58" );

		/*
		 * Merchant configured Indicates support for partial transactions,
		 * probably merchant terminal specific
		 */
		request.setField( PNS.Bit48.P8.EnhancedAuthorizationRequestIndicator, "11" );

		/*
		 * Set Bit48 - Token Requestor Token requestor ID is obtained from
		 * GetCheckoutDataPOS2 response's tokenRequestorId field
		 */
		String tokenRequestorId = getCheckoutDataPOS2ResponseElement.getTokenRequestorID();
		request.setField( PNS.Bit48.TK.TokenRequestorID, tokenRequestorId );
		request.setField( PNS.Bit48.TK.TokenAssuranceLevel, "  " );

		/*
		 * Set Bit55 - EMV Data Bit 55 is created by combining
		 * GetCheckoutDataPOS2 response's paymentInfo2 field with
		 * merchant/transaction specific data
		 * 
		 * PaymentInfo2 as returned from GetCheckoutDataPOS2 is encoded as
		 * Base64. You will need to convert this data to hexadecimal.
		 */
		byte[] paymentInfo2Bytes = org.apache.commons.codec.binary.Base64
				.decodeBase64(
						getCheckoutDataPOS2ResponseElement.getPaymentInfo2() );
		String emvData = org.apache.commons.codec.binary.Hex
				.encodeHexString( paymentInfo2Bytes );

		/*
		 * The decoded PaymentInfo2 is a series of Tag Length Values (TLVs) that
		 * will be sent in Bit55.
		 * 
		 * The following tags are mandatory and not present in the decoded
		 * PaymentInfo2. You will need to add them. - 9F02 Amount Authorized -
		 * 9C Transaction Type - 9F33 Terminal Capabilities - 9F1A Terminal
		 * Country Code - 9A Transaction Date - 95 Terminal Verification Results
		 * - 5F2A Transaction Currency Code
		 * 
		 * For each additional tag, you will need to concatenate the tag, the
		 * length of the value in bytes (represented as hexadecimal) and the
		 * actual value. Both the length and value must independently be in
		 * whole bytes (an even number of hexadecimal characters) and should
		 * meet the length requirements as detailed in the EMV specification
		 * guide. Left pad with zeroes as necessary to meet these requirements.
		 * 
		 * The spectrum SDK provides the following convenience method to help
		 * with this action: com.paymentech.sdk.common.Utils.leftPad(..)
		 * 
		 * You can find further information about the format of Bit55 in section
		 * 10.30 of the PNS ISO specification. The specific length/format
		 * requirements for each tag can be found in the EMV specification.
		 * 
		 * See the formatTlv method to get an idea of what's involved.
		 */
		emvData += formatTlv( "9F02", "500", 12 );
		emvData += formatTlv( "9F33", "E028C8", 6 );
		emvData += formatTlv( "9F1A", "840", 4 );
		emvData += formatTlv( "95", "8000008000", 10 );
		emvData += formatTlv( "5F2A", "840", 4 );
		emvData += formatTlv( "9A", "170425", 6 );
		emvData += formatTlv( "9C", "00", 2 );

		// Finally, find the entire length of the EMV data in chars (NOT bytes),
		// in decimal (Base10) format
		// If necessary, left pad with zeroes until 3 characters
		String emvDataLength = Integer.toString( emvData.length() );
		emvDataLength = Utils.padLeft( emvDataLength, '0', 3 );

		// Left pad this length with zeroes, if necessary, so that it is 3
		// characters and prepend to the emvData
		emvData = emvDataLength + emvData;

		// Tandem expects any alphabetic characters to be upper case, so lets
		// convert the entire string here
		emvData = emvData.toUpperCase();

		// Finally insert the resulting value into Bit55 of the request
		request.setField( PNS.Bit55.ChipCardData, emvData );

		/*
		 * Was card acceptor at point of sale (10.31) Ex: 00 - attended
		 */
		request.setField( PNS.Bit60.A1.AttendedTerminalData, "00" );

		/*
		 * Indicates what type, if any, of CAT this is (vending machines /
		 * self-service terminals) ex: 00 - Not a cat device
		 */
		request.setField( PNS.Bit60.A1.CardholderActivatedTerminalInformation, "00" );

		/*
		 * Was the card holder present ex: 00 - card holder present
		 */
		request.setField( PNS.Bit60.A1.CardholderAttendance, "00" );
		// Merchant configured: was actual card present
		/*
		 * Was the card present ex: 0 - card was present
		 */
		request.setField( PNS.Bit60.A1.CardPresentIndicator, "0" );

		/*
		 * Primary method used to acquire card holder information ex: 04 -
		 * optical character recognition
		 */
		request.setField( PNS.Bit60.A1.TerminalEntryCapability, "04" );

		/*
		 * Indicates the location of terminal ex: 00 - on premises of card
		 * acceptor
		 */
		request.setField( PNS.Bit60.A1.TerminalLocation, "00" );

		/*
		 * HardwareVendorIdentifier and SoftwareIdentifier will be provided to
		 * you by a Commerce Solutions representative at time of certification
		 * HardwareSerialNumber is specific to your device
		 */
		request.setField( PNS.Bit62.P1.HardwareVendorIdentifier, "0000" );
		request.setField( PNS.Bit62.P1.SoftwareIdentifier, "0000" );
		request.setField( PNS.Bit62.P1.HardwareSerialNumber, "                    " );

		/*
		 * Bit 62.P2 indicates which system is used by the payment application
		 * TAS/TCS/HCS and auth/settle/both
		 */
		request.setField( PNS.Bit62.P2.Bit2.P2HostProcessingPlatform, "0C" );
		request.setField( PNS.Bit62.P2.Bit3.P3MessageFormatSupport1, "80" );
		request.setField( PNS.Bit62.P2.Bit4.P4EMVSupport, "E0" );
		request.setField( PNS.Bit62.P2.Bit7.P7CommunicationInformation1, "02" );
		request.setField( PNS.Bit62.P2.Bit9.P9IndustryInformation1, "02" );
		request.setField( PNS.Bit62.P2.Bit11.P11ClassComplianceCertification, "10" );

		/*
		 * Bit63 Retail industry data Invoice Number: Reference number to be
		 * included on cardholder's statement Tran information: Free form
		 * information related to transaction
		 */
		request.setField( PNS.Bit63.R2.InvoiceNumber, "123456" );
		request.setField( PNS.Bit63.R2.TranInformation, "Icanput20charshere" );

		// dispatch the request
		ResponseIF response = dispatcher.processRequest( request );

		String transactionStatus = response.getField( PNSResp.Bit39.ResponseCode );
		boolean isApproved = transactionStatus.equals( "00" );
		if ( isApproved )
		{
			// These values will be required for the GetConfirmation request
			// later sent back to Orbital
			String authorizationIdResponse = response.getField( PNSResp.Bit38.AuthorizationIDResponse );
			String visaTransactionId = response.getField( PNSResp.Bit48.V1.TransactionID );
		}
	}

	/**
	 * Creates a random retrieval reference number to send with your request
	 * Each transaction should have a unique retrieval reference number;
	 * however, different requests in the same transaction may share the same
	 * number.
	 *
	 * @return a random 12 digit numeric string
	 */
	private static String createRetrievalReferenceNumber()
	{
		long randomLong = (long) (Math.random() * 999999999999L);
		return Utils.padLeft( Long.toString( randomLong ), '0', 12 );
	}

	/**
	 * Create a complete TLV from the provided tag and value
	 *
	 * For all tags, you can find information on the appropriate format of the
	 * value and the expected length in the emv implementation guide
	 *
	 * @param tag
	 *            The tag for this tlv as a hexadecimal string. When generating
	 *            the emv data this should always be 2 or 4 hexadecimal
	 *            characters.
	 * @param value
	 *            The value of this tag before padding to an appropriate length
	 * @param expectedLength
	 *            Expected length of this tag's value in characters This should
	 *            always be an even number and the correct value can be found in
	 *            the EMV guide.
	 * @return a complete TLV
	 */
	private static String formatTlv( String tag, String value,
			int expectedLength )
	{
		/*
		 * Left pad the provided value with zeroes to reach the expected length
		 * ex: "500" -> "000000000500"
		 */
		value = Utils.padLeft( value, '0', expectedLength );

		/*
		 * Find the length of the value in bytes (hexadecimal pairs) and convert
		 * to hexadecimal ex: given value 000000000500
		 */
		String valueLength = Integer.toHexString( value.length() / 2 );

		// ex: "9F02" + "06" + "000000000500"
		return tag + value + valueLength;
	}

	public static void main( String[] args ) throws Exception
	{
		PNSOnlineCPayQRC samplePNS1200Request = new PNSOnlineCPayQRC( "45645", "001" );
		GetCheckoutDataPOS2ResponseElement getCheckoutDataPOS2ResponseElement = new GetCheckoutDataPOS2ResponseElement();
		samplePNS1200Request.createAndSendRequest( getCheckoutDataPOS2ResponseElement );
	}

	private static class GetCheckoutDataPOS2ResponseElement
	{
		String paymentInfo2;
		String tokenRequestorID;
		String applicationID;
		String track2Equivalent;
		// Plus other fields

		private GetCheckoutDataPOS2ResponseElement()
		{
			// Static content for testing
			this.applicationID = "A0000000031010";
			this.paymentInfo2 = "hAegAAAAAxAQn24EMDAwMJ98BQIrKgYCggIAAJ8QEhFEAXAAAAAAAAAAAAAHJ3YAAJ8mCB4qz8GYLTIynzYCAEefNwRZAMGC";
			this.tokenRequestorID = "40010074892";
			this.track2Equivalent = "4071231000175932D2208201";
		}

		public String getPaymentInfo2()
		{
			return paymentInfo2;
		}

		public String getTokenRequestorID()
		{
			return tokenRequestorID;
		}

		public String getApplicationID()
		{
			return applicationID;
		}

		public String getTrack2Equivalent()
		{
			return track2Equivalent;
		}
	}
}