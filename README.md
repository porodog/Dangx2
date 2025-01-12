# **댕댕히어로즈 ( Dangx2 )**
> 유기견의 분양 및 입양이 주 목적이 아닌 봉사활동 위주의 애견커뮤니티
기존의 존재하던 유기견 분양&봉사 사이트 리뉴얼
**리뉴얼 작업 사이트** : [애니멀스토리](http://xn--9i1bs4kxmf1z6pdy7y.com/)

## 제작 기간 & 참여 인원 & 참여부분
  - 제작 기간 : 24/09/02 ~ 24/10/14
  - 참여 인원 : 더조은컴퓨터학원 팀 세미프로젝트(4명)
  - 참여 부분 : 페이지 전체 Front-end 구성
    - 메인, 로그인, 회원가입, ID/PW 찾기, 약관동의, 마이페이지, 게시판, 봉사, 후원 등

## 사용 기술
- Java 17
- Jsp + Servlet
- MySQL 8.4
- AJax
- JSON
- HTML + CSS + JS
- JSTL
  
## DataBase ERD
![image](https://github.com/user-attachments/assets/a420c243-fb1f-486b-b1f3-adceb2a534a2)

## 핵심기능
댕댕히어로즈 서비스의 핵심 기능은 봉사신청 및 커뮤니티 기능입니다. 캘린더 기능을 이용하여 사용자가 직접 봉사 신청을 하고, 게시판을 사용하여 사용자들과의 커뮤니케이션, 봉사, 유기견에 관한 정보 공유를 할 수 있습니다.

## 참여 페이지 
<details>
  <summary>페이지 구현</summary>

  ## 1. 메인 
  ![main](https://github.com/user-attachments/assets/0a833470-6fa9-47a6-b2a1-de6a6fac241d)

  ## 2. 소개 
  ![소개페이지](https://github.com/user-attachments/assets/635ddbf8-c9b3-470b-a005-4eb6e4410b0c)

  ## 3. 쉼터 안내
  ![쉼터](https://github.com/user-attachments/assets/07fa6b93-808d-4df2-829e-c5157040c860)

  ## 4. 쉼터 / 입양 아이들
  ![아이들](https://github.com/user-attachments/assets/e2a57681-1c05-4783-805b-95ad52d4111d)

  ## 5. 후원 안내
  ![후원](https://github.com/user-attachments/assets/fff7724d-c286-48a2-b733-ec477a79a9ed)

  ## 6. 봉사 안내
  ![봉사](https://github.com/user-attachments/assets/be675125-adc6-4327-a671-941b5d9ec078)

  ### 6.1 봉사 신청 달력 
  ![봉사달력](https://github.com/user-attachments/assets/99036243-e529-4b27-a436-bbbc8d8c105a)
  ( FullCalendar )

  ### 6.2 봉사 신청
  ![봉사신청](https://github.com/user-attachments/assets/4755dc55-dbac-4a2d-ad15-432ac905a6d4)

  ### 6.3 봉사 수정
  ![봉사수정](https://github.com/user-attachments/assets/87c64b03-4fb5-4797-ae33-c0f49b4963de)
  → 신청후 캘린더에 저장
  ![달력저장](https://github.com/user-attachments/assets/58d113c4-4172-40fc-a383-e60b940937d1)

  ## 7. 게시판 총 3개의 카테고리 동일
  ![게시판](https://github.com/user-attachments/assets/9fed7e3d-3d23-440f-ac6b-3f920fa4b6c9)
</details>
<details>
   <summary>작성 코드</summary>

  1. reset CSS
   ```CSS
@charset "UTF-8";

html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	em, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup, tt, var,
	b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label, legend,
	table, caption, tbody, tfoot, thead, tr, th, td, article, aside, canvas,
	details, embed, figure, figcaption, footer, header, hgroup, menu, nav,
	output, ruby, section, summary, time, mark, audio, video {
	margin: 0;
	padding: 0;
	border: 0;
	font-size: 100%;
	font: inherit;
	vertical-align: baseline;
}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure, footer, header, hgroup,
	menu, nav, section {
	display: block;
}

body {
	line-height: 1;
}

ol, ul {
	list-style: none;
}

blockquote, q {
	quotes: none;
}

blockquote:before, blockquote:after, q:before, q:after {
	content: '';
	content: none;
}

table {
	border-collapse: collapse;
	border-spacing: 0;
}
```
2. Common CSS
  - Header, Footer 
  - 대표 색상 : #EC6818
  - SNS 버튼 생성 ( 인스타, 블로그, 카페 )
</details>


