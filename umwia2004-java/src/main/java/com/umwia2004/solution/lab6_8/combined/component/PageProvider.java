package com.umwia2004.solution.lab6_8.combined.component;

import com.umwia2004.solution.lab6_8.combined.domain.Page;

public class PageProvider {
    public Page providePage(String vpn) {
        return new Page(vpn, vpn);
    }
}
