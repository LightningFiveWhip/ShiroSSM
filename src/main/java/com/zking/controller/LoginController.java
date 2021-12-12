package com.zking.controller;

import com.zking.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping("/goLogin")
    public String goLogin(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(User user, HttpSession session){
        String msg="";
        int code=1;
        Map<String,Object> map=new HashMap<>();
        Subject subject= SecurityUtils.getSubject();
        UsernamePasswordToken upt=new UsernamePasswordToken(
                user.getUsername(),user.getPassword()
        );
        try {
            subject.login(upt);
        } catch (UnknownAccountException e1){
           msg="账号不存在";
           code=-1;
        }
        catch (AuthenticationException e2) {
            msg="密码错误";
            code=0;
        }
        map.put("code",code);
        map.put("msg",msg);
        session.setAttribute("map",map);
        if(code<1){
            return "login";
        }else{
            return "main";
        }
    }
}
