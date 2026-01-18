package Revision.SplitWise;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        User u1 = new User("1","A");
        User u2 = new User("2","B");
        User u3 = new User("3","C");
        Groups g1 = new Groups("g1","Trip1", Arrays.asList(u1,u2,u3));
        Expense expense = new Expense(u1,300,g1);
        BalanceSheetService service = new BalanceSheetService();
        SplitStrategy splitStrategy = new EqualSplit();

        service.addExpense(splitStrategy,expense);
        service.showBalance();
    }
}
