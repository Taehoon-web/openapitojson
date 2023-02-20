package getdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestGoodShop {

	private static String serviceKey = "";
	
	public static void main(String[] args) {
		
		int  numOfRows   = 10;  // 한페이지에 보여줄 자료수
		int  pageNo      = 1;   // 보여줄 페이지 번호 1page
		
		String  apiURL  = "http://apis.data.go.kr/6260000/BusanSafeRestaurantService";
		apiURL         += "?serviceKey=" + serviceKey;
		apiURL         += "&numOfRows="  + numOfRows;
		apiURL         += "&pageNo="     + pageNo;
		apiURL         += "&resultType=json";
		
		String  responseBody = get(apiURL); 
		
		System.out.println( responseBody );

	}

	private static String get(String apiURL) {
		HttpURLConnection  conn = connect(apiURL);
		
		try {
			conn.setRequestMethod("GET");
			
			int responseCode = conn.getResponseCode();
			if( responseCode == HttpURLConnection.HTTP_OK) {
				return readBody(conn.getInputStream());
			} else {
				return readBody(conn.getErrorStream());
			}			
		} catch (IOException e) {			
			throw new RuntimeException("API 요청과 응답실패 ", e);
		} finally {
			conn.disconnect();
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body); 
		
		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder  responseBody = new StringBuilder();
			
			String line = null;
			while((line = lineReader.readLine())!= null) {
				responseBody.append(line);
			}
			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패 ", e);	
		} 		
	}

	private static HttpURLConnection connect(String apiURL) {
		try {
			URL  url = new URL(apiURL);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다 " + apiURL, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다 " + apiURL, e);
		} 
		
	}

}







