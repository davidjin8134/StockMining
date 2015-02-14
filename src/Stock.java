import java.util.ArrayList;

public class Stock { 
	
	private String symbol; 
	private double price;
	private int volume;
	private double pe;
	private double eps;
	private double week52low;
	private double week52high;
	private double daylow;
	private double dayhigh;
	private double movingav50day;
	private double marketcap;
	private double score;
	private ArrayList<Double> histPrices = new ArrayList<Double>(); 
	
	public Stock(String symbol, double price, int volume, double pe, double eps, double week52low,      
					double week52high, double daylow, double dayhigh, double movingav50day, double marketcap,ArrayList<Double> prices,double score) {	
		this.symbol = symbol; 
		this.price = price;	
		this.volume = volume; 
		this.pe = pe; 
		this.eps = eps; 
		this.week52low = week52low; 
		this.week52high = week52high; 
		this.daylow = daylow; 
		this.dayhigh = dayhigh; 
		this.movingav50day = movingav50day; 
		this.marketcap = marketcap;
		this.histPrices = prices;
		this.score = score;
	} 
	
	public String getSymbol() { 
		return this.symbol;		
	} 
	
	public double getPrice() { 		
		return this.price;		
	} 
	
	public int getVolume() {    
		return this.volume;     
	} 
 
	public double getPe() {    
		return this.pe;     
	} 
  
	public double getEps() { 
		return this.eps;     
	} 
  
	public double getWeek52low() {    
		return this.week52low;    
	} 
  
	public double getWeek52high() {  
		return this.week52high;    
	} 
  
	public double getDaylow() {    
		return this.daylow;    
	} 
  
	public double getDayhigh() {    
		return this.dayhigh;     
	} 
  
	public double getMovingav50day() {     
		return this.movingav50day;  
	} 
  
	public double getMarketcap() { 
		return this.marketcap;
	} 
	
	public void showHistory(){
		for (Double p : histPrices)
		{
			System.out.println(p);
			
		}
	}
	
	public void buyOrNot()
	{   
		System.out.println("The score of the stock is: "+this.score);
		if(this.score>0.67)
		{
			System.out.println("You should Sell this one");
		}
		else if(this.score<0.33)
		{
			System.out.println("You should Buy this one");
		}
		else
			System.out.println("No idea");
		}
	
	
}