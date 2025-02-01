package com.example.bookmanager.Service;

import java.util.Map;

public interface LibraryConfigService {
    Map<String, String> getConfig();
    void setLoanMaxCount(int count);
    void setLoanDuration(int duration);
}
