package jpabook.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j //로그기능 사용
public class HomeController {

    @RequestMapping("/") //초기화면시 실행
    public String home(){
        log.info("home controller"); //home controller에 대한 로그가 찍힘.
        return "home"; //home.html로 찾아감
    }
}
