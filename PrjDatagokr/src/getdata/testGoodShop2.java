package getdata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class testGoodShop2 {

		
	public static void main(String[] args) throws IOException {
		 String serviceKey  = "HPG%2FiUcz%2Ft%2FQ8HAFjrKL9sP2JYkLOIIgbnEzvj9enzRYy%2BjXWDidxABqUgD85CcU%2FUhqdtU2SPY%2Btq97nfbRxw%3D%3D";
	     
		 StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/6260000/BusanSafeRestaurantService/getSafeRestaurantList"); /*URL*/
		 urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + serviceKey); /*Service Key*/
		 urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
		 urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
		 urlBuilder.append("&" + URLEncoder.encode("resultType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*JSON방식으로 호출 시 파라미터 resultType=json 입력*/
		 
		 URL url = new URL( urlBuilder.toString() );
		 
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		 conn.setRequestMethod("GET");   // GET / POST
		 conn.setRequestProperty("Content-type", "application/json");  // 결과는 json data 로 받겠다
		 System.out.println("Response code: " + conn.getResponseCode()); // 응답결과 : 200 - Ok
		 
		 BufferedReader rd;
		 if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {  // 정상적으로 조회되면
			 rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		 } else {
			 rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		 }
		 
		 StringBuilder sb = new StringBuilder();
		 String        line;
		 while ((line = rd.readLine()) != null) {
			 sb.append(line);
		 }
		 rd.close();
		 conn.disconnect();
		 
		 System.out.println(sb.toString());
		 
		 //------------------------------------------------------------------------------
		 	 
		 // https://eomcheon.tistory.com/165  
		 // JSON String -> JsonObject
		 
		 // String str = sb.toString();
		 //JsonParser parser = new JsonParser();
		 //JsonElement element = parser.parse(str);
		 //JsonObject rootob = element.getAsJsonObject().get("response").getAsJsonObject();
		 //JsonObject body = rootob.getAsJsonObject().get("body").getAsJsonObject();
		 //JsonObject items = body.getAsJsonObject().get("items").getAsJsonObject();
		 
		 //	List객체로 변환할때
		 // Gson gson = new Gson();
		 // JsonArray item = items.getAsJsonObject().get("item").getAsJsonArray();
		 //	List<ReturnVo> list2 = gson.fromJson(item.toString(), new TypeToken<List<ReturnVo>>(){}.getType());

		 // 단일객체로 변환할때
		 // Gson gson = new Gson();
		 // ReturnVo item = gson.fromJson(items, ReturnVo.class);
		 
		 String jsonStr = sb.toString(); // json 형태의 문자열 
		 JsonElement  element                = JsonParser.parseString(jsonStr);		 
		 JsonObject   getSafeRestaurantList  = element.getAsJsonObject().get("getSafeRestaurantList").getAsJsonObject();
		 JsonObject   header                 = getSafeRestaurantList.getAsJsonObject().get("header").getAsJsonObject();
		 String       resultCode             = header.get("resultCode").getAsString(); 		 	 
		 System.out.println(resultCode);  // 00
		 //----------------------------------------------------------
		 		 
		 JsonObject  body                   =  getSafeRestaurantList.getAsJsonObject().get("body").getAsJsonObject();   
		 JsonObject  items                  =  body.getAsJsonObject().get("items").getAsJsonObject();   
		 JsonArray   item                   =  items.getAsJsonObject().get("item").getAsJsonArray();
		 
		 for (int i = 0; i < item.size(); i++) {
			JsonObject  shop     =  item.get(i).getAsJsonObject(); 
			String      biz_nm   =  shop.get("biz_nm").getAsString();  
			String      addrs    =  shop.get("addrs").getAsString();  
			String      biz_tel  =  shop.get("biz_tel").getAsString();  
			String      geom     =  shop.get("geom").getAsString();
			
			System.out.println("-----------------");
			System.out.println("가계이름:" + biz_nm);
			System.out.println("주소:"     + addrs);
			System.out.println("전화:"     + biz_tel);
			System.out.println("위치:"     + geom);
		 }
	
    }

}






