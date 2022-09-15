package com.a_apps.cocoberry;


public class constructorForDatabase {

    String orderNo, TDate,Total;


    public constructorForDatabase () {


        this.orderNo = orderNo;
        this.TDate = TDate;
        this.Total=Total;


    }



    public String getOrderNo(){
        return orderNo;

    }
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }



    public String getTDate(){
        return TDate;

    }
    public void setTDate(String TDate){
        this.TDate = TDate;
    }



    public String getTotal(){
        return Total;

    }
    public void setTotal(String Total){
        this.Total = Total;
    }





}
