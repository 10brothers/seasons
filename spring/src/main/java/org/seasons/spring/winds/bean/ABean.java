package org.seasons.spring.winds.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/20
 */
public class ABean {

    @Value("${aa.bb.cc}")
    private String string;


}
