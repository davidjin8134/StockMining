import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockFetcher {  
	
	/*
	* Returns a Stock Object that contains info about a specified stock.
	* @param 	symbol the company's stock symbol
	* @return 	a stock object containing info about the company's stock
	* @see Stock
	*/
	static Stock getStock(String symbol) {  
		String sym = symbol.toUpperCase();
		double price = 0.0;
		int volume = 0;
		double pe = 0.0;
		double eps = 0.0;
		double week52low = 0.0;
		double week52high = 0.0;
		double daylow = 0.0;
		double dayhigh = 0.0;
		double movingav50day = 0.0;
		double marketcap = 0.0;
		ArrayList<Double> histPrices = new ArrayList<Double>(); 
		double totalPrices = 0;
		double score = 0;
		try { 
			
			// Retrieve CSV File
			URL yahoo = new URL("http://finance.yahoo.com/d/quotes.csv?s="+ symbol + "&f=l1vr2ejkghm3j3");
			URLConnection connection = yahoo.openConnection(); 
			InputStreamReader is = new InputStreamReader(connection.getInputStream());
			BufferedReader br = new BufferedReader(is);  
			
			// Parse CSV Into Array
			String line = br.readLine(); 
			String[] stockinfo = line.split(","); 
			
			// Handle Our Data
			StockHelper sh = new StockHelper();
			
			price = sh.handleDouble(stockinfo[0]);
			volume = sh.handleInt(stockinfo[1]);
			pe = sh.handleDouble(stockinfo[2]);
			eps = sh.handleDouble(stockinfo[3]);
			week52low = sh.handleDouble(stockinfo[4]);
			week52high = sh.handleDouble(stockinfo[5]);
			daylow = sh.handleDouble(stockinfo[6]);
			dayhigh = sh.handleDouble(stockinfo[7]);   
			movingav50day = sh.handleDouble(stockinfo[8]);
			marketcap = sh.handleDouble(stockinfo[9]);
		    
			//Get history price of the stock
			URL yahoohistory = new URL("http://ichart.finance.yahoo.com/table.csv?s="+ symbol);
		    URLConnection connection2 = yahoohistory.openConnection(); 
			InputStreamReader is2 = new InputStreamReader(connection2.getInputStream());
			BufferedReader br2 = new BufferedReader(is2); 
			
			// Get rid of the first line since it is not number 
			String strHeader = br2.readLine();
		    int rowsCounted = 0;
			
		    // Read the first line of data
		    String strValues = br2.readLine();
			while(strValues != null&&rowsCounted <120)
			{
				String[] stockHistoryInfo = strValues.split(","); 
				double hisPrice = sh.handleDouble(stockHistoryInfo[6]); 
				histPrices.add(hisPrice);
				totalPrices = totalPrices + hisPrice;
				rowsCounted++;
				strValues = br2.readLine();
			}
			
			double average = totalPrices/histPrices.size();
			double sum = 0;
			for (double i : histPrices)
			{
				sum = sum + Math.pow(i-average, 2);
			}
			double deviationNumber = Math.sqrt(sum/(histPrices.size()-1));
			double bollingerTop = average + (2 * deviationNumber);
			double bollingerBottom = average - (2 * deviationNumber);
            double range = bollingerTop - bollingerBottom;
			double value = price - bollingerBottom;
			score = value/range;
			/*
			 double heatVal = 0x00FF00;
			 long heatRange = 0xFF0000 - 0x00FF00;
			 heatVal += heatRange * score;
			 long heatValBits = (long)heatVal;
			*/
			
		} catch (IOException e) {
			Logger log = Logger.getLogger(StockFetcher.class.getName()); 
			log.log(Level.SEVERE, e.toString(), e);
			return null;
		}
		
		return new Stock(sym, price, volume, pe, eps, week52low, week52high, daylow, dayhigh, movingav50day, marketcap,histPrices,score);
		
	}
}
