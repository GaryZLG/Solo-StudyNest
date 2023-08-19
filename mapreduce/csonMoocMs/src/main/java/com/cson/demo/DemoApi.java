package com.cson.demo;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 本代码来自 Cspiration，由 @Cspiration 提供
 * - 讲师：Edward Shi
 * - 官方网站：https://cspiration.com
 * - 版权所有，禁止转发和分享
 */
@RestController
public class DemoApi {

    @GetMapping("/demoGet")
    public Long demoGet(@RequestParam Long id) {
        return id;
    }

    @PostMapping("demoPost")
    public Map<String, Object> demoPost(@RequestBody Map<String, Object> param) {
        return param;
    }

    @GetMapping("/timeout")
    public String timeout(@RequestParam Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "熔断测试";
    }
}

