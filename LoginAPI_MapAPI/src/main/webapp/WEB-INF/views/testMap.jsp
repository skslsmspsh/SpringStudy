<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=u6jfsb151k&submodules=geocoder"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=fe558848fec64f1ac18010f804089f6c&libraries=services,clusterer,drawing"></script>

</head>
<body>

<h3>Naver 지도</h3>
<div>
	<form action="mapTest.do" method="get">
		<input type="text" name="juso" placeholder="도로명/주소명 입력"  />
		<input type="submit" value="검색" />
	</form>
	<div id="map" style="width:400px;height:400px;"></div>
</div>
<hr />
<h3>Kakao 지도</h3>
<div>
	<form action="mapTestK.do" method="get">
		<input type="text" name="juso" placeholder="도로명/주소명 입력"  />
		<input type="submit" value="검색" />
	</form>
	<div id="map2" style="width:400px;height:400px;"></div>
</div>

<script>
//var map = new naver.maps.Map('map', mapOptions); 기본 형태

	var x = '${x}';
	var y = '${y}';

	var map = new naver.maps.Map("map", {
	    center: new naver.maps.LatLng(y, x),
	    zoom: 18,
	    mapTypeControl: true
	});
</script>

<script>
	
	var xx = '${xx}';
	var yy = '${yy}';
	
	var container = document.getElementById('map2'); //지도를 담을 영역의 DOM 레퍼런스
	var options = { //지도를 생성할 때 필요한 기본 옵션
		center: new kakao.maps.LatLng(yy, xx), //지도의 중심좌표.
		level: 2 //지도의 레벨(확대, 축소 정도)
	};

	var map2 = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴

	var mapTypeControl = new kakao.maps.MapTypeControl();

	// 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
	map2.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
</script>

</body>
</html>