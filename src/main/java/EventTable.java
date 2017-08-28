import java.util.Vector;


public class EventTable
{
	static final long serialVersionUID = 19571L ;
	private Vector tab = new Vector() ;
	
	public void add( Event a)
	{	int id = tab.size() > 0 ? ((Event)tab.get( tab.size()-1)).getId() : 1;
		a.setId( id);
		tab.add( a) ;
	}
		
	public Event getById( int id)
	{	if( id == 0)
			return null ;
		for( int i= 0;  i < tab.size(); i++)
		{	Event a = (Event)tab.get( i) ;
			if( a.getId() == id)
				return a ;
		}
		return null ;
	}	
	
}
