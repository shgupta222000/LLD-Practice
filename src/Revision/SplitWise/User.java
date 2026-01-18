package Revision.SplitWise;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Entities -> enum -> interface -> implementation-> Services-> Driver
public class User {
    String userID;
    String Name;

    public User(String userID, String Name) {
        this.userID = userID;
        this.Name = Name;
    }
    public String getUserID() {
        return userID;
    }
    public String getName() {
        return Name;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

}

class Groups {
    String groupID;
    String groupName;

    List<User>users;
    public Groups(String groupID, String groupName, List<User>users) {
        this.groupID = groupID;
        this.groupName = groupName;
        this.users = users;
    }
    public List<User> getUsers() {
        return users;
    }
}

class Expense {
    User paidBy;
    double paidAmount;
    Groups group;
    public Expense(User paidBy, double paidAmount, Groups group){
        this.paidBy = paidBy;
        this.paidAmount = paidAmount;
        this.group = group;
    }
    public User getPaidBy(){
        return paidBy;
    }
    public double getpaidAmount(){
        return paidAmount;
    }
    public Groups getGroup(){
        return group;
    }
}
//Enums Not needed Now
//interface

interface SplitStrategy{

     Map<User,Double> split(Expense expense);
}

class EqualSplit implements SplitStrategy{
    @Override
    public Map<User,Double> split(Expense expense) {
        List<User> users = expense.getGroup().getUsers();
        double amount = expense.getpaidAmount()/users.size();
        Map<User, Double> splits = new HashMap<>();
        for(User user : users){
            splits.put(user,amount);
        }
        return splits;
    }
}

class PercentageSplitStrategy implements SplitStrategy {

    private final Map<User, Double> percentageMap;

    public PercentageSplitStrategy(Map<User, Double> percentageMap) {
        this.percentageMap = percentageMap;
    }

    @Override
    public Map<User, Double> split(Expense expense) {
        Map<User, Double> splits = new HashMap<>();

        for (Map.Entry<User, Double> entry : percentageMap.entrySet()) {
            double amount = expense.getpaidAmount() * entry.getValue() / 100;
            splits.put(entry.getKey(), amount);
        }
        return splits;
    }
}
//EqualSplit
//BalanceTracking

class BalanceSheetService{
        // user-> (OtherUser->amount)
    Map<User,Map<User, Double>>balanceSheet = new HashMap<>();
    //addExpense
    public void addExpense(SplitStrategy splitStrategy, Expense expense){
            Map<User, Double> splits = splitStrategy.split(expense);
            User paidBy = expense.getPaidBy();
            for(Map.Entry<User, Double> entry : splits.entrySet()){
                User user = entry.getKey();
                double amount = entry.getValue();
                if(user.equals(paidBy)){
                    continue;
                }
                // Step 1: Check if the user already exists in the balanceSheet
//                Map<String, Double> innerMap = balanceSheet.get(user);
//
//                if (innerMap == null) {
//                    // Step 2: If not, create a new inner map
//                    innerMap = new HashMap<>();
//
//                    // Step 3: Put the newly created inner map into the balanceSheet
//                    balanceSheet.put(user, innerMap);
//                }
//
//                // Step 4: Now check if 'paidBy' exists inside user's inner map
//                Double existingAmount = innerMap.get(paidBy);
//
//                if (existingAmount == null) {
//                    // Step 5: No existing value → insert the amount
//                    innerMap.put(paidBy, amount);
//                } else {
//                    // Step 6: Value exists → add to it
//                    double updatedAmount = existingAmount + amount;
//                    innerMap.put(paidBy, updatedAmount);
//                }
//                /*Equivallent code in one line
                balanceSheet.computeIfAbsent(user, k -> new HashMap<>()).merge(paidBy, amount, Double::sum);
                balanceSheet.computeIfAbsent(paidBy, k->new HashMap<>()).merge(user,-amount,Double::sum);
            }

    }
    //ShowBalance
    public void showBalance() {
        for(User user : balanceSheet.keySet()){
            for(Map.Entry<User, Double> entry : balanceSheet.get(user).entrySet()){
                if(entry.getValue()>0){
                    System.out.println(
                            user.getName()+ "Owes"+
                            entry.getValue()+"to"+
                            entry.getKey().getName());
                }
             }
            }
    }
    public void settleBalances() {
        Map<User, Double> netBalance = new HashMap<>();

        for (User user : balanceSheet.keySet()) {
            double total = balanceSheet.get(user)
                    .values()
                    .stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
            netBalance.put(user, total);
        }

        for (User user : netBalance.keySet()) {
            if (netBalance.get(user) > 0) {
                System.out.println(user.getName() + " should receive " + netBalance.get(user));
            } else if (netBalance.get(user) < 0) {
                System.out.println(user.getName() + " should pay " + (-netBalance.get(user)));
            }
        }
    }
}

//SettleBalance
//positive means user want that money negative means user have to give

