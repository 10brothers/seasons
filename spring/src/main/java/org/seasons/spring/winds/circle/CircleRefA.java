package org.seasons.spring.winds.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircleRefA {


    private CircleRefB circleRefB;

    @Autowired
    public void setCircleRefB(CircleRefB circleRefB) {
        this.circleRefB = circleRefB;
    }
}
