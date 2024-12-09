<a id="top"></a>
[Read.me로 돌아가기](README.md)
> # Calendar API - Schedule
> ## schedule api 링크<br>
> [일정추가](#일정-추가)<br>
> [모든 일정 조회](#모든-일정-조회)<br>
> [특정 일정 조회](#특정-일정-조회)<br>
> [특정 작성자의 일정 조회](#특정-작성자의-일정-조회)<br>
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
> ![add_schedule.png](src/main/resources/image/api/add_schedule.png)
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
> `/schedule/all-schedule`<br>
> 
> ** 쿼리 파라미터 포함 url: `/schedule/all-schedule?page=3&size=2`
>
> ## Request:
>
> ![all_schedule_request.png](src/main/resources/image/api/all_schedule_request.png)
>
> ## Response:
> ![img.all_schedule_response](src/main/resources/image/api/all_schedule_response.png)
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
> `/schedule/{id}`
>
> ## Request:
>
> ![find_schedule_request.png](src/main/resources/image/api/find_schedule_request.png)
>
> ## Response:
> ![find_schedule_response.png](src/main/resources/image/api/find_schedule_response.png)
>
> ## 상세코드:
>
> 200: 정상 등록
> 
[맨 위로](#top)


>
> ## 기능:
> ### 특정 작성자의 일정 조회
>
> ## Method:
> `get`
>
> ## URL:
> `/schedule/{id}`
>
> ## Request:
>
> ![find_by_writerId_schedule_request.png](src/main/resources/image/api/find_by_writerId_schedule_request.png)
>
> ## Response:
> ![find_by_writerId_schedule_response.png](src/main/resources/image/api/find_by_writerId_schedule_response.png)
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
> ![update_schedule.png](src/main/resources/image/api/update_schedule.png)
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
> ![delete_schedule.png](src/main/resources/image/api/delete_schedule.png)
>
> ## Response:
> x
>
> ## 상세코드:
>
> 200: 정상 삭제
>
[맨 위로](#top)
