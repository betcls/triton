import java.util.Date;


public class Event
{
	private int id  = 0 ;
	private Date tijd = new Date() ;
	private String content ;
	
	public int getId()
	{
		return id;
	}
	public void setId( int id)
	{
		this.id = id ;
	}
	
	
	
	public Date getTijd()
	{
		return tijd;
	}
	public void setTijd( Date tijd)
	{
		this.tijd = tijd;
	}
	public String getContent()
	{
		return content;
	}
	public void setContent( String content)
	{
		this.content = content;
	}
	
	

}
