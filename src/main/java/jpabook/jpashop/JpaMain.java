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
 * # 일대일 관계
 * - 일대일 관계는 그 반대도 일대일
 * - 주 테이블이나 대상 테이블 중에 외래키 선택 가능
 *  - 주 테이블에 외래키
 *  - 대상 테이블에 외래키
 * - 외래 키에 데이터 베이스 유니크 제약조건 추가
 *
 * ## 일대일 : 주이블에 외래키 양방향 정리
 * - 다대일 양방향 매핑 처럼 외래키가 있는곳이 연관관계의 주인
 * - 반대편은 mappedBy 적용
 *
 * ##일대일 정리
 * - 주 테이블에 외래키
 *  - 주 객체가 대상 객채의 참조를 가지는것 처럼 주 테이블에 외래키를 두고 대상 테이블을 찾음
 *  - 객체지향 개발자 선호
 *  - JPA 매핑 관리
 *  - 장점 : 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능
 *  - 단점 : 값이 없으면 외래키에 Null허용
 * - 대상 테이블에 외래키
 *  - 대상 테이블에 외래 키가 존재
 *  - 전통적인 데이터베이스 개발자선호
 *  - 장점 : 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할때 테이블 구조 유지
 *  - 단점 : 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시로딩 됨.
 *
 *
 * # 다대다 [N:M] (권장하지않음, 실무에서 거의 사용 X)
 * - 관계형 데이터베이스는 정규화된 테이블 2개로 다대다 관계를 표현할수 없음.
 * - 연결 테이블을 추가하여 일대다, 다대일 관계로 풀어내야함.
 * - 객체는 컬렉션을 사용해서 객체2개로 다대다 관계 가능
 *
 *
 * ## 다대다 매핑의 한계
 * - 편리해보이지만 실무에서 사용 X
 * - 연결 테이블이 단순히 연결만 하고 끝나지 않음
 * - 주문시간, 수량 같은 데이터가 들어올 수 있음
 *
 * ## 다대다 한계 극복
 * - 연결 테이블용 엔티티 추가 (연결테이블을 엔티티로 승격)
 * - @ManyToMany -> @OneToMany , @ManyToOne
 *
 * */