package com.bootcamp.guestbook.service;

import com.bootcamp.guestbook.dto.GuestbookDTO;
import com.bootcamp.guestbook.dto.PageRequestDTO;
import com.bootcamp.guestbook.dto.PageResultDTO;
import com.bootcamp.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService guestbookService;

    @Test
    public void testRegister() {
        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample title")
                .content("Sample contetnt")
                .writer("me")
                .build();

        System.out.println(guestbookService.register(guestbookDTO));
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = guestbookService.getList(pageRequestDTO);
        Assertions.assertNotNull(resultDTO);
    }
}