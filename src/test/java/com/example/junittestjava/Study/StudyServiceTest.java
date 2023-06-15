package com.example.junittestjava.Study;

import com.example.junittestjava.Member.MemberService;
import com.example.junittestjava.domain.Member;
import com.example.junittestjava.domain.Study;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class StudyServiceTest {
    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    public void createStudy_Mock_stubbing_1() throws Exception {

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@naver.com");

        /** 1L을 입력받으면 member를 리턴 */
//        when(memberService.findById(1L)).thenReturn(Optional.of(member));

        /** 아무 값을 넣어도 member를 리턴 */
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        /** 1L을 입력받으면 RuntimeException throw */
//        when(memberService.findById(1L)).thenThrow(new RuntimeException());

        /** validate에 1L을 입력해서 호출하면 IllegalArgumentException throw */
        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        StudyService studyService = new StudyService(memberService, studyRepository);

        Study study = new Study(10, "java");
//        studyService.creatNewStudy(1L, study);


        assertEquals("test@naver.com", memberService.findById(1L).get().getEmail());
        assertEquals("test@naver.com", memberService.findById(2L).get().getEmail());
        assertThrows(IllegalArgumentException.class, () -> memberService.validate(1L));
    }



    @Test
    public void createStudy_Mock_stubbing_2() throws Exception {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@naver.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("test@naver.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> memberService.findById(2L));

        assertEquals(Optional.empty(), memberService.findById(1L));

    }


    @Test
    public void createStudy_Mock_stubbing_practice() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@naver.com");

        Study study = new Study(10, "test");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.creatNewStudy(1L, study);

        assertEquals(member, study.getOwner());

    }

    @Test
    public void createStudy_Mock_객체_확인() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@naver.com");

        Study study = new Study(10, "test");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.creatNewStudy(1L, study);

        assertEquals(member, study.getOwner());

        /** notify 메소드가 호출 되었는지 확인하는 메소드 */
        verify(memberService, times(1)).notify(study);
        verify(memberService, times(1)).notify(member);

        /** validate 메소드가 절대 호출되지 않아야 한다 */
        verify(memberService, never()).validate(any());

        /** notify-study가 호출된 후에, notify-member가 호출되야 한다. - 순서가 중요한 경우 */
        InOrder inOrder = inOrder(memberService);
        inOrder.verify(memberService).notify(study);

    }

    @Test
    public void createStudy_Mock_객체_확인_NoMore() throws Exception {
        Member member = new Member();
        member.setId(1L);
        member.setEmail("test@naver.com");

        Study study = new Study(10, "test");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        StudyService studyService = new StudyService(memberService, studyRepository);
        studyService.creatNewStudy(1L, study);

        assertEquals(member, study.getOwner());

        verify(memberService, times(1)).findById(member.getId());
        verify(memberService, times(1)).notify(study);
        verifyNoMoreInteractions(memberService);
        /** verify 이후에 더 이상의 memberService 동작이 없는지 확인 - notify(member)가 동작하기 때문에 테스트는 fail */

    }

}