<a id="top"></a>
> # Calendar API - Schedule
> ## schedule api 링크<br>
> [일정추가](#일정-추가)<br>
> [모든 일정 조회](#모든-일정-조회)<br>
> [특정 일정 조회](#특정-일정-조회)<br>
> [일정 수정](#일정-수정)<br>
> [일정 삭제](#일정-삭제)<br>

> ## 기능: 
> ### 일정 추가
> 
> ## Method: 
> `post`
> 
> ## URL: 
> `/schedule`
> 
> ## Request:
> 
> {<br> "todo" : "What should I do?",<br>
> "writerId" : "d0614652-a3ea-4d1d-bef6-73955cb188a9", <br>
>"password" : "123" <br>}
> 
> ## Response:
> x
> 
> ## 상세코드:
> 
> 200: 정상 등록<br>
> 
[맨 위로](#top)
> ## 기능:
> ### 모든 일정 조회
>
> ## Method:
> `get`
>
> ## URL:
> `/schedule/all-schedule`
>
> ## Request:
>
> x
>
> ## Response:
> {<br>"todo": "What should I do?1",<br>
> "name": "Kim1",<br>
> "createdAt": "2024-02-12",<br>
> "editedAt": "2024-02-12"<br>},<br>
> {<br>"todo": "What should I do?",<br>
> "name": "Kim",<br>
> "createdAt": "2024-02-12",<br>
> "editedAt": "2024-02-12"<br>}
>
> ## 상세코드:
>
> 200: 정상 등록
>
[맨 위로](#top)

>
> ## 기능:
> ### 특정 일정 조회
>
> ## Method:
> `get`
>
> ## URL:
> `/schedule`
>
> ## Request:
>
> {id: "ad1cfe52-e419-4905-a6b0-3410552b7d1b"}
>
> ## Response:
> {<br>"todo": "What should I do?",<br>
> "name": "Kim",<br>
> "createdAt": "2024-02-12",<br>
> "editedAt": "2024-02-12"<br>}
>
> ## 상세코드:
>
> 200: 정상 등록
> 
[맨 위로](#top)


> ## 기능:
> ### 일정 수정
>
> ## Method:
> `put`
>
> ## URL:
> `/schedule`
>
> ## Request:
>
> {<br>
"id": "d985c1a1-6e0c-466a-853f-c322a64182da",<br>
"todo": "I should go home",<br>
"password": "123"<br>
}
>
> ## Response:
> x
>
> ## 상세코드:
>
> 200: 정상 수정
>
[맨 위로](#top)


> ## 기능:
> ### 일정 삭제
>
> ## Method:
> `delete`
>
> ## URL:
> `/schedule`
>
> ## Request:
>
> {<br>
"id" : "d985c1a1-6e0c-466a-853f-c322a64182da",<br>
"password" : "123"<br>
}
>
> ## Response:
> x
>
> ## 상세코드:
>
> 200: 정상 삭제
>
[맨 위로](#top)
