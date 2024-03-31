package org.bearluxury;

import java.io.*;
import java.util.ArrayList;

public class AccountBuilder {
    ArrayList<Account> accountList;

    AccountBuilder (String csvFile) {
        accountList = new ArrayList<>();
        File file = new File(csvFile);
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            line = reader.readLine();

            while((line  = reader.readLine()) != null) {
                String[] parsedLine = line.split(",");

                Account account = new Account(
                        parsedLine[0],
                        parsedLine[1],
                        parsedLine[2],
                        parsedLine[3],
                        Long.parseLong(parsedLine[4]),
                        parsedLine[5]
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addAccount(String firstName,
                           String lastName,
                           String userName,
                           String email,
                           long phoneNumber,
                           String password) {
        Account account = new Account(firstName,
                lastName,
                userName,
                email,
                phoneNumber,
                password);
        accountList.add(account);
    }

    public void writeAccount (String csvFile) {
        File file = new File(csvFile);
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(file));
            writer.write("first name, last name, username, email, phone number, password\n");

            for(int i = 0; i < accountList.size(); i++) {
                writer.write(accountList.get(i).toString());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());
                }
            }
        }
    }

    ArrayList<Account> getAccountList() {
        return accountList;
    }
}
