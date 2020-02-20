package edu.eci.arsw.moneylaundering;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoneyLaundering
{
    public TransactionAnalyzer transactionAnalyzer;
    public TransactionReader transactionReader;
    public int amountOfFilesTotal;
    public AtomicInteger amountOfFilesProcessed;
    private static ArrayList<MonkeyLaunderingThread> hilos;

    public MoneyLaundering()
    {
        transactionAnalyzer = new TransactionAnalyzer();
        transactionReader = new TransactionReader();
        amountOfFilesProcessed = new AtomicInteger();
        hilos= new ArrayList<MonkeyLaunderingThread>();
    }

    public void processTransactionData(int ini,int fin)
    {
        amountOfFilesProcessed.set(0);
        List<File> transactionFiles = getTransactionFileList();
        amountOfFilesTotal = transactionFiles.size();
        for(int i=ini;i<fin;i++)
        {            
            List<Transaction> transactions = transactionReader.readTransactionsFromFile(transactionFiles.get(i));
            for(Transaction transaction : transactions)
            {
                transactionAnalyzer.addTransaction(transaction);
            }
            amountOfFilesProcessed.incrementAndGet();
        }
    }

    public List<String> getOffendingAccounts()
    {
        return transactionAnalyzer.listOffendingAccounts();
    }

    public List<File> getTransactionFileList()
    {
        List<File> csvFiles = new ArrayList<>();
        try (Stream<Path> csvFilePaths = Files.walk(Paths.get("src/main/resources/")).filter(path -> path.getFileName().toString().endsWith(".csv"))) {
            csvFiles = csvFilePaths.map(Path::toFile).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return csvFiles;
    }

    public static void main(String[] args)
    {
        System.out.println(getBanner());
        System.out.println(getHelp());
        MoneyLaundering moneyLaundering = new MoneyLaundering();
        int total=moneyLaundering.getTransactionFileList().size();
        //System.out.println(total);
        int tempo=total/5;
        //System.out.println(tempo);
        int inicio=0;
        int fin=tempo;
        for(int j=0;j<5;j++) {
        	
        	if(j==0) {
        		fin+=total%5;
        				
        	}	
        	//System.out.println("xd");
        	//System.out.println(inicio);
        	//System.out.println(fin);
        	MonkeyLaunderingThread h=new MonkeyLaunderingThread(moneyLaundering,inicio,fin);
        	hilos.add(h);
        	h.start();
        	inicio=fin;
        	fin+=tempo;
        }
        //Thread processingThread = new Thread(() -> moneyLaundering.processTransactionData());
        //processingThread.start();
    
        while(true)
        {
    
        	
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            for(MonkeyLaunderingThread h:hilos) {
            	synchronized(h) {
            		h.pause=true;
            		h.notifyAll();
            	}	
            	
            }
            if(line.contains("exit"))
            {
                System.exit(0);
            }
            
            String message = "Processed %d out of %d files.\nFound %d suspect accounts:\n%s";
            List<String> offendingAccounts = moneyLaundering.getOffendingAccounts();
            String suspectAccounts = offendingAccounts.stream().reduce("", (s1, s2)-> s1 + "\n"+s2);
            message = String.format(message, moneyLaundering.amountOfFilesProcessed.get(), moneyLaundering.amountOfFilesTotal, offendingAccounts.size(), suspectAccounts);
            System.out.println(message);
            moneyLaundering.activarhilos();
        }
    }
    private void activarhilos() {
    	  for(MonkeyLaunderingThread h:hilos) {
          	synchronized(h) {
          		h.pause=true;
          		h.notifyAll();
          	}	
          	
          }
    	
    }
    private static String getBanner()
    {
        String banner = "\n";
        try {
            banner = String.join("\n", Files.readAllLines(Paths.get("src/main/resources/banner.ascii")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return banner;
    }

    private static String getHelp()
    {
        String help = "Type 'exit' to exit the program. Press 'Enter' to get a status update\n";
        return help;
    }
}