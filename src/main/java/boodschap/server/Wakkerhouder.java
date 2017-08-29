package boodschap.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Wakkerhouder extends Thread 
{	private static Wakkerhouder instance = null ;


	private Wakkerhouder()
	{
		
	}
	
	public static Wakkerhouder getInstance()
	{	if( instance == null)
		{	instance = new Wakkerhouder() ;
			
		
		}
		return instance ;
	}
	
	public void run()
	{	while( true)
		{	try 
			{
			URL url = new URL("http://triton-bcrl01.rhcloud.com/Boodschap?action=LaadLijst") ;
			HttpURLConnection conn = (HttpURLConnection)url.openConnection() ;
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			
			// Send post request
			DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
			wr.writeBytes("");
			wr.flush();
			wr.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			//System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);
			
			
			BufferedReader in = 
	                new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
	            
            String response;
            while ( (response = in.readLine()) != null ) 
            {
                //System.out.println( response );
            }
            in.close();
			
			
			Thread.sleep(3*60*60*1000); // om de drie uren
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public static void main(String[] args)
	{
		Wakkerhouder worker = Wakkerhouder.getInstance() ;
		worker.start();
	}

}
