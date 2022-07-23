package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //재사용이 가능하다. 응집도가 높다. 임베디드타입을 소유한 Entity에 생명주기를 의존한다.
@Getter //임베디드 타입에 Setter 금지. 값 변경 안되고 변경하고싶다면 새로운 객체를 생성해야함.
public class Address {
    public String city;
    public String street;
    public String zipcode;
    protected Address(){
    }
    public Address(String city, String street, String zipcode){ //기본 생성자를 필수로 만들어야 한다.
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
