package com.lyfen.pear.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 *
 * @author lyfen
 */
@Component
@ConfigurationProperties(prefix = "gen")
public class GenConfig {

    /**
     * 生成包路径
     */
    public static String packageName;

    /**
     * 表前缀(类名不会包含表前缀)
     */
    public static String tablePrefix;

    public static String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        GenConfig.packageName = packageName;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        GenConfig.tablePrefix = tablePrefix;
    }
}
