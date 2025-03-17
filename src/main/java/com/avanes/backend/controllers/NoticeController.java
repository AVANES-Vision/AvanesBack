package com.avanes.backend.controllers;

import com.avanes.backend.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    // todo please implement the following methods
//    @GetMapping("allNotices")
//    public String allNotices() {
//        return noticeService.getAllNotices;
//    }

}
