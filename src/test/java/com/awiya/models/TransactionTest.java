package com.awiya.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.text.ParseException;
import java.util.Date;

import static com.awiya.models.Amount.amountOf;
import static com.awiya.utils.DateBuilder.date;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionTest {

    @Mock
    PrintStream printer;

    @Test
    public void should_print_credit_transaction() throws ParseException {
        Transaction transaction = new Transaction(amountOf(1000), date("10/01/2012"));

        transaction.printTo(printer, amountOf(1000));

        verify(printer).println("10/01/2012 | 1000,00  |          | 1000,00");
    }

    @Test
    public void should_print_debit_transaction() throws ParseException {
        Transaction transaction = new Transaction(amountOf(-2000), date("23/03/2023"));

        transaction.printTo(printer, amountOf(-2000));

        verify(printer).println("23/03/2023 |          | 2000,00  | -2000,00");
    }

    @Test
    public void should_calculate_current_balance_after_debit() throws ParseException {
        Transaction transaction = new Transaction(amountOf(2000), date("23/03/2023"));

        Amount currentValue = transaction.balanceAfterTransaction(amountOf(100));

        assertThat(currentValue, is(amountOf(2100)));
    }

    @Test
    public void should_calculate_current_balance_after_credit() throws ParseException {
        Transaction transaction = new Transaction(amountOf(-2000), date("23/03/2023"));

        Amount currentValue = transaction.balanceAfterTransaction(amountOf(100));

        assertThat(currentValue, is(amountOf(-1900)));
    }

    @Test
    public void should_be_equal_to_other_transaction_with_same_value_and_date() throws ParseException {
        Date depositDate = date("23/03/2023");
        Transaction depositOfOneHundred = new Transaction(amountOf(2000), depositDate);
        Transaction anotherDepositOfOneHundred = new Transaction(amountOf(2000), depositDate);

        assertThat(depositOfOneHundred, is(equalTo(anotherDepositOfOneHundred)));
    }

}
