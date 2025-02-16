package com.example.bookmanager.Service.impl;

import com.example.bookmanager.Annotation.LogRecord;
import com.example.bookmanager.Config.LibraryConfig;
import com.example.bookmanager.Entity.LibraryConfigEntity;
import com.example.bookmanager.Service.LibraryConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryConfigServiceImpl implements LibraryConfigService {
    private final LibraryConfig libraryConfig;

    public LibraryConfigServiceImpl(LibraryConfig libraryConfig) {
        this.libraryConfig = libraryConfig;
    }

    @Override
    public List<LibraryConfigEntity> getConfig() {
        return libraryConfig.getConfigMapper().getConfig();
    }

    @LogRecord
    @Override
    public void setLoanMaxCount(int count) {
        libraryConfig.setConfigByKey("loanMaxCount", String.valueOf(count));
    }

    @LogRecord
    @Override
    public void setLoanDuration(int duration) {
        libraryConfig.setConfigByKey("loanDuration", String.valueOf(duration));
    }

    @LogRecord
    @Override
    public void setMaxRenewTimes(Integer value) {
        libraryConfig.setConfigByKey("maxRenewTimes", String.valueOf(value));
    }
}
