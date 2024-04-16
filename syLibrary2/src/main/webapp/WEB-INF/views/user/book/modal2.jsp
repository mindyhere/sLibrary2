<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>	
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.1/font/bootstrap-icons.css">
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
function bookInfo(success, data) {
	console.log("콜백= " + data.item[1].title);
	let title=data.item.title;
	let author=data.item.author;
	let publisher=data.item.publisher;
	for (i=0;i<data.item.length;i++){
		console.log("for문: "+i+" => "+data.itemp[i]);
		let str+="<td><a href='#' onclick=''>"+title[i]+"</a>&nbsp;("+author+"&nbsp;|&nbsp;"+publisher+")</td>;
	}
	/*let params={
	"title": "All About IB Psychology Essay - IB Psychology Essay Guide for SL and HL Students",
	"link": "http://www.aladin.co.kr/shop/wproduct.aspx?ItemId=269866535&amp;partner=openAPI&amp;start=api",
	"author": "강수민 (지은이)",
	"pubDate": "2021-05-15",
	"description": "성공적으로 IB 심리 과정을 이수한 저자가 에세이 노하우의 모든것을 이 책에 적어냈다. 심리 에세이를 접근하는 방법부터 안정적인 에세이의 구성, IB 심리에서 용이하게 쓰이는 용어와 문장들이 정리되어 있다.",
	"isbn13": "9791197045318",
	"cover": "https://image.aladin.co.kr/product/26986/65/cover/k452730517_1.jpg",
	"categoryName": "국내도서\u003E자기계발\u003E취업/진로/유망직업\u003E해외취업/이민/유학.연수",
	"publisher": "그루미출판"
	};*/
	console.log("콜백, str: " + str);
	$("#result").append(str);
}

function search() {
	let keyword = $("#keyword").val(); // aladin-bookinfo로 시작하는 id를 가진 div 엘리먼트를 찾습니다.
	console.log("클릭 = "+keyword);
	
	if (keyword !="") { 
		let url = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey=ttbabout_kei2155001&Query="
			+ keyword + "&QueryType=Keyword&MaxResults=10&start=1&SearchTarget=Book&Sort=Title&cover=Big&output=js&callBack=bookInfo";
		console.log("if => "+keyword);
		
		$.ajax({
			url: url,
			async: false,
			dataType: "jsonp",
			jsonp: "bookInfo"
		});
	}
}
</script>

<div id="result">
<table class="table table-sm table-striped table-hover align-middle text-center">
  <tbody>
	<c:forEach var="dto" items="${list }">
	<tr style="background: #fbf7f5 !important;border-color:#FAE0E0;">
		<th scope="row">&nbsp;&nbsp;&nbsp;&nbsp;@&nbsp;${dto.b_id }</th>
		<td>${dto.b_name }&nbsp;(${dto.b_author }&nbsp;|&nbsp;${dto.b_pub })</td>
		<td>
			<label onclick="insert('${dto.b_id }')"><i class="bi bi-box-arrow-right"></i>&nbsp;&nbsp;&nbsp;&nbsp;</label>
    	</td>
    </tr>
	</c:forEach>
  </tbody>	
</table>
</div>

