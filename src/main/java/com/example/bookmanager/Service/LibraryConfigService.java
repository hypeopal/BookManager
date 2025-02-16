package com.example.bookmanager.Service;

import com.example.bookmanager.Entity.LibraryConfigEntity;

import java.util.List;

public interface LibraryConfigService {
    List<LibraryConfigEntity> getConfig();
    void setLoanMaxCount(int count);
    void setLoanDuration(int duration);
    void setMaxRenewTimes(Integer value);
}
