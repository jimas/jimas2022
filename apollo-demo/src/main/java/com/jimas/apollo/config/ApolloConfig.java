package com.jimas.apollo.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Date: 2022/10/11
 * Time: 23:24
 *
 * @author jimas
 */
@Configuration
@Slf4j
public class ApolloConfig {

    @Bean
    public Config getConfig() {
        Config config = ConfigService.getAppConfig();
        config.addChangeListener(changeEvent -> {
            log.info("Changes for namespace:{} ", changeEvent.getNamespace());
            //监听到key的value值变更，可以做相应的初始化处理
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                log.info(String.format("Found change - key: %s, oldValue: %s, newValue: %s, changeType: %s", change.getPropertyName(), change.getOldValue(), change.getNewValue(), change.getChangeType()));
            }
        });
        return config;
    }
}
