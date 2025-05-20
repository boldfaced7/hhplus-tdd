package io.hhplus.tdd.point.adapter.config;

import io.hhplus.tdd.point.adapter.in.web.ChargeUserPointController;
import io.hhplus.tdd.point.adapter.in.web.UseUserPointController;
import io.hhplus.tdd.point.application.service.ChargeUserPointServiceWithEvent;
import io.hhplus.tdd.point.application.service.UseUserPointServiceWithEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public ChargeUserPointController chargeUserPointController(
            ChargeUserPointServiceWithEvent chargeUserPointServiceWithEvent
    ) {
        return new ChargeUserPointController(chargeUserPointServiceWithEvent);
    }

    @Bean
    public UseUserPointController useUserPointController(
            UseUserPointServiceWithEvent useUserPointServiceWithEvent
    ) {
        return new UseUserPointController(useUserPointServiceWithEvent);
    }
}
