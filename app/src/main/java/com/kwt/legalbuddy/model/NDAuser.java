package com.kwt.legalbuddy.model;

/**
 * Created by ASHU on 9/7/2015.
 */
public class NDAuser {
    public String first_party_type, first_party_name,first_party_occupation, second_party_type, second_party_name,second_party_occupation;
    public String governing_law,effective_date,closing_date;
    public  String signing_date,signing_first,signing_second,agreement_terminated;
    public String software_shared,info_shared_among_company,make_copies,timeline,future_buiness;
    public NDAuser(){
        first_party_occupation="CEO";
        first_party_type ="Company";
        second_party_type="Company";
        first_party_name ="Mr. Ashutosh Barthwal";
        second_party_name="Mr. Android App";
        second_party_occupation="CEO";
        governing_law="India";
        effective_date="09/09/2015";
        software_shared="1";
        info_shared_among_company="1";
        make_copies="1";
        timeline="1";
        closing_date="00/00/0000";
        signing_date="09/12/2015";
        signing_first="Mr. Ashutosh Barthwal";
        signing_second="Mr. John Doe";
        future_buiness="1";
        agreement_terminated="discloser only";
    }
}
