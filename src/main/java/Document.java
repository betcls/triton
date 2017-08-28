import java.util.Date;


public class Document
{
	private String code = null ;
	private String bronNaam = null ;
	private String doelNaam = null ;
	private Date tijdstip = new Date() ;
	private String bestemming = null ;
	private int aantalPogingen = 0 ;
	
	
	
	public String getBestemming() {
		return bestemming;
	}
	public void setBestemming(String bestemming) {
		this.bestemming = bestemming;
	}
	
	public Date getTijdstip() {
		return tijdstip;
	}
	public void setTijdstip(Date tijdstip) {
		this.tijdstip = tijdstip;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode( String code)
	{
		this.code = code;
	}
	public String getBronNaam()
	{
		return bronNaam;
	}
	public void setBronNaam( String bronNaam)
	{
		this.bronNaam = bronNaam;
	}
	public String getDoelNaam()
	{
		return doelNaam;
	}
	public void setDoelNaam( String doelNaam)
	{
		this.doelNaam = doelNaam;
	}
	public int getAantalPogingen()
	{
		return aantalPogingen;
	}
	public void setAantalPogingen( int aantalPogingen)
	{
		this.aantalPogingen = aantalPogingen;
	}
	public void addAantalPogingen()
	{
		this.aantalPogingen++ ;
	}
	
	
	
	

}
