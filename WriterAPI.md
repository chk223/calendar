<a id="top"></a>
> # Calendar API - Writer
> ## Writer api 링크<br>
> [작성자 추가](#작성자-추가)<br>
> [모든 작성자 조회](#모든-작성자-조회)<br>
> [작성자 조회](#작성자-조회)<br>
> [작성자 수정](#작성자-수정)<br>
> [작성자 삭제](#작성자-삭제)<br>
> 
>  ## 기능:
> ### 작성자 추가
>
> ## Method:
> `post`
>
> ## URL:
> `/writer/sign-up`
>
> ## Request:
>
> {<br>"name": "kim",<br>
"email": "kim@naver.com"<br>}
>
> ## Response:
> x
>
> ## 상세코드:
>
> 200: 정상 등록<br>
>
[맨 위로](#top)

>  ## 기능:
> ### 모든 작성자 조회
>
> ## Method:
> `get`
>
> ## URL:
> `/writer/all-writer`
>
> ## Request:
> x
>
> ## Response:
> {<br>
"name": "kim1",<br>
"email": "kim1@naver.com",<br>
"joinedAt": "2024-03-12",<br>
"updatedAt": "2024-03-12"<br>
},<br>
{<br>
"name": "kim",<br>
"email": "kim@naver.com",<br>
"joinedAt": "2024-03-12",<br>
"updatedAt": "2024-03-12"<br>
}
>
> ## 상세코드:
>
> 200: 정상 조회<br>
>
[맨 위로](#top)

>  ## 기능:
> ### 작성자 조회
>
> ## Method:
> `get`
>
> ## URL:
> `/writer`
>
> ## Request:
> { "id" : "e24650f5-88ca-409b-8c13-14938d46f6b3"}
>
> ## Response:
> {<br>
"name": "kim",<br>
"email": "kim@naver.com",<br>
"joinedAt": "2024-03-12",<br>
"updatedAt": "2024-03-12"<br>
}
>
> ## 상세코드:
>
> 200: 정상 조회<br>
>
[맨 위로](#top)

>  ## 기능:
> ### 작성자 수정
>
> ## Method:
> `put`
>
> ## URL:
> `/writer`
>
> ## Request:
> {<br>
> "id" : "7ec32069-1179-470e-ab57-ae6d4d3fb009",<br>
"name" : "Lee",<br>
"email" : "Lee@nvaer.com"<br>
> }
>
> ## Response:
> x
> ## 상세코드:
>
> 200: 정상 수정<br>
>
[맨 위로](#top)


>  ## 기능:
> ### 작성자 삭제
>
> ## Method:
> `delete`
>
> ## URL:
> `/writer`
>
> ## Request:
> {<br>
> "id" : "09d66954-b28c-4338-9ba5-16b049120d51"<br>
> }
>
> ## Response:
> x
> ## 상세코드:
>
> 200: 정상 삭제<br>
>
[맨 위로](#top)

