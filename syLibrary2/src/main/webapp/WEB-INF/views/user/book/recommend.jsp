<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- container-md -->
<div class="container-md">
  <div class="d-flex justify-content-around">
	<div id="row" class="row"> <!-- row -->
		<c:forEach var="dto" items="${list }">
			<div class="col">
				<a href="/user/search/bookInfo/${dto.b_id}">
				<div class="card" id="book" style="width: 10em">
					<img src="${dto.b_url}" class="card-img" alt="준비중" title="more detail">
					<div class="card-body" id="bookCover">
						<input type="hidden" name="b_id" value="${dto.b_id }">
						<h5 class="card-title" style="color:white">${dto.b_name }</h5>
						<h6>${dto.b_author }</h6>
					</div>
				</div>
				</a>
			</div>
		</c:forEach> 
	</div> <!-- row 끝 -->
</div>
</div> <!-- container-md 끝 -->