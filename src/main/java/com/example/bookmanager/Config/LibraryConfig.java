package com.example.bookmanager.Config;

import com.example.bookmanager.Mapper.LibraryConfigMapper;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LibraryConfig {
    @Getter
    private final Map<String, String> configMap = new HashMap<>();
    private final LibraryConfigMapper configMapper;

    public LibraryConfig(LibraryConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @PostConstruct
    public void init() {
        configMapper.getConfig().forEach(config -> configMap.put(config.getKey(), config.getValue()));
    }

    public String getConfigValue(String key) {
        return configMap.get(key);
    }

    public void setConfigByKey(String key, String value) {
        configMap.put(key, value);
        configMapper.updateConfig(key, value);
    }

    public int getLoanDurationDays() {
        return Integer.parseInt(getConfigValue("loanDuration"));
    }

    public int getLoanMaxCount() {
        return Integer.parseInt(getConfigValue("loanMaxCount"));
    }

    public int getMaxRenewTimes() {
        return Integer.parseInt(getConfigValue("maxRenewTimes"));
    }

    public int getRenewPriorDays() {
        return Integer.parseInt(getConfigValue("renewPriorDays"));
    }

    public int getMaxReserveCount() {
        return Integer.parseInt(getConfigValue("maxReserveCount"));
    }
}
