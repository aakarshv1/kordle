import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Data {
	public static void main(String[] arg) throws IOException {
	
		URL url = new URL("https://api.sportradar.com/tennis-t2/en/players/{competitor_id}/profile.json?api_key= vmevcu3979ucen2xvc5hw3x9");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		Map<String, String> parameters = new HashMap<>();
		parameters.put("param1", "val");

		con.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(con.getOutputStream());
		out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
		out.flush();
		out.close();
		
		con.setRequestProperty("Content-Type", "application/json");
		String contentType = con.getHeaderField("Content-Type");
		
		con.setConnectTimeout(5000);
		con.setReadTimeout(5000);
		
		con.setInstanceFollowRedirects(false);
		HttpURLConnection.setFollowRedirects(false);
		
		int status = con.getResponseCode();
		
		if (status == HttpURLConnection.HTTP_MOVED_TEMP
				  || status == HttpURLConnection.HTTP_MOVED_PERM) {
				    String location = con.getHeaderField("Location");
				    URL newUrl = new URL(location);
				    con = (HttpURLConnection) newUrl.openConnection();
				}
		
		BufferedReader in = new BufferedReader(
				  new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				}
				in.close();
				
		con.disconnect();
			
		//int status = con.getResponseCode();

		Reader streamReader = null;

		if (status > 299) {
		    streamReader = new InputStreamReader(con.getErrorStream());
		} else {
		    streamReader = new InputStreamReader(con.getInputStream());
		}
		
		//System.out.println(getFullResponse());
		
		
		
	}
	
	

}
