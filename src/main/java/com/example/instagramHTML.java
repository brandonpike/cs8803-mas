import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class instagramHTML {

	public static void main(String args[]) {
		try {
			URL url = new URL("https://www.instagram.com/brandonpike/?hl=en");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			System.out.println(con.getResponseCode());
			System.out.println(con.getResponseMessage());
			System.out.println(con.getHeaderFields());
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				System.out.println(inputLine);
				int found = inputLine.indexOf("edge_followed_by");	
				//System.out.println(found);	
				if (found>=0) {
					String fby = inputLine.substring(found-1, found+45);
					fby = fby.substring(fby.indexOf("{")+1, fby.indexOf("}"));
					int index = -1;
					for (int i=0; i<fby.length(); i++) {
						if (Character.isDigit(fby.charAt(i))){
							index=i;
							break;
						}
					}
					fby = fby.substring(index).trim();
					//System.out.println(fby);
					//self.data[username]["followers"] = followers
				}
			}
			in.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		/*

		self.headers = {'User-Agent': 'Mozilla/5.0'}

		*/
	}

}