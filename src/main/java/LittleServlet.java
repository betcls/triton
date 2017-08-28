

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import com.bc.servlet.otp3.OtpRequest;
import com.bc.servlet.otp3.OtpServlet;

@WebServlet("/MijnServlet")
public class LittleServlet extends OtpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Object processRequest(HttpServletRequest httpRequest, OtpRequest otpRequest) 
			throws Exception 
	{
		// TODO Auto-generated method stub
		return null;
	}

	



}
