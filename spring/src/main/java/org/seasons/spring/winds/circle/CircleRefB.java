package org.seasons.spring.winds.circle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CircleRefB {

    private CircleRefA circleRefA;

    @Autowired
    public void setCircleRefA(CircleRefA circleRefA) {
        this.circleRefA = circleRefA;
    }
}
