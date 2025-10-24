package org.jyr.jpademo.repository;

import org.jyr.jpademo.domain.Board;
import org.jyr.jpademo.repository.search.BoardSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {
    //@Query("select b from Board b where b.title like concat('%',:keyword, '%')or b.content like concat('%', :keyword, '%')")
    //select b의 내용 다. Board는 기니까 b라고 부를게
    //List<Board> findByTitleAndContent(String keyword);
    //테이블 이름을 안써. 엔티티의 클레스 이름을 써. 나중에 쿼리 dsl이라는 걸 써서 간편하게 쓴데

    //Domain page로 import pageable도 domain
    @Query("select b from Board b where b.title like concat('%',:keyword, '%') or b.content like concat('%', :keyword, '%')")
    Page<Board> findKeyword(String keyword, Pageable pageable);

}
