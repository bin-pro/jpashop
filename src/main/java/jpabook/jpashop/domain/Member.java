package jpabook.jpashop.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //order 내의 "member" 필드에 의해 매핑됨.
    private List<Order> orders = new ArrayList<>(); //객체 생성할때 외에 컬렉션을 바꾸지 말고 있는 것을 쓰는게 안전하다. 컬렉션은 필드에서 초기화하자.

}