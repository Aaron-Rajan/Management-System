import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.*;

public class main_file {
    public static void main(String[] args) throws FileNotFoundException, IOException{
            File file = new File("Management_System.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
    
            System.out.println("Welcome to your business management system, what would you like to do?");
            System.out.println("Please press:" + "\n" +  "a to add a new employee" + "\n" +
            "r to remove an existing employee" + "\n" +
             "c to change the data of an existing employee" + "\n" + 
             "f to find the data of an existing employee"+ "\n" + 
             "e to empty the list" + "\n" +
             "q to quit");
            Scanner sc = new Scanner(System.in);

            while (true) {
                String input = sc.nextLine();
                if (input.equals("a")) {
                    add(file, sc);
                    System.out.println("Employee has been successfully added");
                }
                else if (input.equals("r")) {
                    boolean b = remove(file, sc);
                    if (b == false) {
                        System.out.println("The inputted ID does not exist");
                    }
                    else {
                        System.out.println("Employee has been successfully removed");
                    }
                }
                else if (input.equals("c")) {
                    boolean b = change(file, sc);
                    if (b == true) {
                        System.out.println("The data of this employee has been successfully changed");                      
                    }
                }
                else if (input.equals("f")) {
                    find(sc, file);
                }
                else if (input.equals("e")) {
                    empty(file);
                }
                else if (input.equals("q")) {
                    break;
                }
                else if (input.equals("")) {
                    continue;
                }
                else {
                    System.out.println("Not a valid key, please try again");
                }
                System.out.println("What would you like to do next?");
            }

            sc.close();
            System.out.println("Have a good day!");

    }
    
    public static void add(File file, Scanner sc) throws IOException {
        System.out.println("Please enter the name, ID, and wage($/hr) of the employee you would like to add");
        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("ID: ");
        String ID = sc.nextLine();
        ID = check_ID(file, ID, sc);

        System.out.print("Salary: ");
        String wage = sc.nextLine();

        String info = "Name: " + name + "\t" + "ID: " + ID + "\t" + "Salary: " + wage;

        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(info + "\n");
        bw.close();
    }
    public static String check_ID(File file, String ID, Scanner sc) throws FileNotFoundException, IOException {
        FileReader fr = new FileReader(file); 
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String file_line = line;
            if (file_line.contains("ID: " + ID + "\t")) {
                System.out.println("The inputted ID already exists in the system, please choose a new ID");
                System.out.print("ID: ");
                ID = sc.nextLine();
                ID = check_ID(file,ID,sc);
            }
        }
        br.close();
        return ID;
    }
    public static boolean remove(File file, Scanner sc) throws IOException {
        System.out.println("What is the ID of the employee you would like to remove from the system?");
        String remove_ID = sc.nextLine();
        boolean b = false;

        File temp_file = new File("Management_System_temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(temp_file);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
            
        while((line = br.readLine()) != null) {
            if (line.contains("ID: " + remove_ID + "\t")) {
                b = true;
                continue;
            }
            else {
                bw.write(line + "\n");
            }
        }
        br.close();
        bw.close();

        file.delete();
        temp_file.renameTo(file);
        
        return b;
    }
    public static String get_wage_ID(String ID, File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String wage_2 = null;

        while ((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                String[] arr = line.split("\t");
                String wage_cat = arr[2];
                String[] wage_arr = wage_cat.split(":");
                wage_2 = wage_arr[1];
            }
        }
        br.close();
        return wage_2;        
    }
    public static String get_wage_name(String temp_name, File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String wage_2 = null;

        while ((line = br.readLine()) != null) {
            if (line.contains("Name: " + temp_name)) {
                String[] arr = line.split("\t");
                String wage_cat = arr[2];
                String[] wage_arr = wage_cat.split(":");
                wage_2 = wage_arr[1];
            }
        }
        br.close();
        return wage_2;
    }
    public static String get_name(String ID, File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        String temp_name = null;

        while ((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                String[] arr = line.split("\t");
                String wage_cat = arr[0];
                String[] wage_arr = wage_cat.split(":");
                temp_name = wage_arr[1];
            }
        }
        br.close();
        return temp_name;        
    }
    public static boolean check_ID_exists(String ID, File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        int ID_count = 0;
        boolean b = false;

        while ((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                ID_count++;
            }
        }

        if (ID_count > 0) {
            b = true;
        }

        br.close();
        return b;

    }
    public static void change_name(String ID, String new_name, File file) throws IOException {
        File temp_file = new File("Management_System_temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(temp_file);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
        
        while((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                String salary = get_wage_ID(ID, file);
                bw.write("Name: " + new_name + "\t" + "ID: " + ID + "\t" + "Salary:" + salary + "\n");
            }
            else {
                bw.write(line + "\n");
            }
        }
        br.close();
        bw.close();

        file.delete();
        temp_file.renameTo(file);
    }
    public static void change_ID(String temp_name, String new_ID, File file) throws IOException {
        File temp_file = new File("Management_System_temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(temp_file);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
            
        while((line = br.readLine()) != null) {
            if (line.contains("Name: " + temp_name)) {
                String salary = get_wage_name(temp_name, file);
                bw.write("Name: " + temp_name + "\t" + "ID: " + new_ID + "\t" + "Salary:" + salary + "\n");
            }
            else {
                bw.write(line + "\n");
            }
        }
        br.close();
        bw.close();

        file.delete();
        temp_file.renameTo(file);
    }
    public static void change_ID_2(String temp_name, String old_ID, String new_ID, File file) throws IOException {
        File temp_file = new File("Management_System_temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(temp_file);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
            
        while((line = br.readLine()) != null) {
            if (line.contains("ID: " + old_ID + "\t")) {
                String salary = get_wage_ID(old_ID, file);
                bw.write("Name: " + temp_name + "\t" + "ID: " + new_ID + "\t" + "Salary:" + salary + "\n");
            }
            else {
                bw.write(line + "\n");
            }
        }
        br.close();
        bw.close();

        file.delete();
        temp_file.renameTo(file);        
    }
    public static void change_wage(String ID, String new_wage, File file) throws IOException {
        File temp_file = new File("Management_System_temp.txt");
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(temp_file);
        BufferedWriter bw = new BufferedWriter(fw);

        String line;
        
        while((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                String temp_name = get_name(ID, file);
                bw.write("Name:" + temp_name + "\t" + "ID: " + ID + "\t" + "Salary: " + new_wage + "\n");
            }
            else {
                bw.write(line + "\n");
            }
        }
        br.close();
        bw.close();

        file.delete();
        temp_file.renameTo(file);        
    }
    public static boolean change(File file, Scanner sc) throws IOException {
        System.out.println("Would you like to update the name, ID, or salary of an existing employee?" + "\n" +
        "Please press n to change the name" + "\n" + 
        "Please press i to change the ID" + "\n" + 
        "Please press s to change the salary");
        String input_2 = sc.nextLine();
        boolean b = true;

        if (input_2.equals("n")) {
            System.out.println("What is the ID of the employee you would like to edit?");
            System.out.print("ID: ");
            String ID = sc.nextLine();

            b = check_ID_exists(ID, file);

            if (b == false) {
                System.out.println("The inputted ID does not exist in the system");
            }
            else {
                System.out.println("What would you like to change the name to?");
                System.out.print("New name: ");
                String new_name = sc.nextLine();
    
                change_name(ID, new_name, file);
            }
        }
        else if(input_2.equals("i")) {

            boolean d = true;

            System.out.println("What is the name of the employee you would like to edit?");
            System.out.print("Name: ");
            String name = sc.nextLine();

            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line;
            int name_count = 0;
            String old_ID = null;

            while ((line = br.readLine()) != null) {
                if (line.contains("Name: " + name)) {
                    name_count++;
                }
            }

            if (name_count > 1) {
                System.out.println("More than one employee is registered under the inputted name, please enter the ID of employee you would like to edit");
                System.out.print("Old ID: ");
                old_ID = sc.nextLine();

                b = check_ID_exists(old_ID, file);
                d = false;
            }
            else if (name_count == 0) {
                b = false;
            }

            br.close();

            if (b == true) {
                System.out.println("What would you like to change the ID to?");
                System.out.print("New ID: ");
                String new_ID = sc.nextLine();
                new_ID = check_ID(file, new_ID, sc);

                if (d == false) {
                    change_ID_2(name, old_ID, new_ID, file);
                }
                else {
                    change_ID(name, new_ID, file);
                }
            }
            else {
                System.out.println("The inputted ID does not exist in the system");
            }
        }
        else if (input_2.equals("s")) {
            System.out.println("What is the ID of the employee you would like to edit?");
            System.out.print("ID: ");
            String ID = sc.nextLine();
            b = check_ID_exists(ID, file);

            if (b == true) {
                System.out.println("What would you like to change the salary to?");
                System.out.print("New salary: ");
                String new_wage = sc.nextLine();
    
                change_wage(ID, new_wage, file);
            }
            else {
                System.out.println("The inputted ID does not exist in the system");
            }
        }
        else {
            b = false;
            System.out.println("Not a valid key");
        }
        return b;
    }
    public static void find(Scanner sc, File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        System.out.println("What is the ID of the employee you would like to find?");
        System.out.print("ID: ");
        String ID = sc.nextLine();
        int count = 0;

        while ((line = br.readLine()) != null) {
            if (line.contains("ID: " + ID + "\t")) {
                System.out.println(line);
                count++;
            }
        }
        if (count == 0) {
            System.out.println("The inputted ID does not exist in the system");
        }
        br.close();
    }
    public static void empty(File file) throws IOException {
        File temp_file = new File("Management_System_temp.txt");
        if (!temp_file.exists()) {
            temp_file.createNewFile();
        }
        file.delete();
        temp_file.renameTo(file);
        System.out.println("Your management file has been emptied");
    }
}
