package com.example.junittestjava.Study;

import com.example.junittestjava.Member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    public void createStudyService() throws Exception {
        //given
        //when
        StudyService studyService = new StudyService(memberService, studyRepository);

        //then
        assertNotNull(studyService);
    }
}