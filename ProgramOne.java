/*
 * Michael Krueger
 * 09/03/2024
 * Program 1 - Find It Fast
 */


//imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ProgramOne {
    public static String Target;

    public static void main(String[] args) {
        //make list1
        List<Character> list1 = new List<>();
        try {
            //reads input file
            File myObj = new File("prog1input2.txt");
          try (Scanner myReader1 = new Scanner(myObj)) {
              while (myReader1.hasNextLine()) {
                  String data = myReader1.nextLine();
                  //System.out.println(data);
                  //inserts each character of input 1 into list1
                    for (int i = 0; i < data.length(); i++) {
                        list1.InsertAfter(data.charAt(i));
                    }
              }
          }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            System.err.println("File not found: " + e.getMessage());
        }
        //create target
        Target = System.console().readLine("Enter the target string: ");
        //call brute force and time it
        long startTime = System.nanoTime();//for finding time I used this tutorial https://www.tutorialspoint.com/java/lang/system_nanotime.htm
        BruteForce(list1);
        long endTime = System.nanoTime();
        //call KMP and time it
        long startTime2 = System.nanoTime();
        KMP(list1);
        long endTime2 = System.nanoTime();
        //call Boyer-Moore and time it
        long startTime3 = System.nanoTime();
        Boyer_Moore(list1);
        long endTime3 = System.nanoTime();
        //print time for each algorithm
        long timeElapsed = endTime - startTime;
        long timeElapsed2 = endTime2 - startTime2;
        long timeElapsed3 = endTime3 - startTime3;
        System.out.println("Brute Force Time: " + timeElapsed + " nanoseconds");
        System.out.println("KMP Time: " + timeElapsed2 + " nanoseconds");
        System.out.println("Boyer-Moore Time: " + timeElapsed3 + " nanoseconds");
    }

///////////////////////////////////////////////////////////////////brute force algorithm
    public static void BruteForce(List<Character> list1){
        int count = 0;
        int sLength = list1.GetSize();
        //list of indexes
        List<Integer> indexes = new List<>();
        //see how many of target there is in list1
        for (int i = 0; i < sLength - Target.length()+1; i++){
            //make boolean for if target is found
            boolean found = true;
            //check if target is in list1
            for (int j = 0; j < Target.length(); j++){
                //if target is found
                if(list1.GetValueAt(i+j) == Target.charAt(j)){
                    found = true;
                    
                }else{
                    //if target is not found
                    found = false;
                    break;
                }
            }
            //if target is found add to count
            if(found){
                count++;
                //add indexes to list and makes sure to add the first index of the target with starting at 0
                indexes.InsertAfter(i-Target.length()+5); //if target is found add indexes to list
            }
        }
        //print indexes
        System.out.print("Brute Force Indexes: ");
        indexes.First();
        for (int i = 0; i < indexes.GetSize(); i++){
            //prints index with spacing
            System.out.print(indexes.GetValue() + " ");
            //goes to next index
            indexes.Next();
        }
        System.out.println();
        System.out.println("Brute Force: " + Target + " appears " + count + " times.");
    }

/////////////////////////////////////////////////////////////////////KMP Algorithm
    public static void KMP(List<Character> list1){
        //failure function
        int[] failure = new int[Target.length()];
        //make failure function for target
        for (int i = 1; i < Target.length(); i++){
            int j = failure[i-1];
            //if index i and j are not the same then go to the next index
            while (j > 0 && Target.charAt(i) != Target.charAt(j)){
                j = failure[j-1];
            }
            //if index i and j are the same then add 1 to j
            if (Target.charAt(i) == Target.charAt(j)){
                j++;
            }
            //add j to failure
            failure[i] = j;
        }
        //search
        //list of indexes
        List<Integer> indexes = new List<>();
        int count = 0;
        int sLength = list1.GetSize();
        //see how many of target there is in list1
        for (int i = 0, j = 0; i < sLength; i++){
            //while loop to check if target is in list1 using failure function
            while (j > 0 && list1.GetValueAt(i) != Target.charAt(j)){
                j = failure[j-1];
            }
            //if target is in list1
            if (list1.GetValueAt(i) == Target.charAt(j)){
                j++;
            }
            //if target is found add to count and go to next index
            if (j == Target.length()){
                count++;
                j = failure[j-1];
                indexes.InsertAfter(i-Target.length()+1); //if target is found add indexes to list
            }
        }

        //print indexes
        System.out.print("KMP Indexes: ");
        indexes.First();
        for (int i = 0; i < indexes.GetSize(); i++){
            System.out.print(indexes.GetValue() + " ");
            indexes.Next();
        }
        System.out.println();
        System.out.println("KMP: " + Target + " appears " + count + " times.");
    }
    
