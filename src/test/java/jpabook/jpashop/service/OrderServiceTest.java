package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.item.Movie;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;

    @Autowired OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given
        Member member = createMember("회원1", "서울", "경기", "123-123");

        Item book = new Book();

        book.setName("시골 JPA");
        book.setPrice(10000);
        book.setStockQuantity(10);
        em.persist(book);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        //then
        Order getOrder = orderRepository.findOne(orderId);
        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    public void 주문취소() throws Exception{
        //given

        //when

        //then
    }
    @Test
    public void 상품주문_재고수량초과() throws Exception{
        //given
        assertThrows(NotEnoughStockException.class, () ->{
            Member member = createMember("회원2", "city", "street", "zip-code");

            Item movie = new Movie();
            movie.setStockQuantity(3);
            movie.setPrice(8000);
            movie.setName("화이");
            em.persist(movie);

            int orderCount = 5;
            //when
            orderService.order(member.getId(), movie.getId(), orderCount);
            //then
            fail("재고 수량 부족 예외가 발생해야 한다.");
        });
    }
    private Member createMember(String 회원1, String 서울, String 경기, String zipcode) {
        Member member = new Member();
        member.setName(회원1);
        member.setAddress(new Address(서울, 경기, zipcode));
        em.persist(member);
        return member;
    }

}

