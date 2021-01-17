package algorithms.np_problems;

import java.util.ArrayList;
import java.util.Scanner;

public class Graph {
    private ArrayList<ArrayList<Integer>> adjacencyMatrix = null;
    private Integer numberOfVertecies = null;

    public static int[] swap(int data[], int left, int right)
    {
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
        return data;
    }

    public static int[] reverse(int data[], int left, int right)
    {
        while (left < right) {
            int temp = data[left];
            data[left++] = data[right];
            data[right--] = temp;
        }

        return data;
    }

    public static boolean findNextPermutation(int data[])
    {

        if (data.length <= 1)
            return false;

        int last = data.length - 2;

        while (last >= 0) {
            if (data[last] < data[last + 1]) {
                break;
            }
            last--;
        }

        if (last < 0)
            return false;

        int nextGreater = data.length - 1;

        for (int i = data.length - 1; i > last; i--) {
            if (data[i] > data[last]) {
                nextGreater = i;
                break;
            }
        }

        data = swap(data, nextGreater, last);
        data = reverse(data, last + 1, data.length - 1);
        return true;
    }


    public Graph() {
        createGraph();
    }

    public void createGraph() {

        while (true) {
            System.out.print("State how many vertecies would you like: ");
            Scanner scanner = new Scanner(System.in);
            try {
                numberOfVertecies = scanner.nextInt();
                if (numberOfVertecies < 1) throw new IllegalArgumentException();
                break;
            } catch (Exception e) {
                if (numberOfVertecies < 1) System.out.println("Number of vertecies must be larger than 0.");
                else System.out.println("Invalid input, try again!");
            }
        }

        System.out.println("For each vertex enter the weights of edges for adjacent vertecies.");

        adjacencyMatrix = new ArrayList<>();
        for (int i = 0; i < numberOfVertecies; i++) {
            adjacencyMatrix.add(new ArrayList<>());
            for (int j = 0; j < numberOfVertecies; j++) adjacencyMatrix.get(i).add(0);
        }
        for (int i = 0; i < numberOfVertecies; i++) {
            for (int j = i + 1; j < numberOfVertecies; j++) {
                System.out.print("Enter weight of the edge " + (i + 1) + " - " + (j + 1) + " (0 if it doesn't exist):");
                Scanner scanner = new Scanner(System.in);
                try {
                    String input = scanner.nextLine();

                    Integer weight = Integer.parseInt(input);
                    if (weight < 0) throw new IllegalArgumentException();
                    adjacencyMatrix.get(i).set(j, weight);
                    adjacencyMatrix.get(j).set(i, weight);

                } catch (Exception e) {
                    System.out.println("Invalid input, try again!");
                    j--;
                }
            }
        }
    }

    boolean solveKClique(int k) {
        if (k < 2 || k > numberOfVertecies)
            throw new IllegalArgumentException("Argument k must be larger than 2 and lower than the number of vertecies in the graph.");

        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
        int[] permutation = new int[numberOfVertecies];
        for (int i = 0; i < numberOfVertecies; i++) {
            if(i < numberOfVertecies - k) permutation[i] = 0;
            else permutation[i] = 1;
        }

        do{
            ArrayList<Integer> newPermutation = new ArrayList<>();
            for (int j = 0; j < numberOfVertecies; j++) {
                if (permutation[j] > 0)
                    newPermutation.add(j);
            }
            permutations.add(newPermutation);
        } while (findNextPermutation(permutation));

        for (int i = 0; i < permutations.size(); i++) {
            if (verifyKClique(permutations.get(i))) return true;
        }

        return false;
    }

    public boolean verifyKClique(ArrayList<Integer> permutation) {
        if (permutation.size() > numberOfVertecies)
            throw new IllegalArgumentException("Permutation can't have more vertecies than there are in the graph.");

        for (Integer vertex1 : permutation) {
            for (Integer vertex2 : permutation) {
                if (vertex1 != vertex2 && adjacencyMatrix.get(vertex1).get(vertex2) == 0)
                    return false;
            }
        }
        return true;
    }

    boolean solveIndependentSet(int k) {
        if (k < 2 || k > numberOfVertecies)
            throw new IllegalArgumentException("Argument k must be larger than 2 and lower than the number of vertecies in the graph.");

        ArrayList<ArrayList<Integer>> permutations = new ArrayList<>();
        int[] permutation = new int[numberOfVertecies];
        for (int i = 0; i < numberOfVertecies; i++) {
            if(i < numberOfVertecies - k) permutation[i] = 0;
            else permutation[i] = 1;
        }

       do{
            ArrayList<Integer> newPermutation = new ArrayList<>();
            for (int j = 0; j < numberOfVertecies; j++) {
                if (permutation[j] > 0)
                    newPermutation.add(j);
            }
            permutations.add(newPermutation);
        } while (findNextPermutation(permutation));

        for (int i = 0; i < permutations.size(); i++) {
            if (verifyIndependentSet(permutations.get(i))) return true;
        }

        return false;
    }

    public boolean verifyIndependentSet(ArrayList<Integer> permutation) {
        if (permutation.size() > numberOfVertecies)
            throw new IllegalArgumentException("Permutation can't have more vertecies than there are in the graph.");

        for (Integer vertex1 : permutation) {
            for (Integer vertex2 : permutation) {

                if (vertex1 != vertex2 && adjacencyMatrix.get(vertex1).get(vertex2) != 0)
                    return false;
            }
        }
        return true;
    }
}
