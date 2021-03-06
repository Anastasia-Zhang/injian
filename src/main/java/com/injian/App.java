package com.injian;

import com.injian.dao.UserDOMapper;
import com.injian.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

@SpringBootApplication(scanBasePackages = {"com.injian"})
@RestController//相当于spring的controller
@MapperScan("com.injian.dao")
public class App 
{
    @Autowired(required = false)
    private UserDOMapper userDOMapper;

    @RequestMapping("/injian")
    public String home(){
        return "hello welcome";
        //UserDO userDO = userDOMapper.selectByPrimaryKey(1);
//        if (userDO == null){
//            return "用户对象不存在";
//        }else
//            return userDO.getUsername();
    }

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class);
    }
}
