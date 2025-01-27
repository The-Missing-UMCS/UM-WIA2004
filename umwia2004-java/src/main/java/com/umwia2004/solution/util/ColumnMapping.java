package com.umwia2004.solution.util;

import lombok.Getter;

@Getter
public class ColumnMapping {
    private final String fieldName;
    private final String header;

    private ColumnMapping(String fieldName, String header) {
        this.fieldName = fieldName;
        this.header = header;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String fieldName;
        private String header;

        public Builder forField(String fieldName) {
            this.fieldName = fieldName;
            return this;
        }

        public Builder withHeader(String header) {
            this.header = header;
            return this;
        }

        public ColumnMapping build() {
            return new ColumnMapping(fieldName, header);
        }
    }
}