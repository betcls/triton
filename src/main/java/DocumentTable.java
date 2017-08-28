import java.util.Vector;


public class DocumentTable 
{
	static final long serialVersionUID = 19571L ;
	private Vector tab = new Vector() ;
	
	public void add( Document a)
	{	tab.add( a);
	}
	
	public Document getByCode( String code)
	{	if( code == null)
			return null ;
		
		
		for( int i= 0;  i < tab.size(); i++)
		{	Document a = (Document) tab.get( i) ;
			if( a.getCode().equals(  code))
				return a ;
		}
		return null ;
	}	
}
