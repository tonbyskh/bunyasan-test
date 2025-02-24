package com.bunyasan.test.crm.constants;


public enum RequestType {
    BANK_ACCOUNT_DETAILS("001", "เรียกดูรายละเอียดบัญชี"),
    BANK_ACCOUNT_LIST("002", "เรียกดูรายการบัญชีทั้งหมดของลูกค้า"),
    TRANSACTION_HISTORY("003", "เรียกดูประวัติการทํารายการบัญชีของลูกค้า"),
    OTHER("000", "อื่นๆ");
    public final String code;
    public final String label;
    RequestType(String code, String label) {
        this.code = code;
        this.label = label;
    }
}
