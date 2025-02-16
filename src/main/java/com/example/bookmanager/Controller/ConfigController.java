package com.example.bookmanager.Controller;

import com.example.bookmanager.Annotation.RequireAdmin;
import com.example.bookmanager.Exception.BusinessException;
import com.example.bookmanager.Service.LibraryConfigService;
import com.example.bookmanager.Utils.Result;
import com.example.bookmanager.Utils.ResultData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    private final LibraryConfigService libraryConfigService;
    public ConfigController(LibraryConfigService libraryConfigService) {
        this.libraryConfigService = libraryConfigService;
    }

    @RequireAdmin
    @GetMapping
    public Result getConfig() {
        try {
            return ResultData.success("Get config successfully", libraryConfigService.getConfig());
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to get config: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PostMapping("/loanDuration")
    public Result updateLoanDuration(@RequestParam Integer value) {
        try {
            libraryConfigService.setLoanDuration(value);
            return Result.success("Update loan duration successfully");
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to update loan duration: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PostMapping("/loanMaxCount")
    public Result updateLoanMaxCount(@RequestParam Integer value) {
        try {
            libraryConfigService.setLoanMaxCount(value);
            return Result.success("Update loan max count successfully");
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to update loan max count: " + e.getMessage());
        }
    }

    @RequireAdmin
    @PostMapping("/maxRenewTimes")
    public Result updateMaxRenewTimes(@RequestParam Integer value) {
        try {
            libraryConfigService.setMaxRenewTimes(value);
            return Result.success("Update max renew times successfully");
        } catch (Exception e) {
            throw new BusinessException(4, 400, "Failed to update max renew times: " + e.getMessage());
        }
    }
}
