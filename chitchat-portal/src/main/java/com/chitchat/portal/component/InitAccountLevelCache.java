package com.chitchat.portal.component;

import com.chitchat.portal.service.account.AccountLevelServiceI;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * 容器启动完成时加载用户等级至Redis缓存
 *
 * Created by Js on 2022/11/21.
 */
//@Component
@RequiredArgsConstructor
@Slf4j
public class InitAccountLevelCache implements CommandLineRunner {

    private final AccountLevelServiceI accountLevelServiceI;

    /**
     * 初始化用户等级信息
     *
     * @param args
     */
    @Override
    public void run(String... args) {
        log.info(">>>>>>>>>>> init Account Level Info.");
        accountLevelServiceI.refreshAccountLevel();
    }
}
