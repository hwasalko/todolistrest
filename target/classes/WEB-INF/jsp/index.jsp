<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>TODO List 프로그램</title>
	<link rel="stylesheet" href="/webjars/bootstrap/4.1.3/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
	
		<div class="page-header">
			<blockquote class="blockquote">
			  <h3>| TODO List Application <small>pre work</small></h3>
			</blockquote>
	    </div>
	    
	    <hr>  
	    
	    <button type="button" class="btn btn-light">새로고침</button>  
      
      <table class="table table-striped table-dark table-hover table-bordered">
		  <thead>
		    <tr>
		      <th scope="col">id</th>
		      <th scope="col">할일</th>
		      <th scope="col">작성일시</th>
		      <th scope="col">최종수정일시</th>
		      <th scope="col">완료처리</th>
		    </tr>
		  </thead>
		  <tbody>
		    <tr>
		      <th scope="row">1</th>
		      <td>집안일</td>
		      <td>2018-04-01 10:00:00</td>
		      <td>2018-04-01 13:00:00</td>
		      <td></td>
		    </tr>
		    <tr>
		      <th scope="row">2</th>
		      <td>빨래 @1</td>
		      <td>2018-04-01 11:00:00</td>
		      <td>2018-04-01 11:00:00</td>
		      <td>Y</td>
		    </tr>
		    <tr>
		      <th scope="row">3</th>
		      <td>청소 @1</td>
		      <td>2018-04-01 12:00:00</td>
		      <td>2018-04-01 13:00:00</td>
		      <td></td>
		    </tr>
		    <tr>
		      <th scope="row">4</th>
		      <td>방청소 @1 @3</td>
		      <td>2018-04-01 12:00:00</td>
		      <td>2018-04-01 13:00:00</td>
		      <td></td>
		    </tr>
		  </tbody>
		</table>
		
		
		<nav aria-label="...">
		  <ul class="pagination">
		    <li class="page-item disabled">
		      <a class="page-link" href="#" tabindex="-1">Previous</a>
		    </li>
		    <li class="page-item active">
		      <a class="page-link" href="#">1 <span class="sr-only">(current)</span></a>
		    </li>
		    <li class="page-item"><a class="page-link" href="#">2</a></li>
		    <li class="page-item"><a class="page-link" href="#">3</a></li>
		    <li class="page-item">
		      <a class="page-link" href="#">Next</a>
		    </li>
		  </ul>
		</nav>
		
     
	</div>

	<script src="/webjars/jquery/3.3.1-1/jquery.min.js"></script>
	<script src="/webjars/bootstrap/4.1.3/js/bootstrap.min.js"></script>
</body>
</html>
