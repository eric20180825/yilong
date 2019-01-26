package com.yilong.bbs.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("")
    public String index() {
        return "hello,this is bbs-core index.";
    }
    @RequestMapping("ping")
    public String ping() {
        return "pong";
    }
}
