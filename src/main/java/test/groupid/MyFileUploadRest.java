package test.groupid;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.servlet.MultipartServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.util.lang.Bytes;

public class MyFileUploadRest extends WebPage{
	public MyFileUploadRest(){
		super();
		final ServletWebRequest webRequest = (ServletWebRequest) getRequest();
		MultipartServletWebRequest multiPartRequest;
		try {
			multiPartRequest = webRequest.newMultipartWebRequest(Bytes.megabytes(100), "ignored");

			String uuid = multiPartRequest.getRequestParameters().getParameterValue("uuid").toString();
			if(uuid == null || "".equals(uuid.trim()) || "null".equals(uuid)){
				getRequestCycle().scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", "uuid parameter missing"));
			}else{
				getRequestCycle().scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", uuid));
			}
		}catch(Exception e){
			getRequestCycle().scheduleRequestHandlerAfterCurrent(new TextRequestHandler("application/json", "UTF-8", e.getMessage()));
		}
	}
}
