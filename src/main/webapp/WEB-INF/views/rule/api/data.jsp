<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<body>
	<h3>
		<b>Class Data</b>
	</h3>
	<span>사용자가 가져다 쓸 DB 접속 객체</span><br/><br/>
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
					<td>String</td>
					<td>
						<div class="class-component-bold">driver</div>
						<div>DB(PostgreSQL) Driver 클래스명</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold"">url</div>
						<div>DB(PostgreSQL) url</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">user</div>
						<div>DB(PostgreSQL) 아이디</div>
					</td>
				</tr>
				<tr>
					<td>String</td>
					<td>
						<div class="class-component-bold">pass</div>
						<div>DB(PostgreSQL) 비밀번호</div>
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
						<span class="class-component-bold">Data</span>()
						<div>Data 객체의 기본 생성자(JDBC 정보 세팅)</div>
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
					<td>Connection</td>
					<td>
						<span class="class-component-bold">getConnect</span>()
						<div>
							DB 설정 정보를 넣고 DB 연결 객체를 반환함.
						</div>
					</td>
				</tr>
				<tr>
					<td>void</td>
					<td>
						<span class="class-component-bold">closeAll</span>(ResultSet rs,PreparedStatement ps, Connection conn)
						<div>
							DB와 관련된 자원을 닫음.
						</div>
					</td>
				</tr>
				<tr>
					<td>List&lt;Metadata&gt;</td>
					<td>
						<span class="class-component-bold">getMetadata</span>(int metadata_id)
						<div>
							해당되는 Metadata 테이블을 가져옴. 
							metadata_id가 0일 경우 전체 Metadata 테이블을 가져옴.
						</div>
					</td>
				</tr>
				<tr>
					<td>List&lt;Metadata&gt;</td>
					<td>
						<span class="class-component-bold">getMetadataAndProgram</span>(int metadata_id)
						<div>
							해당되는 Metadata 테이블과 Program 테이블을 조인하여 가져옴. 
							metadata_id가 0일 경우 전체 Metadata 테이블과 Program 테이블의 조인 결과를 가져옴.
						</div>
					</td>
				</tr>
				<tr>
					<td>List&lt;Speaker&gt;</td>
					<td>
						<span class="class-component-bold">getSpeaker</span>(int metadata_id)
						<div>
							해당되는 Speaker 테이블을 가져옴.
							metadata_id가 0일 경우 전체 Speaker 테이블을 가져옴.
						</div>
					</td>
				</tr>
				<tr>
					<td>List&lt;Utterance&gt;</td>
					<td>
						<span class="class-component-bold">getUtterance</span>(int metadata_id)
						<div>
							해당되는 Utterance 테이블을 가져옴.
							metadata_id가 0일 경우 전체 Utterance 테이블을 가져옴.
						</div>
					</td>
				</tr>
				<tr>
					<td>List&lt;EojeolList&gt;</td>
					<td>
						<span class="class-component-bold">getEojoelList</span>(int metadata_id)
						<div>
							해당되는 EojeolList 테이블을 가져옴.
							metadata_id가 0일 경우 전체 EojeolList 테이블을 가져옴.
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>