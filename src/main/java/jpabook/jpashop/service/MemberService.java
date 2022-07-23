package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
1. 이렇게 해놓으면 됨. @autowired같은 final이 불가능한 주입방식을 사용하지 않게 도와주고 생성자 주입을 쉽게 해주는 lombok annotation
2. jpa의 데이터의 모든 변경은 기본적으로 트랜잭션 안에서 수행되어야 함 class에 어노테이션을 쓰면 메소드들은 기본적으로 다 적용됨.
3. readOnly = true는 트랜잭션 내에서 데이터를 변경하지 않을 때 jpa 성능을 최적화한다. + 클래스 내에서 데이터 변경 메소드에는 따로 @Transactional 추가
 */
@Service
@RequiredArgsConstructor //1
@Transactional(readOnly = true) //2
//3.
public class MemberService {
    //final을 넣어야 컴파일 시점에 생성이 되었는지 체크 가능
    private final MemberRepository memberRepository;

    /*
    회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복회원이 있는지 validate
    private void validateDuplicateMember(Member member) {
        //중복회원 있다면 예외 터트림. 실무에서는 동시성때문에 member.getName을 unique 제약조건으로 사용.
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다?");
        }
    }
    //회원 전체 조회
    public List<Member> findMembers(){
        List<Member> members = memberRepository.findAll();
        return members;
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
