package com.kwt.legalbuddy.model;

/**
 * Created by ASHU on 9/7/2015.
 */
public class NDAuser {
    public String discloser_type, discloser_name,company_signing_name,occupation, receiver_type, receiver_name,governing_law;
    public NDAuser(){
        company_signing_name="";
        occupation="";
        discloser_type ="Company";
        receiver_type="Company";
        discloser_name ="";
        receiver_name="";
        governing_law="";
    }
}
