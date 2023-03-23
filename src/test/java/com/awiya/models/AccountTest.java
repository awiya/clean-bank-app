package com.awiya.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;

import static com.awiya.builders.TransactionBuilder.aTransaction;
import static com.awiya.models.Amount.amountOf;
import static com.awiya.utils.DateBuilder.date;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AccountTest {

    @Mock
    private Statement statement;
    private Account account;

    @Before
    public void initialise() {
        account = new Account(statement);
    }

    @Test
    public void should_add_deposit_line_to_statement() throws ParseException {
        Date depositDate = date("20/03/2023");
        Amount depositAmount = amountOf(1000);

        account.credit(depositAmount, depositDate);

        verify(statement).addLineContaining(aTransaction()
                        .with(depositDate)
                        .with(depositAmount).build(),
                currentBalanceEqualsTo(depositAmount));
    }

    @Test
    public void should_add_withdraw_line_to_statement() throws ParseException {
        Date withdrawalDate = date("21/03/2023");

        account.debit(amountOf(500), withdrawalDate);

        verify(statement).addLineContaining(aTransaction()
                        .with(amountOf(-500))
                        .with(withdrawalDate).build(),
                amountOf(-500));
    }

    @Test
    public void should_print_statement() {
        PrintStream printer = System.out;

        account.printStatement(printer);

        verify(statement).printTo(printer);
    }

    private Amount currentBalanceEqualsTo(Amount amount) {
        return amount;


    }
}