
import com.jpmc.cms.sdk.framework.Dispatcher;
import com.jpmc.cms.sdk.framework.ORBResp;
import com.jpmc.cms.sdk.framework.exceptions.DispatcherException;
import com.jpmc.cms.sdk.framework.exceptions.RequestException;
import com.jpmc.cms.sdk.framework.interfaces.DispatcherIF;
import com.jpmc.cms.sdk.framework.interfaces.RequestIF;
import com.jpmc.cms.sdk.framework.interfaces.ResponseIF;

/**
 * Please note that this code is for documentation purposes only and is
 * specifically written in a simplistic way, in order to better represent and
 * convey specific concepts in the use of the SDK. Best practices in
 * error handling and control flow for java coding, have often been ignored in
 * favor of providing code that clearly represents a specific concept. Due to
 * the best practices compromises we have made in our sample code, we do not
 * recommend using this code in a production environment without rigorous
 * improvements to error handling and overall architecture.
 */
public class ORBOnlineAuth
{
	public static void main( String[] args )
	{
		DispatcherIF dispatcher = null;

		try
		{
			// Create a Dispatcher - This is your entry point to  the  SDK.
			// The Dispatcher loads its properties from configuration files in the 
			// MSDK_HOME/config directory. The Dispatcher looks for the "config" 
			// directory under the directory specified by the MSDK_HOME environment 
			// variable. If no MSDK_HOME environment variable 
			// exists, pass the absolute path to msdk home to the
			// Dispatcher constructor or place the config, converter & templates  
			// directories  under the classpath or place all the required 
			// directories in a jar/war file in classpath
			// eg: dispatcher = new Dispatcher( "/opt/JPMC_MSDK_X.X.X");
			dispatcher = new Dispatcher();
		}
		catch ( DispatcherException e )
		{
			// Catch any dispatcher initialization exception
			System.out.println( "************************Exception while creating Dispatcher************************" );
			System.out.println( e.toString() );
			System.out.println( "Error Code=" + e.getError().toString() );
			return;
		}

		try
		{
			RequestIF request = dispatcher.createRequest( "NewOrder", "HTTPSConnectORB" );
			
			// Config data
			request.getConfig().setField( "Host", "med-rcmtst1.csez.zohocorpin.com" );
			request.getConfig().setField( "Port", "8443" );
			
			request.setField( "OrbitalConnectionUsername", "" );
			request.setField( "OrbitalConnectionPassword", "" );
			
			//Basic Information
			request.setField( "PC3LineItemArray.PC3LineItem.PC3DtlDesc", "1234567890123456789" );
			request.setField( "CurrencyCode", "840" );
			request.setField( "IndustryType", "EC" );
			request.setField( "MessageType", "A" );
			request.setField( "MerchantID", "041756" );
			request.setField( "BIN", "000001" );
			request.setField( "OrderID", "122003SA" );
			request.setField( "AccountNum", "5191409037560100" );
			request.setField( "Amount", "25000" );
			request.setField( "Exp", "1209" );
			request.setField( "TerminalID", "001" );
			//AVS Information
			request.setField( "AVSname", "Jon Smith" );
			request.setField( "AVSaddress1", "4200 W Cypress St" );
			request.setField( "AVScity", "Tampa" );
			request.setField( "AVSstate", "FL" );
			request.setField( "AVSzip", "33607" );
			//Common Optional
			request.setField( "Comments", "This is Java SDK" );
			request.setField( "ShippingRef", "FEDEX WB12345678 Pri 1" );
			
			// PC 2 Data
			request.setField( "TaxInd", "1" );
			request.setField( "Tax", "100" );
			request.setField( "PCOrderNum", "PO8347465" );
			request.setField( "PCDestZip", "33607" );
			request.setField( "PCDestName", "Terry" );
			request.setField( "PCDestAddress1", "88301 Teak Street" );
			request.setField( "PCDestAddress2", "Apt 5" );
			request.setField( "PCDestCity", "Hudson" );
			request.setField( "PCDestState", "FL" );    
			
				
			
			System.out.println( "************************Dumping request object************************" );
			System.out.println( request.dumpFieldValues() );
			
			System.out.println( "************************Process the request object************************" );
			ResponseIF resp = dispatcher.processRequest( request );
						
			// Use the response field name to get the value from the response object.
			// Refer to the SDK documentation for return formats & data elements.
			
			if ( resp.hasField( ORBResp.QuickResp.ProcStatus ) )
			{
				System.out.println( "\n************************Quick response************************" );
				System.out.println( "ProcStatus=" + resp.getField( ORBResp.QuickResp.ProcStatus ) );
				System.out.println( "StatusMsg=" + resp.getField( ORBResp.QuickResp.StatusMsg ) );
			}

			String respCode = "-1";
			if ( resp.hasField( ORBResp.NewOrderResp.ProcStatus ) )
			{
				respCode = resp.getField( "NewOrderResp.RespCode" );
				if ( respCode.equals( "00" ) )
				{
					System.out.println( "\n************************Successfully processed the request************************" );
					System.out.println( "Auth code =>" + resp.getField( "NewOrderResp.AuthCode" ) );
				} 
				else
				{
					System.out.println( "\n************************Failed to process the request****************************" );
				}
			}
			System.out.println( "\n************************Dumping response object************************" );
			System.out.println( resp.dumpFieldValues() );
		} 
		catch ( DispatcherException e )
		{
			// All of the SDK exceptions have an error code that you can test 
			// to determine how to handle a particular error.
			System.out.println( "************************Exception while processing the request************************" );
			System.out.println( e.toString() );
			System.out.println( "Error Code=" + e.getError().toString() );
			if ( e.getError().equals( DispatcherIF.Error.CONNECT_FAILURE ) )
			{
				// Perform connect error handling...
			}
		} 
		catch ( RequestException e )
		{
			System.out.println( "************************Exception while processing the request************************" );
			System.out.println( e.toString() );
			System.out.println( "Error Code=" + e.getError().toString() );
		} 
		catch ( Exception e )
		{
			System.out.println( "************************Exception while processing the request************************" );
			System.out.println( e.toString() );
		}
	}
}