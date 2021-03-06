import java.util.*;
import java.io.*;

public class JaccardsClinic{
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        int choice;
        CountingBloomFilter CBFdisease = new CountingBloomFilter(50);
        CBFdisease.initialize();
        ContadorEstocastico CEone = new ContadorEstocastico();
        ContadorEstocastico CEtwo = new ContadorEstocastico(0);
        ArrayList<String> map = new ArrayList<String>();

        while(true){
                System.out.println("\n\t\t***** Welcome to Jaccard's Clinic *****\n");
                System.out.println("");
                System.out.println("");
                System.out.println("How can we help you?");
                System.out.print("  1) Begin diagnosis\n  2) Show user count\n  3) Show disease graph\n  4) Exit\n\nOption: ");
                try{
                    choice = sc.nextInt();
                }catch(InputMismatchException ime){
                    choice = 0; sc.next();
                }
                 

            if(choice == 1){    
                System.out.print("Choose shingle size (1 is recommended): ");
                int shingleSize = sc.nextInt();
                MinHash x = new MinHash(shingleSize);

                System.out.print("Please describe your symptoms (at least 3 recommended):\n\t");
                sc.nextLine();
                String sick = sc.nextLine();
                sick = sick.toLowerCase();

                if(sick.length() < shingleSize){
                    System.out.println("The amount of words describing your symptoms should be longer than the chosen shingle size!");
                }
                else{
                    x.wordShingle(sick);
                    map = loadD(x);
                    MinHash.updateMatrix();

                    double JSmax = 0.0;
                    int index = 0;

                    for (int i = 2; i <= x.setSaver().size(); i++){
                        if(x.JSimMH(1, i) > JSmax){
                            JSmax = x.JSimMH(1,i);
                            index = i;
                        }
                    }
                    if(index != 0){
                        System.out.println("You probably have: " + map.get(index-2));
                        System.out.println("However, this is just a diagnosis. Consult your doctor for a more detailed examination.");
                        CBFdisease.insert(map.get(index-2));
                        CEone.primeiraSolucao();
                        CEtwo.segundaSolucao();
                    }
                    else{
                        System.out.println("We didn't find any disease with these symptoms.");
                        System.out.println("Maybe you are describing very few symptoms. Please try to be as specific as possible.");
                    }
                }
            }

            else if(choice == 2){
                System.out.println("Stochastic counter algorithm one: " + CEone.primeiraToString());
                System.out.println("Stochastic counter algorithm two: " + CEtwo.segundaToString());
            }

            else if(choice == 3){
                if (map.size()==0){
                    System.out.println("No diagnoses have yet been made to show a disease chart!");
                }
                else {
                    for (int i = 0; i < map.size(); i++) {
                        System.out.print(String.format("%20s %1s", map.get(i),"|"));

                        if(CBFdisease.isMember(map.get(i))){
                            for (int j = 0; j < CBFdisease.count(map.get(i)); j++) {
                                System.out.print("*");
                            }
                        } 

                        System.out.println();   
                    }  
                }
            }

            else if(choice == 4){
                System.exit(0);
                // Close the program
            }

            else{
                System.out.println("Invalid option!");
                // Jumps to the menu again
            }
        }
    }



    public static ArrayList<String> loadD(MinHash x) throws IOException{
        File list = new File("Sintomas.txt");
        Scanner fsc = new Scanner(list);
        ArrayList<String> map = new ArrayList<String>();

        while(fsc.hasNextLine()){
            if(fsc.next().charAt(0) == '@'){
                map.add(fsc.next());
            }
            else{
                x.wordShingle(fsc.nextLine());
            }
        }
        fsc.close();
        return map;
    }
}
