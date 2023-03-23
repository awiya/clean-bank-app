package com.awiya;

import com.awiya.models.Account;
import com.awiya.models.Statement;

import java.text.ParseException;

import static com.awiya.models.Amount.amountOf;
import static com.awiya.utils.DateBuilder.date;

public class App {

    public static void main(String[] args) throws ParseException {
        Account account = new Account(new Statement());

        account.credit(amountOf(1000), date("19/03/2023"));
        account.credit(amountOf(2000), date("20/03/2023"));
        account.debit(amountOf(500), date("23/03/2023"));

        account.printStatement(System.out);
    }

}
