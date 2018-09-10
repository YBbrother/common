package com.myproject.common.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum TableOfSheetEnum {
    TBAA("group_base_data", "集团注册信息"),
    TBBA("non_company", "投资非金融机构信息"),
    TBCA("holding_company", "控股金融机构信息"),
    TBDA("group_partner", "集团股东结构"),
    TBEA("group_manger", "集团高管信息"),
	TBFA("group_rule", "集团治理主要制度"),
	TBGA("financial_equity", "对金融机构的股权"),
	TBHA("financial_manger", "集团高管信息"),
	TBIA("risk_info", "集团全面风险管理概况"),
	TBJA("firewall_info", "风险管理的防火墙设置"),
	TBKA("rule_info", "全面风险管理相关制度"),
	TBLA("capital_info", "集团资本补充机制");

    private final  String value;
    private  final String label;
    
    TableOfSheetEnum(String value, String label){
    	this.value=value;
        this.label=label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    } 

    public static String getLabel(String value){
        if (TBAA.getValue().equals(value)){
            return TBAA.getLabel();
        } else if (TBBA.getValue().equals(value)){
            return TBBA.getLabel();
        }else if (TBCA.getValue().equals( value)) {
            return TBCA.getLabel();
        } else if (TBDA.getValue().equals(value)){
            return TBDA.getLabel();
        } else if(TBEA.getValue().equals(value)){
            return TBEA.getLabel();
        } else if(TBFA.getValue().equals(value)){
            return TBFA.getLabel();
        } else if(TBGA.getValue().equals(value)){
            return TBGA.getLabel();
        } else if(TBHA.getValue().equals(value)){
            return TBHA.getLabel();
        } else if(TBIA.getValue().equals(value)){
            return TBIA.getLabel();
        } else if(TBJA.getValue().equals(value)){
            return TBJA.getLabel();
        } else if(TBKA.getValue().equals(value)){
            return TBKA.getLabel();
        } else if(TBLA.getValue().equals(value)){
            return TBLA.getLabel();
        } else
            return null;
    }
    
    public static String getValue(String Label){
        if (TBAA.getLabel().equals(Label)) {
            return TBAA.getValue();
        } else if (TBBA.getLabel().equals(Label)) {
            return TBBA.getValue();
        } else if (TBCA.getLabel().equals(Label)) {
            return TBCA.getValue();
        } else if (TBDA.getLabel().equals(Label)) {
            return TBDA.getValue();
        } else if(TBEA.getLabel().equals(Label)) {
            return TBEA.getValue();
        } else if(TBFA.getLabel().equals(Label)) {
            return TBFA.getValue();
        } else if(TBGA.getLabel().equals(Label)) {
            return TBGA.getValue();
        } else if(TBHA.getLabel().equals(Label)) {
            return TBHA.getValue();
        } else if(TBIA.getLabel().equals(Label)) {
            return TBIA.getValue();
        } else if(TBJA.getLabel().equals(Label)) {
            return TBJA.getValue();
        } else if(TBKA.getLabel().equals(Label)) {
            return TBKA.getValue();
        } else if(TBLA.getLabel().equals(Label)) {
            return TBLA.getValue();
        } else
            return null;
    }

    public static Map<String,String> getlogTypeMap(){
        Map<String,String> map =new LinkedHashMap<String, String>();
        for (TableOfSheetEnum logType : TableOfSheetEnum.values()){
            map.put(logType.value,logType.label);
        }
        return  map;
    }
}
