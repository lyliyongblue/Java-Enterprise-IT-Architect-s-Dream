package com.yong.course.edge.api.web;

import com.yong.course.provider.IHelloService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @DubboReference(version = "1.0.0")
    private IHelloService helloService;

    /**
     * 测试URL:  http://localhost:8081/hello?name=%E6%9D%8E%E5%8B%87
     */
    @GetMapping("/hello")
    public ResponseEntity<String> hello(String name) {
        return ResponseEntity.ok(helloService.sayHello(name));
    }
}
