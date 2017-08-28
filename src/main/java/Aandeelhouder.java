
public class Aandeelhouder
{
	static final long serialVersionUID = 19571L ;
	
	private String code ;
	private String naam ;
	private String alias ;
	private String mailAdres ;
	private int pogingen ;
	
	
	private DocumentTable documentTab = new DocumentTable() ;
	private EventTable eventTab = new EventTable() ;
	
	public EventTable getEventTab()
	{	if(eventTab == null)
		eventTab = new EventTable() ;
		return eventTab;
	}
	public DocumentTable getDocumentTab()
	{	if(documentTab == null)
			documentTab = new DocumentTable() ;
		return documentTab;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode( String code)
	{
		this.code = code;
	}
	public String getNaam()
	{
		return naam;
	}
	public void setNaam( String naam)
	{
		this.naam = naam;
	}
	public String getAlias()
	{
		return alias;
	}
	public void setAlias( String alias)
	{
		this.alias = alias;
	}
	public String getMailAdres()
	{
		return mailAdres;
	}
	public void setMailAdres( String mailAdres)
	{
		this.mailAdres = mailAdres;
	}
	public int getPogingen()
	{
		return pogingen;
	}
	public void setPogingen( int pogingen)
	{
		this.pogingen = pogingen;
	}
	
	

}
