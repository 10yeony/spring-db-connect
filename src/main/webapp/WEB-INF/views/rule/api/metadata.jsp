<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<body>
	<h3>
		<b>Class Metadata</b>
	</h3>
	<span>Metadata 테이블(한 강의에 대한 전반적인 정보를 담고 있는 테이블)</span><br/><br/>
	<h5>
		<b>Field Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Modifier and Type</th>
					<th>Field and Description</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>int</td>
					<td>
						<div class="class-component-bold">id</div>
						<div>auto increment된 기본키</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold"">creator</div>
						<div>스크립트 작성자</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">annotation_level</div>
						<div></div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">year</div>
						<div>제작년도</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">sampling</div>
						<div></div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">title</div>
						<div>파일 이름</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">category</div>
						<div></div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">audio_type</div>
						<div>파일명에서 앞두글자를 따옴(강의/회의/상담/고객응대에 따라 파일명 패턴이 다름)</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">distributor</div>
						<div></div>
					</td>
				</tr>
				<tr>
					<td>int</td>
					<td>
						<div class="class-component-bold">sentence_count</div>
						<div>문장 개수(Data의 getMetadataAndProgram() 메소드를 통해 가져올 수 있음.)</div>
					</td>
				</tr>
				<tr>
					<td>int</td>
					<td>
						<div class="class-component-bold">eojeol_total</div>
						<div>어절 개수(Data의 getMetadataAndProgram() 메소드를 통해 가져올 수 있음.)</div>
					</td>
				</tr>
				<tr>
					<td>Program</td>
					<td>
						<div class="class-component-bold">program</div>
						<div>Program 테이블(Data의 getMetadataAndProgram() 메소드를 통해 가져올 수 있음.)</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>Constructor Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Constructor and Description</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>
						<span class="class-component-bold">Metadata</span>()
						<div>Metadata 객체의 기본 생성자</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div><br/>
	<h5>
		<b>Method Summary</b>
	</h5>
	<div class="table-responsive">
		<table class="table table-bordered" width="100%" cellspacing="0">
			<thead>
				<tr>
					<th>Modifier and Type</th>
					<th>Method and Description</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td>int</td>
					<td>
						<span class="class-component-bold">getId</span>()
						<div>
							기본키를 반환함
						</div>
					</td>
				</tr>
				<tr>
					<td>void</td>
					<td>
						<span class="class-component-bold">setId</span>(int id)
						<div>
							기본키를 세팅함
						</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<span class="class-component-bold">getCreator</span>()
						<div>
							스크립트 작성자를 반환함
						</div>
					</td>
				</tr>
				<tr>
					<td>void</td>
					<td>
						<span class="class-component-bold">setCreator</span>(String creator)
						<div>
							스크립트 작성자를 세팅함
						</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<span class="class-component-bold">getAnnotation_level</span>()
						<div>
							annotation_level를 반환함
						</div>
					</td>
				</tr>
				<tr>
					<td>void</td>
					<td>
						<span class="class-component-bold">setAnnotation_level</span>(String annotation_level)
						<div>
							annotation_level를 세팅함
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>