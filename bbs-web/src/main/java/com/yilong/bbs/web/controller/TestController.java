package com.yilong.bbs.web.controller;

import com.yilong.bbs.core.common.response.RespEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @RequestMapping("testException")
    public RespEntity testException() throws Exception {

        throw new Exception("测试异常");
    }
}
