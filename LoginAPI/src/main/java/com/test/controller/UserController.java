package com.test.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.domain.UserVO;
import com.test.service.UserService;

@Controller
public class UserController {
	
	@Autowired UserService userService;
	
	@RequestMapping(value="nlogin1.do", method=RequestMethod.GET)//하나만 집어넣을때는 속성 스킵 가능(value="login.do", method="")
	public String login(HttpSession session) throws UnsupportedEncodingException {
		
		String clientId = "UabT1g81M4Ry_sJfo739";//애플리케이션 클라이언트 아이디값";
	    String redirectURI = URLEncoder.encode("http://localhost:8080/callback.do", "UTF-8");
	    SecureRandom random = new SecureRandom();
	    String state = new BigInteger(130, random).toString();
	    
	    String apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code"
	         + "&client_id=" + clientId
	         + "&redirect_uri=" + redirectURI
	         + "&state=" + state;
	 
	    session.setAttribute("state", state); //검증용
		
		return "redirect:"+apiURL;  
	}
	
	@RequestMapping(value="callback.do", method=RequestMethod.GET)
	public ModelAndView getAccessToken(ModelAndView model, HttpSession session, HttpServletRequest request,UserVO vo) throws IOException, ParseException {
		
		String clientId = "UabT1g81M4Ry_sJfo739";//애플리케이션 클라이언트 아이디값";
		String clientSecret = "AbUl1Ut8LH";//애플리케이션 클라이언트 시크릿값";
		String access_token = "";
		
		BufferedReader br,br2;

		String code = request.getParameter("code");
		String state = request.getParameter("state");
		System.out.println("code : "+code);
		System.out.println("state : "+state);
		
		String redirectURI = URLEncoder.encode("http://localhost:8080/callback.do", "UTF-8");
		
		String apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code"
			    + "&client_id=" + clientId
			    + "&client_secret=" + clientSecret
			    + "&redirect_uri=" + redirectURI
			    + "&code=" + code
			    + "&state=" + state;
		
		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
/*		
		int responseCode = con.getResponseCode();
		
		if (responseCode == 200) {
			System.out.println("정상적으로 접근됨"+responseCode);
		}else {
			System.out.println("비정상 접근"+responseCode);
		}
*/	
		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuilder res = new StringBuilder();
		while ((inputLine = br.readLine()) != null) {
		  res.append(inputLine);
		}
		br.close();
		
		JSONParser parsing = new JSONParser();
	    Object obj = parsing.parse(res.toString());
	    JSONObject jsonObj = (JSONObject)obj;
	            
	    access_token = (String)jsonObj.get("access_token");
	
		con.disconnect();
		//============================================================여기까지 access_token 얻기까지 과정
		
		String apiURL2 = "https://openapi.naver.com/v1/nid/me";
		
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
		
		JSONParser parsing2 = new JSONParser();
		Object obj2 = parsing2.parse(res2.toString());
		JSONObject jsonObj2 = (JSONObject)obj2;
		JSONObject resObj2 = (JSONObject)jsonObj2.get("response");
	
		model.addObject("username", resObj2.get("id"));
		model.addObject("email", resObj2.get("email"));
		model.addObject("name", resObj2.get("name"));
		
		model.setViewName("hi");//hi.jsp
		//============================================================여기까지 사용자 프로필 얻기까지 과정
		
		vo.setEmail((String) resObj2.get("email"));
		vo.setUsername((String) resObj2.get("username"));
		vo.setSns_id((String) resObj2.get("id"));
		userService.insertUser(vo);		
		
		return model;
	}
	
	@RequestMapping(value="klogin1.do", method=RequestMethod.GET)
	public String login1() throws UnsupportedEncodingException {
		String clientId = "c9e9586c57fe79bc3c0ee0c52ad1a6f2";
		String redirectURI = URLEncoder.encode("http://localhost:8080/callbackK.do", "UTF-8");
		
		String apiURL = "https://kauth.kakao.com/oauth/authorize?response_type=code"
		         + "&client_id=" + clientId
		         + "&redirect_uri=" + redirectURI;
		
		return "redirect:"+apiURL;
	}
	
	@RequestMapping(value="callbackK.do", method=RequestMethod.GET)
	public ModelAndView getKakaoToken(ModelAndView model, HttpSession session, HttpServletRequest request,UserVO vo) throws IOException, ParseException {
		
		BufferedReader br,br2;
		
		String code = request.getParameter("code"); //넘어온 code 받기
		String clientId = "c9e9586c57fe79bc3c0ee0c52ad1a6f2"; //kakao client 아이디
		String redirectURI = URLEncoder.encode("http://localhost:8080/callback.do", "UTF-8");
		
		String apiURL="https://kauth.kakao.com/oauth/token?grant_type=authorization_code"
				+ "&client_id="+ clientId
				+ "&redirect_url="+redirectURI
				+ "&code="+code; 
		
		URL url = new URL(apiURL);
		
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		
		String inputLine;
		StringBuilder res = new StringBuilder();
		while ((inputLine = br.readLine()) != null) {
		  res.append(inputLine);
		}
		br.close();
		
		JSONParser parsing = new JSONParser();
		Object obj = parsing.parse(res.toString());
		JSONObject jsonObj = (JSONObject)obj;
		
		String token_type = (String) jsonObj.get("token_type"); //토큰 타입, bearer로 고정
		String access_token = (String) jsonObj.get("access_token"); //사용자 액세스 토큰 값
		//int expires_in = (Integer) jsonObj.get("expires_in"); //액세스 토큰과 ID 토큰의 만료 시간(초), 참고: 액세스 토큰과 ID 토큰의 만료 시간은 동일
		String refresh_token = (String) jsonObj.get("refresh_token"); //사용자 리프레시 토큰 값
		//int refresh_token_expires_in = (Integer) jsonObj.get("refresh_token"); //리프레시 토큰 만료 시간(초)
		
		con.disconnect();
		
		String apiURL2 = "https://kapi.kakao.com/v2/user/me";
		
		URL url2 = new URL(apiURL2);
		
		HttpURLConnection con2 = (HttpURLConnection) url2.openConnection();
		con2.setRequestMethod("GET");
		con2.setRequestProperty("Authorization", "Bearer "+access_token);
		con2.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		br2 = new BufferedReader(new InputStreamReader(con2.getInputStream()));
		
		String inputLine2;
		StringBuilder res2 = new StringBuilder();
		while ((inputLine2 = br2.readLine()) != null) {
		  res2.append(inputLine2);
		}
		br2.close();
		
		JSONParser parsing2 = new JSONParser();
		Object obj2 = parsing2.parse(res2.toString());
		JSONObject jsonObj2 = (JSONObject)obj2;
		JSONObject resObj2 = (JSONObject)jsonObj2.get("kakao_account");
		JSONObject resObj3 = (JSONObject) resObj2.get("profile");
		
		System.out.println(res2);
		model.addObject("username", resObj3.get("nickname"));
		model.addObject("email", resObj2.get("email"));
			
		model.setViewName("hi");
		
		vo.setEmail((String) resObj2.get("email"));
		vo.setUsername((String) resObj3.get("nickname"));
		userService.insertUser(vo);	
		
		return model;
	}
	
	@RequestMapping(value="hi.do")
	public String logout(HttpSession session) { 
		
		return "redirect:hi.do";
	}
}
