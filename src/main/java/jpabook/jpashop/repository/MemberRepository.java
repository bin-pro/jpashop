package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository //component scan에 의해 자동으로 스프링 빈에 등록되어 스프링 빈 내에서 관리됨.
@RequiredArgsConstructor
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public void save(Member member){
        em.persist(member); //jpa가 얘를 저장하는 로직
    }
    public Member findOne(Long id){
        return em.find(Member.class, id);
    }//jpa가 제공하는 find 메서드(단건조회)를 이용하여 member를 반환


    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList(); //createQuery(jpql, 반환타입)
    } //from의 대상이 table이 아니라 Entity.
    /*
    sql과 jpql의 차이? sql은 테이블을 대상으로 쿼리를 날리지만 jpql은 엔티티(객체)를 대상으로 쿼리를 날린다.
     */

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
    /*
        파라미터 바인딩은 :을 사용함. 이름으로 구분하면 된다. m.name = >> :name << 얘를 setParameter메서드에서 똑같이 바인딩 해주면 됨.
     */
}
