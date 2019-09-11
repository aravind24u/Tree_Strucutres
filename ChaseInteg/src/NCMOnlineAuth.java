import com.jpmc.cms.sdk.configurator.ConfigurationData;
import com.jpmc.cms.sdk.framework.Dispatcher;
import com.jpmc.cms.sdk.framework.MessageProcessor;
import com.jpmc.cms.sdk.framework.exceptions.DispatcherException;
import com.jpmc.cms.sdk.framework.interfaces.DispatcherIF;

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
public class NCMOnlineAuth
{
	public static void main( String[] args )
	{
		DispatcherIF dispatcher = null;
		MessageProcessor request = null;
		String div = "123456";

		if ( args.length > 0 )
		{
			div = args[0];
		}
		
		String message = "P74VABC123456789DEF       AX371234567890123    " + 
				"120600001234560000000075758401    AU AZ03079     USFR 9876\r";
		
		try
		{
			// Create a Dispatcher - This is your entry point to  the SDK.
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
			request = new MessageProcessor();
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
			
			ConfigurationData config = dispatcher.getConfig("HTTPSConnectSLM");
			
			config.setField( "Host", "localhost" );
			config.setField( "Port", "9898" );
			config.setField( "UserName", "yournetproxyusername" );
			config.setField( "UserPassword", "yourproxypassword" );

			request.setMerchantID( div );
			
			
			System.out.println( "************************Process the request object************************" );
			byte[] resp = request.process( message.getBytes(), config );
			
			String str = new String( resp );
			
			System.out.println( str );
			
		} 
		catch ( DispatcherException e )
		{
			// All of the SDK exceptions have an error code that you can test 
			// to determine how to handle a particular error.
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