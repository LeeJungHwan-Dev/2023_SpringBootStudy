package com.shop.shop.repository;

import com.shop.shop.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    /**
     * JPA
     * 기존 SQL문을 사용하면 특정 DB에 종속된다.
     * 하지만 JPA를 사용하면 findBy아이템 이름 조건 등과 같이 JPQL snippet을 참고해
     * DB에 종속되지 않는 추상화 된 객체지향 쿼리 언어를 작성할 수 있다.
     * */
    List<Item> findByItemNm(String itemNm);
    //itemNm이랑 같은 리스트를 조회한다.
    List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
    //상품을 상품명과 상품 상세 설명을 OR 조건을 이용하여 조회하는 쿼리 메소드
    //ItemNm을 조회하거나, ItemDetail을 조회해준다.

    List<Item> findByPriceLessThan(Integer price);
    //LessThan은 비교군 보다 작은 아이템을 리스트에 담아준다.

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
    //가격을 내림차순으로 정렬해서 조회한다.

    List<Item> findByPriceLessThanOrderByPriceAsc(Integer price);
    //가격을 오름차순으로 정렬해서 조회한다.


    /**
     * Spring DATA JPA Query
     * 해당 어노테이션을 사용하면 여러 조건이 추가되어 이름이 길어지는 JPA를 보다 보기 좋게 작성할 수 있다.
     * 해당 부분을 좀 더 응용하면 (value ="sql", nativeQuery = true)를 사용하면 기존 DB에서 사용하는 쿼리를 사용할 수 있다.
     * */

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
    // 쿼리를 바탕으로 Param으로 받은 itemDetail이 기존 테이블의 데이터에 포함되어 있으면 가격의 내림차순으로 조회한다.


    @Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
    List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
    //네이티브 SQL 코드를 사용하는 방식이지만 특정 DB에 종속된다.
    //보통은 복잡한 통계를 해야하는 쿼리에 사용된다.
}
