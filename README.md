# SDTM (smart-data-tracing-management)
> AI 학습용 데이터 구축(2차) 사업 - 데이터 품질 관리 및 검증 플랫폼  

2020.10.05-2021.02.28

크라우드 소싱에 의해 전사된 한국어 강의/회의 음성/고객 응대/상담 음성 데이터를 관리하며, ETRI 전사규칙 기반으로 룰을 만들어 데이터 정보를 확인하고 전사규칙 준수 여부를 점검합니다.  

![](./screenshot.png)

## 매뉴얼 및 API 문서  
[SDTM 매뉴얼](https://docs.google.com/document/d/16VVzlrllfjb7KbuxBibKLUCKckbejyID-tzL9-v_DC8/edit?usp=sharing)  
[Javadoc](https://devyeony.github.io/smart-data-tracing-management/docs/)

## 주요 기능

#### 사용자
* Spring Security를 통한 인증 및 접근 제어, 자동 로그인
* 임시 비밀번호, 가입 승인 완료 메일 발송
* 전사데이터 JSON 파일, 프로그램 Excel 파일, 음성 wav 파일 서버로 업로드
* 전사데이터 정보 확인·수정 및 워드, 엑셀, JSON 파일로 다운로드
* 업로드한 음성 파일 재생
#### 관리자
* 신규 사용자 등록시 가입 승인 요청 메일 발송
* 전사 규칙 준수 여부 확인을 위한 데이터 검수(= 룰) 방식
  * 메서드 : JavaCompiler를 이용해 페이지에서 자바 코드를 받아 실행
  * SQL : 페이지에서 SQL 쿼리문을 받아 Statement를 이용해 쿼리문 실행
* 룰 등록, 작성, 수정, 버전관리
* 사용자 관리 (가입승인, 계정 활성화, 회원 탈퇴)
* SDTM의 사용 로그 및 룰 기록 조회

## 시연 영상  
[![Video Label](https://img.youtube.com/vi/_LzA6-6Jl7Q/maxresdefault.jpg)](https://youtu.be/_LzA6-6Jl7Q?t=0s)  
[시연 영상 보러가기](https://youtu.be/_LzA6-6Jl7Q?t=0s)


