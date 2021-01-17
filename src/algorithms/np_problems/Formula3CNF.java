package algorithms.np_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Formula3CNF {
    private ArrayList<ArrayList<Integer>> clauses =  new ArrayList<>();
    private Integer numberOfVariables = 0;

    private ArrayList<Boolean> addOne(ArrayList<Boolean> x) {
        ArrayList<Boolean> result = new ArrayList<>(x);
        boolean digit = result.get(0);
        int i = 0;
        while(digit) {
            result.set(i, false);
            i++;
            if(i < result.size()) digit = result.get(i);
            else break;
        }

        if(i < result.size()) result.set(i, true);
        return result;
    }

    public Formula3CNF() {
        create3CNF();
    }

    public void create3CNF() {
        clauses = new ArrayList<>();
        Integer numberOfClauses = null;
        while (true) {
            System.out.print("State how many conjuctions would you like: ");
            Scanner scanner = new Scanner(System.in);
            try {
                numberOfClauses = scanner.nextInt();
                if (numberOfClauses < 1 || numberOfClauses > 8) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                if (numberOfClauses < 1) System.out.println("Number of clauses must be larger than 0.");
                else if (numberOfClauses > 8) System.out.println("Number of clauses must be lower than or equal to 8.");
                else System.out.println("Invalid input, try again!");
            }
        }

        System.out.println("Clauses input. For each clause enter 3 numbers divided with commas." +
                "To negate enter a negative number, eg. 1,3,5 or -3,2,1:");

        ArrayList<Integer> existingVariables = new ArrayList<>();
        for (int i = 0; i < numberOfClauses; i++) {
            System.out.print("Enter the " + (i + 1) + ". conjuction:");
            Scanner scanner = new Scanner(System.in);
            Integer variable1 = null, variable2 = null, variable3 = null;
            Boolean variable1Added = false, variable2Added = false, variable3Added = false;
            try {
                String input = scanner.nextLine();
                String[] variablesAsStrings = input.split(",");
                if(variablesAsStrings.length != 3) throw new Exception();
                variable1 = Integer.parseInt(variablesAsStrings[0]);
                variable2 = Integer.parseInt(variablesAsStrings[1]);
                variable3 = Integer.parseInt(variablesAsStrings[2]);

                if (!existingVariables.contains(Math.abs(variable1))) {
                    existingVariables.add(Math.abs(variable1));
                    variable1Added = true;
                }
                if (!existingVariables.contains(Math.abs(variable2))) {
                    existingVariables.add(Math.abs(variable2));
                    variable2Added = true;
                }
                if (!existingVariables.contains(Math.abs(variable3))) {
                    existingVariables.add(Math.abs(variable3));
                    variable3Added = true;
                }

                if (existingVariables.size() > 24) throw new Exception();

                clauses.add(new ArrayList<>(Arrays.asList(variable1, variable2, variable3)));

            } catch (Exception e) {
                if (existingVariables.size() > 24) {
                    if (variable1 != null && variable1Added) existingVariables.remove(variable1);
                    if (variable2 != null && variable2Added) existingVariables.remove(variable2);
                    if (variable3 != null && variable3Added) existingVariables.remove(variable3);
                    System.out.println("Number of variables should not exceed 24. Try again!");
                }
                System.out.println("Invalid input, try again!");
                i--;
            }
        }
        numberOfVariables = existingVariables.size();
    }

    public boolean solve3CNF() {
        ArrayList<Boolean> x = new ArrayList<>(Collections.nCopies(numberOfVariables, false));

        for(int i = 0; i < Math.pow(2, numberOfVariables); i++) {
            x = addOne(x);
            if(verify3CNF(x)) return true;
        }

        return false;
    }

    public boolean verify3CNF(ArrayList<Boolean> x) {
        ArrayList<Boolean> clauseResults = new ArrayList<>();

        for(ArrayList<Integer> clause : clauses){
            Integer index1 = Math.abs(clause.get(0)) - 1;
            Integer index2 = Math.abs(clause.get(1)) - 1;
            Integer index3 = Math.abs(clause.get(2)) - 1;

            clauseResults.add((clause.get(0) > 0 ? x.get(index1) : !x.get(index1)) ||
                                (clause.get(1) > 0 ? x.get(index2) : !x.get(index2)) ||
                                (clause.get(2) > 0 ? x.get(index3) : !x.get(index3)));
        }

        boolean result = true;
        for(Boolean clauseResult : clauseResults){
            result = result && clauseResult;
        }

        return result;
    }

}
