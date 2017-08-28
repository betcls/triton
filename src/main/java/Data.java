import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

public class Data
{
	private AandeelhouderTable aandeelhouders = new AandeelhouderTable();
	private XStream xstream = new XStream( new StaxDriver());
	
	
	

	public Data()
	{
		super();
		File dir = new File( "./data") ;
		if( !dir.isDirectory())
		{
			dir.mkdirs() ;
		}
	}



	public synchronized void save() throws Exception
	{
		ObjectOutputStream objectOutputStream = xstream
				.createObjectOutputStream( new FileOutputStream( "./data/data.xml"));
		objectOutputStream.writeObject( aandeelhouders);
		objectOutputStream.flush();
		objectOutputStream.close();
	}
	
	

	public synchronized void read() throws Exception
	{
		
		ObjectInputStream objectInputStream = xstream.createObjectInputStream(new FileInputStream("./data/data.xml"));
		
		aandeelhouders = (AandeelhouderTable) objectInputStream.readObject();

		objectInputStream.close();
	}

	public String formatXml() throws Exception
	{
		String xml = xstream.toXML( aandeelhouders);
		Transformer serializer = SAXTransformerFactory.newInstance()
				.newTransformer();

		serializer.setOutputProperty( OutputKeys.INDENT, "yes");
		serializer.setOutputProperty( "{http://xml.apache.org/xslt}indent-amount",
				"2");

		Source xmlSource = new SAXSource( new InputSource(
				new ByteArrayInputStream( xml.getBytes())));
		StreamResult res = new StreamResult( new ByteArrayOutputStream());

		serializer.transform( xmlSource, res);

		return new String(
				((ByteArrayOutputStream) res.getOutputStream()).toByteArray());

	}

	public AandeelhouderTable getAandeelhouders()
	{
		return aandeelhouders;
	}

}
