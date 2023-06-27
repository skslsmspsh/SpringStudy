<%@page import="org.json.simple.JSONObject"%>
<%@page import="org.json.simple.parser.JSONParser"%>
<%@page import="org.springframework.http.HttpMethod"%>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String clientId = "UabT1g81M4Ry_sJfo739";//애플리케이션 클라이언트 아이디값";
	String clientSecret = "AbUl1Ut8LH";//애플리케이션 클라이언트 시크릿값";
	String code = request.getParameter("code");
	String state = request.getParameter("state");
	String redirectURI = URLEncoder.encode("http://localhost:8080/callback.jsp", "UTF-8");
	String id,email,name,nickname;
	
	/*
	접근 토큰(최종 인증 값) 발급에 필요한 정보
	client_id: 애플리케이션 등록 후 발급받은 클라이언트 아이디
	client_secret: 애플리케이션 등록 후 발급받은 클라이언트 시크릿
	grant_type: 인증 타입에 대한 구분값. authorization_code로 값이 고정돼 있습니다.
	state: 애플리케이션이 생성한 상태 토큰
	code: 콜백으로 전달받은 인증 코드
	
	접근 토큰 요청에 성공시 접근토큰과 갱신토큰이 포함된 json 형식의 결과값 반환
	{
	    "access_token": "AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
	    "refresh_token": "c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
	    "token_type": "bearer",
	    "expires_in": "3600"
	}
	위와 같은 형식
	
	개발자도구에서 callback 주소 확인 
	new URL(document.referrer).searchParams.get("redirect_uri")
	
	login에서 api를 요청해 json형식으로 온 state와 code를 callback에 보냄, 그 2개를 callback이 받고 
	callback에서 state와 code를 이용해 api를 요청해서 
	access_token,refresh_token,token_type,expires_in을 json으로 받아옴
	
	사용자 프로필 조회 api = https://openapi.naver.com/v1/nid/me
	Authorization: {토큰 타입] {접근 토큰] 로 구성되어 있음
	
	
	*/
	
	String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
	    + "&client_id=" + clientId
	    + "&client_secret=" + clientSecret
	    + "&redirect_uri=" + redirectURI
	    + "&code=" + code
	    + "&state=" + state;
	String access_token = "";
	String refresh_token = "";
	
	try {
		
	  URL url = new URL(apiURL); //api 호출 url을 담음
	  HttpURLConnection con = (HttpURLConnection)url.openConnection();
	  con.setRequestMethod("GET");
	  
	  int responseCode = con.getResponseCode();
	  BufferedReader br;
	  
	  if (responseCode == 200) { // 정상 호출
	    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	  } else {  // 에러 발생
	    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	  }
	  String inputLine;
	  StringBuilder res = new StringBuilder();
	  while ((inputLine = br.readLine()) != null) {
	    res.append(inputLine);
	  }
	  br.close();
	  if (responseCode == 200) {
	    
		  JSONParser parsing = new JSONParser();
	    Object obj = parsing.parse(res.toString());
	    JSONObject jsonObj = (JSONObject)obj;
	                     
	    access_token = (String)jsonObj.get("access_token");
	    refresh_token = (String)jsonObj.get("refresh_token");

		  out.println(res.toString());
	  }
	} catch (Exception e) {
	  // Exception 로깅
	}
	
	BufferedReader br2;
	
	String apiURL2 = "https://openapi.naver.com/v1/nid/me"; //프로필 조회 api
	URL url2 = new URL(apiURL2);
	
	HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
	con2.setRequestMethod("POST");
	con2.setRequestProperty("Authorization", "Bearer "+access_token);
	
	br2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
	
	String inputLine2;
	StringBuilder res2 = new StringBuilder();
	while ((inputLine2 = br2.readLine()) != null) {
	  res2.append(inputLine2);
	}
	br2.close();
	
	JSONParser parsing = new JSONParser();
  Object obj = parsing.parse(res2.toString());
  JSONObject jsonObj = (JSONObject)obj;
  JSONObject resObj = (JSONObject)jsonObj.get("response");
      
  id = (String)resObj.get("id");
  email = (String)resObj.get("email");
  name = (String)resObj.get("name");
  nickname = (String)resObj.get("nickname");
  
  out.println(res2.toString());
  out.println("================");
  out.println("id = "+id+", email = "+email+", name = "+name+", nickname = "+name);

	
	
	
%>	
<h2>현재 페이지에 저장된 속성 읽기(범위상관없음)</h2>
<ul>
	<li>속성 : ${paramValues }</li>
</ul>
</body>
</html>