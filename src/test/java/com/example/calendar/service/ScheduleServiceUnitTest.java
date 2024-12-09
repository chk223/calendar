package com.example.calendar.service;

import com.example.calendar.domain.Schedule;
import com.example.calendar.domain.Writer;
import com.example.calendar.dto.Page;
import com.example.calendar.dto.ScheduleDisplay;
import com.example.calendar.dto.ScheduleInput;
import com.example.calendar.exception.ApiException;
import com.example.calendar.repository.ScheduleRepository;
import com.example.calendar.repository.WriterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class ScheduleServiceUnitTest {
    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private WriterRepository writerRepository;
    @InjectMocks
    private CalendarServiceImpl calendarService;

//    @BeforeEach
//    void initTest() { // 수동 주입 방식
//        calendarService = new CalendarServiceImpl(scheduleRepository,writerRepository);
//    }

    @Test
    void isFieldHasError_throwException_whenErrorOccur() {
        //given
        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        FieldError fieldError1 = new FieldError("input","id","fieldError");
        FieldError fieldError2 = new FieldError("input","name","fieldError");

        Mockito.when(bindingResult.hasErrors()).thenReturn(true);
        Mockito.when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError1, fieldError2));
        //when
        ApiException apiException = Assertions.assertThrows(ApiException.class, () -> {
            isFieldHasError(bindingResult);
        });
        //then
        Assertions.assertEquals("검증 오류가 발생했습니다.", apiException.getMessage());
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, apiException.getStatus());
        Assertions.assertEquals(List.of("id","name"), apiException.getErrorField());
    }
    /**
     * 필드 값의 오류 여부 검증
     * @param result 필드 값을 검증 한 결과를 갖는 객체
     */
    private static void isFieldHasError(BindingResult result) {
        if (result.hasErrors()) {
            // 검증 실패 된 필드명을 예외에 포함해서 던짐
            List<String> errorMessages = result.getFieldErrors().stream()
                    .map(FieldError::getField)
                    .toList();
            throw new ApiException("검증 오류가 발생했습니다.", HttpStatus.BAD_REQUEST, errorMessages);
        }
    }

    @Test
    void addSchedule() {
        //given
        //검증은 완료 됐다고 가정함
        //테스트용 Writer, input 객체 생성
        Writer mockWriter = new Writer("name", "email@email.com");
        ScheduleInput scheduleInput = new ScheduleInput("todo",mockWriter.getId(),"password");
        //서비스 로직에서 writerId가 작성자 목록에 있는지 확인하는 과정 통과
        Mockito.when(writerRepository.find(mockWriter.getId())).thenReturn(Optional.of(mockWriter));
        //테스트 결과를 확인 할 리스트. -> 작성자 id를 통해 할 일 검색하는 방법 사용
        List<Schedule> mockSchedules = new ArrayList<>();
        //scheduleRepository가 호출되면 테스트 결과에 값 추가, 추가 값 반환
        Mockito.when(scheduleRepository.create(Mockito.any(Schedule.class)))
                        .thenAnswer(invocation -> {
                            Schedule schedule = invocation.getArgument(0);
                            mockSchedules.add(schedule);
                            return schedule;
                        });
        Mockito.when(scheduleRepository.findByWriterId(mockWriter.getId())).thenReturn(mockSchedules);
        //when
        calendarService.createSchedule(scheduleInput);//서비스 메서드
        //then
        assertThat(calendarService.findScheduleByWriterId(mockWriter.getId()).size()).isEqualTo(1);
        assertThat(mockSchedules.get(0).getWriterId()).isEqualTo(mockWriter.getId());
    }

    @Test
    public void getAllSchedule() {
        //given
        int page = 1;
        int size = 2;
        int totalSchedule = 4;
        Writer mockWriter = new Writer("name", "email@email.com");
        //writer 저장소 사용 시 반환 값 설정
        Mockito.when(writerRepository.find(mockWriter.getId()))
                .thenReturn(Optional.of(mockWriter));
        List<Schedule> mockSchedules = List.of(
                new Schedule("todo1", mockWriter.getId(), "password1"),
                new Schedule("todo2", mockWriter.getId(), "password2"),
                new Schedule("todo3", mockWriter.getId(), "password3"),
                new Schedule("todo4", mockWriter.getId(), "password4")
        );
        //schedule 저장소 사용 시 반환 값 설정
        Mockito.when(scheduleRepository.countAll()).thenReturn(totalSchedule);
        Mockito.when(scheduleRepository.findAll(2, size))
                .thenReturn(mockSchedules.subList(2, 4));
        //when
        Page<ScheduleDisplay> result = calendarService.findAllScheduleBySort(page, size);
        //then
        assertThat(result.getNowPage()).isEqualTo(page+1);
        assertThat(result.getTotalDataCount()).isEqualTo(totalSchedule);
        assertThat(result.getContent().get(0).getTodo()).isEqualTo("todo3");
        assertThat(result.getContent().get(1).getTodo()).isEqualTo("todo4");
        //Mock 객체 호출 여부 확인
        Mockito.verify(scheduleRepository).countAll();
        Mockito.verify(scheduleRepository).findAll(2, size);
    }

    @Test
    public void getAllSchedule_whenPageOutOfRange() {
        //given
        int page = 3;
        int size = 2;
        int totalSchedules = 4;
        Mockito.when(scheduleRepository.countAll()).thenReturn(totalSchedules);
        //when
        Page<ScheduleDisplay> result = calendarService.findAllScheduleBySort(page, size);
        //then
        assertThat(result.getNowPage()).isEqualTo(page);//빈 배열 반환하기 때문에 this.page = page.
        assertThat(result.getTotalDataCount()).isEqualTo(totalSchedules);
        assertThat(result.getContent()).isEmpty();
        //mock 객체 호출 여부
        Mockito.verify(scheduleRepository).countAll();
        Mockito.verifyNoMoreInteractions(scheduleRepository);//findAll 호출 x
    }
    @Test
    public void getAllSchedule_whenInvalidPageOrSize() {
        // given
        int page = -1; // 잘못된 페이지
        int size = 0;  // 잘못된 크기
        int totalSchedules = 4;
        Mockito.when(scheduleRepository.countAll()).thenReturn(totalSchedules);
        //when
        Page<ScheduleDisplay> result = calendarService.findAllScheduleBySort(page, size);
        //then
        assertThat(result.getNowPage()).isEqualTo(page);
        assertThat(result.getTotalDataCount()).isEqualTo(totalSchedules);
        assertThat(result.getContent()).isEmpty();

        Mockito.verify(scheduleRepository).countAll();
        Mockito.verifyNoMoreInteractions(scheduleRepository); //findAll 호출 x
    }


    /**
     * 작성자의 고유 식별자를 통해 해당 작성자가 작성한 일정을 모두 조회
     * @param id 작성자 아이디(고유 식별자)
     * @return 작성자가 작성한 일정 리스트
     */
    @Test
    public void getScheduleByWriterId() {
        //given
        int totalSchedule = 4;
        Writer mockWriter = new Writer("name", "email@email.com");
        //writer 저장소 사용 시 반환 값 설정
        Mockito.when(writerRepository.find(mockWriter.getId()))
                .thenReturn(Optional.of(mockWriter));
        Writer mockWriter1 = new Writer("name", "email@email.com");
        //writer 저장소 사용 시 반환 값 설정
        Mockito.when(writerRepository.find(mockWriter1.getId()))
                .thenReturn(Optional.of(mockWriter1));
        List<Schedule> mockSchedules = List.of(
                new Schedule("todo1", mockWriter.getId(), "password1"),
                new Schedule("todo2", mockWriter.getId(), "password2"),
                new Schedule("todo3", mockWriter1.getId(), "password3"),
                new Schedule("todo4", mockWriter1.getId(), "password4")
        );
        //schedule 저장소 사용 시 반환 값 설정
        Mockito.when(scheduleRepository.findByWriterId(mockWriter.getId())).thenReturn(
                mockSchedules.stream().filter(schedule -> schedule.getWriterId().equals(mockWriter.getId()))
                        .toList());
        Mockito.when(scheduleRepository.findByWriterId(mockWriter1.getId())).thenReturn(
                mockSchedules.stream().filter(schedule -> schedule.getWriterId().equals(mockWriter1.getId()))
                        .toList());
        //when
        List<ScheduleDisplay> result = calendarService.findScheduleByWriterId(mockWriter.getId());
        List<ScheduleDisplay> result1 = calendarService.findScheduleByWriterId(mockWriter1.getId());
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result1.size()).isEqualTo(2);
        Mockito.verify(scheduleRepository).findByWriterId(mockWriter.getId());
        Mockito.verify(scheduleRepository).findByWriterId(mockWriter1.getId());
    }

    //나머지는 생략... 나중에 하겠습니다..ㅋㅋㅋ



}
