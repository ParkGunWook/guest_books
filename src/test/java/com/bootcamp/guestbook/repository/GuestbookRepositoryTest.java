package com.bootcamp.guestbook.repository;

import com.bootcamp.guestbook.entity.Guestbook;
import com.bootcamp.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummiess() {
        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...." + i)
                    .content("Content..." + i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void auditingTest() {
        Guestbook guestbook = guestbookRepository.save(Guestbook.builder()
                .content("audit")
                .title("audit2")
                .writer("me")
                .build());
        Guestbook guestbook1 = Guestbook.builder()
                .gno(guestbook.getGno())
                .title(guestbook.getTitle())
                .content("modified")
                .writer(guestbook.getWriter())
                .build();
        guestbook1 = guestbookRepository.save(guestbook1);
        Assertions.assertNotEquals(guestbook.getModDate(), guestbook1.getModDate());
        guestbookRepository.delete(guestbook1);
    }

    @Test
    public void testQuery1() {
        PageRequest gno = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression);

        Page<Guestbook> result = guestbookRepository.findAll(builder, gno);

        result.stream().forEach(System.out::println);
    }

    @Test
    public void testQuery2() {
        PageRequest gno = PageRequest.of(0, 10, Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, gno);

        result.stream().forEach(System.out::println);
    }
}