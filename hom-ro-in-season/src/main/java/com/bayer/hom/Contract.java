package com.bayer.hom;

import java.util.Objects;

public class Contract {
    private String contractNumber;
    private String plantNumber;
    private String year;
    private String fieldNumber;
    private String growerName;
    private String acronym;
    private String trackingNumber;
    private String lastHarvestReceiptDate;
    private String contractLineDeleteFlag;
    private double harvestedFemaleArea;
    private double moisturePercentage;

    public Contract() {
    }

    public Contract(String contractNumber, String plantNumber, String year, String fieldNumber, String growerName, String acronym, String trackingNumber, String lastHarvestReceiptDate, String contractLineDeleteFlag, double harvestedFemaleArea, double moisturePercentage) {
        this.contractNumber = contractNumber;
        this.plantNumber = plantNumber;
        this.year = year;
        this.fieldNumber = fieldNumber;
        this.growerName = growerName;
        this.acronym = acronym;
        this.trackingNumber = trackingNumber;
        this.lastHarvestReceiptDate = lastHarvestReceiptDate;
        this.contractLineDeleteFlag = contractLineDeleteFlag;
        this.harvestedFemaleArea = harvestedFemaleArea;
        this.moisturePercentage = moisturePercentage;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getPlantNumber() {
        return this.plantNumber;
    }

    public void setPlantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
    }

    public String getYear() {
        return this.year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFieldNumber() {
        return this.fieldNumber;
    }

    public void setFieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public String getGrowerName() {
        return this.growerName;
    }

    public void setGrowerName(String growerName) {
        this.growerName = growerName;
    }

    public String getAcronym() {
        return this.acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getTrackingNumber() {
        return this.trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getLastHarvestReceiptDate() {
        return this.lastHarvestReceiptDate;
    }

    public void setLastHarvestReceiptDate(String lastHarvestReceiptDate) {
        this.lastHarvestReceiptDate = lastHarvestReceiptDate;
    }

    public String getContractLineDeleteFlag() {
        return this.contractLineDeleteFlag;
    }

    public void setContractLineDeleteFlag(String contractLineDeleteFlag) {
        this.contractLineDeleteFlag = contractLineDeleteFlag;
    }

    public double getHarvestedFemaleArea() {
        return this.harvestedFemaleArea;
    }

    public void setHarvestedFemaleArea(double harvestedFemaleArea) {
        this.harvestedFemaleArea = harvestedFemaleArea;
    }

    public double getMoisturePercentage() {
        return this.moisturePercentage;
    }

    public void setMoisturePercentage(double moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
    }

    public Contract contractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
        return this;
    }

    public Contract plantNumber(String plantNumber) {
        this.plantNumber = plantNumber;
        return this;
    }

    public Contract year(String year) {
        this.year = year;
        return this;
    }

    public Contract fieldNumber(String fieldNumber) {
        this.fieldNumber = fieldNumber;
        return this;
    }

    public Contract growerName(String growerName) {
        this.growerName = growerName;
        return this;
    }

    public Contract acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public Contract trackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public Contract lastHarvestReceiptDate(String lastHarvestReceiptDate) {
        this.lastHarvestReceiptDate = lastHarvestReceiptDate;
        return this;
    }

    public Contract contractLineDeleteFlag(String contractLineDeleteFlag) {
        this.contractLineDeleteFlag = contractLineDeleteFlag;
        return this;
    }

    public Contract harvestedFemaleArea(double harvestedFemaleArea) {
        this.harvestedFemaleArea = harvestedFemaleArea;
        return this;
    }

    public Contract moisturePercentage(double moisturePercentage) {
        this.moisturePercentage = moisturePercentage;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Contract)) {
            return false;
        }
        Contract contract = (Contract) o;
        return Objects.equals(contractNumber, contract.contractNumber) && Objects.equals(plantNumber, contract.plantNumber) && Objects.equals(year, contract.year) && Objects.equals(fieldNumber, contract.fieldNumber) && Objects.equals(growerName, contract.growerName) && Objects.equals(acronym, contract.acronym) && Objects.equals(trackingNumber, contract.trackingNumber) && Objects.equals(lastHarvestReceiptDate, contract.lastHarvestReceiptDate) && Objects.equals(contractLineDeleteFlag, contract.contractLineDeleteFlag) && harvestedFemaleArea == contract.harvestedFemaleArea && moisturePercentage == contract.moisturePercentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contractNumber, plantNumber, year, fieldNumber, growerName, acronym, trackingNumber, lastHarvestReceiptDate, contractLineDeleteFlag, harvestedFemaleArea, moisturePercentage);
    }

    @Override
    public String toString() {
        return "{" +
            " contractNumber='" + getContractNumber() + "'" +
            ", plantNumber='" + getPlantNumber() + "'" +
            ", year='" + getYear() + "'" +
            ", fieldNumber='" + getFieldNumber() + "'" +
            ", growerName='" + getGrowerName() + "'" +
            ", acronym='" + getAcronym() + "'" +
            ", trackingNumber='" + getTrackingNumber() + "'" +
            ", lastHarvestReceiptDate='" + getLastHarvestReceiptDate() + "'" +
            ", contractLineDeleteFlag='" + getContractLineDeleteFlag() + "'" +
            ", harvestedFemaleArea='" + getHarvestedFemaleArea() + "'" +
            ", moisturePercentage='" + getMoisturePercentage() + "'" +
            "}";
    }

}
