import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
public class Transaction {
    private String transactionType;
    private double amount;
    private long accountNum;
    private String accountHName;
    private Date date;
    private String raccountHName;
    private long raccountNum;
    public Transaction() {
    }
    public void transaction(long accountNum, String transactionType,String accountHName,double amount) {
        this.accountNum = accountNum;
        this.transactionType = transactionType;
        this.accountHName = accountHName;
        this.amount = amount;
        date = new Date();
        operation();
    }
    public void transaction(long accountNum, String transactionType,double amount) {
        this.accountNum= accountNum;
        this.transactionType = transactionType;
        this.amount = amount;
        date = new Date();
        operation();
    }
    private void operation() {
        if (transactionType.equalsIgnoreCase("Opening")) {
            try{
                // Create file
                FileWriter fstream = new FileWriter("MyFile.txt", true);
                BufferedWriter out =new BufferedWriter(fstream);
                int userId=(int) (findMaxId()+1);
                out.write(Integer.toString(userId)+"\n");
                out.write(accountHName+"\n");
                out.write(amount+"\n");
                out.write(date+"\n");
                //Close the output stream
                out.close();
            }catch (IOException e) {
                System.err.println("Caught IOException: " + e.getMessage());
            }
        }

        else if (transactionType.equalsIgnoreCase("withdraw")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findUpdate() ;
            }else{
                System.out.println("File not Found");
            }
        }

        else if (transactionType.equalsIgnoreCase("deposit")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findUpdate() ;
            }else{
                System.out.println("File not Found");
            }
        }

        else if (transactionType.equalsIgnoreCase("hydro")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findUpdate() ;
            }else{
                System.out.println("File not Found");
            }
        }

        else if (transactionType.equalsIgnoreCase("electric")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findUpdate() ;
            }else{
                System.out.println("File not Found");
            }
        }

        else if (transactionType.equalsIgnoreCase("gas")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findUpdate() ;
            }else{
                System.out.println("File not Found");
            }
        }

        else if (transactionType.equalsIgnoreCase("showInfo")) {
            Path path = Paths.get("MyFile.txt");
            if (Files.exists(path)) {
                findDisplay();
            }else{
                System.out.println("File not Found");
            }
        }

        else {
            System.out.println("Invalid option");
            return;
        }

    }
    private int findMaxId() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("MyFile.txt"));
            int count=0;
            while ((reader.readLine()) != null)
            {
                count=count+1;
            }
            reader.close();
            // Logic for finding maximum Id
            return count/4;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", "MyFile.txt");
            e.printStackTrace();
        }
        return 0;
    }

    private void findDisplay() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("MyFile.txt"));
            String line;
            String trmpaccountNum=Long.toString(accountNum);
            int count=1;
            while ((line = reader.readLine()) != null)
            {
                //my logic for displaying 2 more lines after found
                if(count>1){
                    trmpaccountNum =line;
                }
                if((line.equals(trmpaccountNum))&&(count<5)){
                    if(count==1){
                        System.out.println("\n\nAccount Number      : "+line);
                        count+=1;
                    }
                    else if(count==2){
                        System.out.println("Account Holder      : "+line);
                        count+=1;
                    }
                    else if(count==3){
                        System.out.println("Account Balance     : "+line);
                        count+=1;
                    }
                    else if(count==4){
                        System.out.println("Last Transaction on : "+ line + "\n\n");
                        count+=1;
                    }
                }
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", "MyFile.txt");
            e.printStackTrace();
        }
    }
    private void findUpdate() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader("MyFile.txt"));

            FileWriter fstream = new FileWriter("TempFile.txt", true);
            BufferedWriter out =new BufferedWriter(fstream);

            String line;
            String trmpaccountNum=Long.toString(accountNum);
            int count=1;
            while ((line = reader.readLine()) != null)
            {

                //my logic for updating 3 lines after found
                if(count>1){
                    trmpaccountNum =line;
                }
                if((line.equals(trmpaccountNum))&&(count<5)){
                    double temp_amount = 0;
                    if (count==1){
                        out.write(accountNum+"\n");
                    }
                    else if(count ==2){
                        out.write(accountHName+"\n");
                    }
                    else if (count==3){
                        if(transactionType.equalsIgnoreCase("withdraw")){
                            temp_amount=Double.parseDouble(line)-amount;
                        }else if(transactionType.equalsIgnoreCase("deposit")){
                            temp_amount=amount+Double.parseDouble(line);
                        }else if(transactionType.equalsIgnoreCase("hydro")){
                            temp_amount=Double.parseDouble(line)-amount;
                        }else if(transactionType.equalsIgnoreCase("electric")){
                            temp_amount=Double.parseDouble(line)-amount;
                        }else if(transactionType.equalsIgnoreCase("gas")){
                            temp_amount=Double.parseDouble(line)-amount;
                        }
                        if(temp_amount<0){
                            System.out.println("Could not update \n");
                            out.write(Double.toString(amount)+"\n");
                        }else{
                            System.out.println(transactionType);
                            System.out.println("Transaction Successfully executed.\n" + Double.toString(temp_amount) + " is updated balance.\n");
                            out.write(Double.toString(temp_amount)+"\n");
                        }
                    }
                    else if(count==4){
                        out.write(date+"\n");
                    }
                    count=count+1;
                }else{
                    out.write(line+"\n");//System.out.println("Account not found in file");
                }
            }
            out.close();
            reader.close();
            // Rename the temporary file and delete original file
            File f1 = new File("MyFile.txt");
            f1.delete();
            File f2 = new File("TempFile.txt");
            boolean b = f2.renameTo(f1);
            if(b){
            }else{
                System.out.println("Error while updating!");
            }
            // Rename the temporary file and delete original file
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", "MyFile.txt");
            e.printStackTrace();
        }
    }
}
