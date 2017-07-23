package com.girl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fzh on 2017/7/23.
 */
@RestController
public class TestController {

//    @Value("${cupSize}")
//    private String cupSize;
//
//    @Value("${content}")
//    private String content;
//    @Autowired
//    private GirlProperties girlProperties;

    @Autowired
    private GirlRepository girlRepository;

    @RequestMapping(value = "girls" ,method = RequestMethod.GET)
    public List<Girl> getGirlList(){
        List girls =  girlRepository.findAll();
        return girls;
    }

    @RequestMapping(value = "insertGirl" ,method = RequestMethod.POST)
//    public Girl insertGirl(@RequestParam("cupSize") String cupSize, @RequestParam("age") String age){
    public Girl insertGirl(String cupSize, String age){
        Girl girl = new Girl();
        girl.setCupSize(cupSize);
        girl.setAge(age);
        return girlRepository.save(girl);

    }
    @RequestMapping(value = "getGirls" ,method = RequestMethod.POST)
    public List<Girl> getGirls (String age){

        List<Girl> girls = girlRepository.findByAge(age);
        return girls;
    }
}
