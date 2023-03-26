package com.hasan.mapper;

import com.hasan.annotation.Column;

import java.math.BigDecimal;

public class SimpleRecord {

    @Column
    private String stringType;
    @Column
    private Integer integerType;
    @Column
    private Long longType;
    @Column
    private Float floatType;
    @Column
    private Double doubleType;
    @Column
    private BigDecimal bigDecimalType;
    @Column
    private Short shortType;
    @Column
    private Boolean booleanType;

    public String getStringType() {
        return stringType;
    }

    public void setStringType(String stringType) {
        this.stringType = stringType;
    }

    public Integer getIntegerType() {
        return integerType;
    }

    public void setIntegerType(Integer integerType) {
        this.integerType = integerType;
    }

    public Long getLongType() {
        return longType;
    }

    public void setLongType(Long longType) {
        this.longType = longType;
    }

    public Float getFloatType() {
        return floatType;
    }

    public void setFloatType(Float floatType) {
        this.floatType = floatType;
    }

    public Double getDoubleType() {
        return doubleType;
    }

    public void setDoubleType(Double doubleType) {
        this.doubleType = doubleType;
    }

    public BigDecimal getBigDecimalType() {
        return bigDecimalType;
    }

    public void setBigDecimalType(BigDecimal bigDecimalType) {
        this.bigDecimalType = bigDecimalType;
    }

    public Short getShortType() {
        return shortType;
    }

    public void setShortType(Short shortType) {
        this.shortType = shortType;
    }

    public Boolean getBooleanType() {
        return booleanType;
    }

    public void setBooleanType(Boolean booleanType) {
        this.booleanType = booleanType;
    }
}
