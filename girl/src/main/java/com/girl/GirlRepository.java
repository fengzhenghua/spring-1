package com.girl;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fzh on 2017/7/23.
 */
public interface GirlRepository extends JpaRepository<Girl,Integer>{

    public List<Girl> findByAge(String age);

}
