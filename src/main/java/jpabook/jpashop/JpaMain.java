package jpabook.jpashop;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Order order = new Order();
            order.addOrderItem(new OrderItem());


            tx.commit();
        } catch(Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();

    }

}

/**
 * # 다대일 [N:1]
 *
 * - 연관관계 매핑시 고려해야할 사항
 *  - 1. 다중성
 *      - 다대일 : @ManyToOne
 *      - 일대다 : @OneToMany
 *      - 일대일 : @OneToOne
 *      - 다대다 : @ManyToMany (실무에서는 거의 안씀)
 *  - 2. 단방향, 양방향
 *      - 테이블
 *          - 외래키 하나로 양쪽으로 조 가능
 *          - 사실 방향이라는 개념이 없음
 *      - 객체
 *          - 참조용 필드가 있 쪽으로만 참조가능
 *          - 한쪽만 참조하면 단방향
 *          - 양쪽이 서로 참조하면 양방향
 *  - 3. 연관관계의 주인
 *      - 테이블은 외래 키 하나 두 테이블의 관계를 맺음
 *      - 객체 양방향 관계는  A->B , B->A 처럼 참조가 2군데
 *      - 객체 양방향 관계는 참조가 2군데 있음, 둘중 테이블 외래키를 관리할 곳을 지정해야함.
 *      - 연관관계의 주인 : 외 키를 관리하는 참조
 *      - 주인의 반대편: 외래키의 영향을 주지 않음. 단순 조회만.
 *
 *  ##다대일 양방향 정리
 *  - 외래 키가 있는 쪽이 연관관계의 주인
 *  - 양쪽을 서로 참조하도록 개발
 *
 * # 일대다[1:N] (권장하지 않음 , 실무에서 잘 사용하지않음)
 * - 일대다 단방향 정리
 *  - 일대다 단방향은 일대다(1:N)에서 일(1)이 연관관계주인
 *  - 테이블 일대다 관계는 항상 다(N)쪽에 외래키가 있음.
 *  - 객체와 테이블의 차이 때문에 반대편 테이블의 외래 키를 관리하는 특이한 구조
 *  - @JoinColumn을  사용해야함. 그렇지 않으면 조인 테이블 방식을 사용(중간에 테이블을 하나 추가)
 *
 * - 일대다 단방향 매핑의 단점
 *  - 엔티티가 관리하는 외래 키가 다른 테이블에 있음.
 *  - 연관관계 관리를 위해 추가로 update SQl 실행
 * - 일대다 단방향 매핑 보다는 다대일 단방향 매핑을 사용하자.
 *
 * - * 일대다 양방향도 존재하는가?!
 *  - 공식적으로는 지원하지 않음.
 *  - @JoinColumn(insertable= false, updatable = false)
 *  - 읽기 전용 필드를 사용해서 양방향 처럼 사용하는 방법
 *  - 다대일 양방향을 사용하는게 좋음(일대다 양방향보단 다대일 양방향을 지양)
 *
 *
 */