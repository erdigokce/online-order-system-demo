package com.onlineordersystem.schduler;

import com.onlineordersystem.domain.Seller;
import com.onlineordersystem.model.LoginRequestDTO;
import com.onlineordersystem.service.LoginService;
import com.onlineordersystem.service.OrderService;
import com.onlineordersystem.service.SellerService;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DeliveryScheduler {

    private final OrderService orderService;
    private final SellerService sellerService;
    private final LoginService loginService;

    @Setter
    @Value("${oos.system.user.name}")
    private String systemUsername;
    @Setter
    @Value("${oos.system.user.password}")
    private String systemPassword;

    @Autowired
    public DeliveryScheduler(OrderService orderService, SellerService sellerService, LoginService loginService) {
        this.orderService = orderService;
        this.sellerService = sellerService;
        this.loginService = loginService;
    }

    @Scheduled(fixedDelay = 1000 * 60L) // Per minute
    public void deliverOrders() {
        loginWithSystemUser();
        orderService.deliverOrders();
        loginService.logoutUser();
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * TUE-SAT") // Next midnight of every week-day
    public void generateEndOfDayReport() {
        loginWithSystemUser();
        List<Seller> allActiveSellers = sellerService.findAll();
        allActiveSellers.forEach(seller -> orderService.generateReport(seller.getId()));
        loginService.logoutUser();
    }

    private void loginWithSystemUser() {
        LoginRequestDTO loginInfo = new LoginRequestDTO();
        loginInfo.setEmail(systemUsername);
        loginInfo.setPassword(systemPassword);
        loginService.authenticateUser(loginInfo);
    }
}
