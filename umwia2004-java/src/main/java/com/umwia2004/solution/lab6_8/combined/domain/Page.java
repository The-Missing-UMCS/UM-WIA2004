package com.umwia2004.solution.lab6_8.combined.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Page {
    public static final int NOT_REFERENCED = 0;
    public static final int REFERENCED = 1;

    private final String vpn;       // Virtual Page Number
    private final String content;
    private int accessBit;
}
