package com.jesse.learn.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ExportTypeEnum {
    TAX_DATA(1, "tax_data", "税务数据导出"),
    ACCOUNT_DATA(2, "account_data", "开户数据导出");

    private Integer value;
    private String name;
    private String desc;

    public static ExportTypeEnum parseByValue(Integer value) {
        return Arrays.stream(values()).filter(v -> Objects.equals(value, v.getValue()))
                .findFirst()
                .orElse(null);
    }



}
