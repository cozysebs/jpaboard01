package org.jyr.jpademo.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.jyr.jpademo.domain.Board;
import org.jyr.jpademo.domain.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {
    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public Page<Board> search1(Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query = from(qboard);
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(qboard.title.containsIgnoreCase("1"));   // or title like '%1%'
        builder.or(qboard.content.containsIgnoreCase("1")); // or content like '%1%'
        builder.or(qboard.author.containsIgnoreCase("1"));
        query.where(builder);
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<Board>(list, pageable, count);
    }

    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        QBoard qboard = QBoard.board;
        JPQLQuery<Board> query = from(qboard);

        if(types!=null && types.length>0 && keyword!=null){
            BooleanBuilder builder = new BooleanBuilder();
            for (String type : types) {
                switch(type){
                    case "t":
                        builder.or(qboard.title.containsIgnoreCase(keyword));
                        break;
                    case "c":
                        builder.or(qboard.content.containsIgnoreCase(keyword));
                        break;
                    case "w":
                        builder.or(qboard.author.containsIgnoreCase(keyword));
                }
            }
            query.where(builder);
        }
        query.where(qboard.bno.gt(0));
        this.getQuerydsl().applyPagination(pageable, query);
        List<Board> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<>(list, pageable, count);
    }
}
