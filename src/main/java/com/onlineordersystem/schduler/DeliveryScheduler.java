package com.onlineordersystem.schduler;

import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.service.LoginService;
import com.onlineordersystem.service.OrderService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Profile("deliverOrderTask")
@Component
public class DeliveryScheduler {

    private final OrderService orderService;
    private final LoginService loginService;

    @Setter
    @Value("${oos.system.user.name}")
    private String systemUsername;
    @Setter
    @Value("${oos.system.user.password}")
    private String systemPassword;

    @Autowired
    public DeliveryScheduler(OrderService orderService, LoginService loginService) {
        this.orderService = orderService;
        this.loginService = loginService;
    }

    @Scheduled(fixedDelay = 1000 * 60L) // Per minute
    public void deliverOrders() {
        loginWithSystemUser();
        orderService.deliverOrders();
        loginService.logoutUser();
    }

    private void loginWithSystemUser() {
        LoginRequestDTO loginInfo = new LoginRequestDTO();
        loginInfo.setEmail(systemUsername);
        loginInfo.setPassword(systemPassword);
        loginService.authenticateUser(loginInfo);
    }
}
