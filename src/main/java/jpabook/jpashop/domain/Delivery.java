package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) //EnumType은 Ordinal, String 가능한데 Ordinal이 아니라 String으로 해야됨.
    // ordinal은 쿼리를 이해하기에 제약사항이 많음 무조건 String 안쓰면 장애남.
    private DeliveryStatus status; //READY, COMP(배송)
}
