package com.awiya.models;

import java.io.PrintStream;

public class StatementLine {

    private final Transaction transaction;
    private final Amount currentBalance;

    public StatementLine(Transaction transaction, Amount currentBalance) {
        this.transaction = transaction;
        this.currentBalance = currentBalance;
    }

    public void printTo(PrintStream printer) {
        this.transaction.printTo(printer, currentBalance);
    }

}