////////////////////////////////////////////////////////////////Boyer-Moore Algorithm
    public static void Boyer_Moore(List<Character> list1) {
        //make bad character table
        int[] badChar = new int[256];
        //make bad character table
        for (int i = 0; i < 256; i++){
            badChar[i] = -1;
        }
        for (int i = 0; i < Target.length(); i++){
            badChar[(int) Target.charAt(i)] = i;
        }
        //good suffix table
        int[] goodSuffix = new int[Target.length()];
        int[] suffix = new int[Target.length()];
        //this is for the good suffix table
        for (int i = 0; i < Target.length(); i++){
            suffix[i] = -1;
        }
        //goes through the target and makes the good suffix table
        for (int i = Target.length()-1; i >= 0; i--){
            if (suffix[i] == -1){
                if (i == Target.length()-1){
                    suffix[i] = 0;
                }else{
                    suffix[i] = Target.length()-i-1;
                }
                //if the suffix is not -1 then go through the target and make the good suffix table
                for (int j = i-1; j >= 0; j--){
                    if (Target.charAt(j) == Target.charAt(Target.length()-1-suffix[i])){
                        suffix[j] = Target.length()-1-j;
                    }
                }
            }
        }
        //these loops are for the good suffix table
        for (int i = 0; i < Target.length(); i++){
            goodSuffix[i] = Target.length();
        }
        for (int i = 0; i < Target.length()-1; i++){
            goodSuffix[Target.length()-1-suffix[i]] = Target.length()-1-i;
        }
        //search
        int count = 0;
        int sLength = list1.GetSize();
        //list for printing indexes
        List<Integer> indexes = new List<>();
        //see how many of target there is in list1
        for (int i = 0; i < sLength - Target.length()+1; i++){
            //make boolean for if target is found
            boolean found = true;
            //check if target is in list1
            for (int j = Target.length()-1; j >= 0; j--){
                //if target is found
                if(list1.GetValueAt(i+j) == Target.charAt(j)){
                    found = true;
                }else{
                    //if target is not found
                    found = false;
                    break;
                }
            }
            //if target is found add to count
            if(found){
                count++;
            }
            //if target is found print the indexes of where target is in list1
            if(found){
                //add indexes to list
                indexes.InsertAfter(i);
            }
        }
        //print indexes
        System.out.print("Boyer-Moore Indexes: ");
        indexes.First();
        for (int i = 0; i < indexes.GetSize(); i++){
            System.out.print(indexes.GetValue() + " ");
            indexes.Next();
        }
        System.out.println();
        System.out.println("Boyer-Moore: " + Target + " appears " + count + " times.");
    }
}


/*
 * SUMMARY
 * When looking at the output of the code, the Brute Force algorthm seems to be the slowest of the three.
 * The KMP algorithm seems to be the fastest for finding the target string in the list, but the Boyer-Moore
 * algorithm seems to be the fastest when the pattern is not present in the list. Boyer-Moore also seems to
 * be a lot slower when the text has a lot of repeats with the pattern also being in the text. So, from this,
 * the algorithm that seems to be better for finding the target string the fastest is the KMP algorithm, and
 * the Boyer-Moore algorithm seems to be better for when the pattern is not present in the text, or rarely present.
 */