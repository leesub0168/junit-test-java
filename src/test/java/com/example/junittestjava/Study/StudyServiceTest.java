package com.example.junittestjava.Study;

import com.example.junittestjava.Member.MemberService;
import com.example.junittestjava.domain.Member;
import com.example.junittestjava.domain.Study;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    public void createStudy_Mock_stubbing_1(@Mock MemberService memberService,
                                   @Mock StudyRepository studyRepository) throws Exception {

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
    public void createStudy_Mock_stubbing_2(@Mock MemberService memberService,
                                            @Mock StudyRepository studyRepository) throws Exception {
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
}