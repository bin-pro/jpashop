package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 이게 있어야 데이터 롤백이 됨.
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false) // 기본적으로 @Transactional Spring annotation이 있으면 Rollback을 실행하기 때문에 insert쿼리가 나가지 않음.
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("Lee");
        //when
        Long savedId = memberService.join(member);
        //then
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) //테스트메서드에서 터진 예외가 IllegalStateException이면 Ok.
    public void 중복_회원_예외() throws Exception{
        Member member1 = new Member();
        member1.setName("Lee");
        Member member2 = new Member();
        member2.setName("Lee");

        memberService.join(member1);
        memberService.join(member2);
    }
}