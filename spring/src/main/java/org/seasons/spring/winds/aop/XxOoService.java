package org.seasons.spring.winds.aop;

import org.seasons.spring.winds.aop.advisor.AOPClass;
import org.seasons.spring.winds.aop.advisor.AOPMethod;

/**
 * --描述
 *
 * @author wangk
 * @date 2022/3/9
 */
@AOPClass
public class XxOoService {

    @AOPMethod
    public void doXx () {
        System.out.println("doXx");
    }

    public void doOo () {
        System.out.println("doOo");
    }


}
