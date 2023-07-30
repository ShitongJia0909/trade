package com.shangan.trade.goods.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class GoodsController {

    @GetMapping("/goods/test")
    public String test(){
        log.info("test...");
        return "hello world";
    }

}
