import java.util.Vector;


public class AandeelhouderTable 
{
	static final long serialVersionUID = 19571L ;
	private Vector tab = new Vector() ;
	
	public void add( Aandeelhouder a)
	{	tab.add( a);
	}
	
	
	public Aandeelhouder getByCode( String code)
	{	if( code == null)
			return null ;
		for( int i= 0;  i < tab.size(); i++)
		{	Aandeelhouder a = (Aandeelhouder)tab.get( i) ;
			if( a.getCode().equals(  code))
				return a ;
		}
		return null ;
	}	
	
}
