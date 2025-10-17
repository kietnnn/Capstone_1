package com.pluralsigh;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Application {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        MainMenu();
        scanner.close();
    }

    public static void MainMenu() {
        boolean running = true;

        while (running) {
            HomeMenu();
            String option = scanner.nextLine();

            switch (option) {
                case "D":
                    ShowAddDeposit();
                    break;
                case "P":
                    AddPayment();
                    break;
                case "L":
                    ShowLedger();
                    break;
                case "E":
                    System.out.println("Thank for using service");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        }

    }


    private static void HomeMenu() {
        System.out.println("""
                {<- - - - -MAIN MENU- - - - ->}
                     (Choose your option)
                 {D}. Add Deposit
                 {P}. Make Payment (Debit)
                 {L}. Ledger
                 {E}. Exit
                
                YOUR OPTION IS: """);
    }

    private static void ShowAddDeposit() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {

            System.out.print("Enter description: ");
            String description1 = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor1 = scanner.nextLine();

            System.out.print("Enter amount (Only positive): ");
            double amount1 = scanner.nextDouble();
            scanner.nextLine();
            if (amount1 <= 0) {
                System.out.println("Invalid deposit, Try Again");
                return;
            }

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().withNano(0);

            String cvs = String.format("%s | %s | %s | %s | %.2f",
                    date, time, description1, vendor1, amount1);
            writer.write("\n");
            writer.write(cvs);


        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }

    private static void AddPayment() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {

            System.out.print("Enter description: ");
            String description2 = scanner.nextLine();

            System.out.print("Enter vendor: ");
            String vendor2 = scanner.nextLine();

            System.out.print("Enter amount (Only Negative): ");
            double amount2 = scanner.nextDouble();
            scanner.nextLine();
            if (amount2 >= 0) {
                System.out.println("Invalid deposit, Try Again");
                return;
            }

            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now().withNano(0);

            String cvs = String.format("%s | %-8s | %s | %s | %.2f",
                    date, time, description2, vendor2, amount2);
            writer.write("\n");
            writer.write(cvs);


        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void ShowLedger() {
        boolean running = true;

        while (running) {
            displayLedger();
            String LedgerOptions = scanner.nextLine();

            switch (LedgerOptions) {
                case "A":
                    displayAll();
                    break;
                case "D":
                    OnlyDeposit();
                    break;
                case "P":
                    OnlyPayment();
                    break;
                case "R":
                    Reportlist();
                    break;
                case "H":
                    running = false;
                    break;
                case "E":
                    System.out.println("Thank for using service");
                    running = false;
                    break;
                default:
                    System.out.println("Go back");

            }
        }
    }

    private static void displayLedger() {
        System.out.println("""
                |=====---[LEDGER OPTIONS]---=====|
                        [A] All
                        [D] Deposits
                        [P] Payment
                        [R] Reports
                        [E] Exit
                Choose your option: """);

    }

    private static void displayAll() {

        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            System.out.printf("%s %s %s %s %" +
                            "s%n",
                    "Date", "Time", "Description", "Vendor", "Amount");

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }


            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void OnlyDeposit() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String table;
            System.out.printf("%s %s %s %s %" +
                            "s%n",
                    "Date", "Time", "Description", "Vendor", "Amount");

            while ((table = bufferedReader.readLine()) != null) {
                if (table.trim().isEmpty()) continue;

                String[] parts = table.split("\\|");

                String date = parts[0].trim();
                String time = parts[1].trim();
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                double amount;

                try {
                    amount = Double.parseDouble(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println(table);
                    continue;
                }

                if (amount > 0) {
                    System.out.printf("%s %s %s %s $%.2f%n",
                            date, time, description, vendor, amount);
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    private static void OnlyPayment() {
        try {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String table;
            System.out.printf("%s %s %s %s %" +
                            "s%n",
                    "Date", "Time", "Description", "Vendor", "Amount");

            while ((table = bufferedReader.readLine()) != null) {
                if (table.trim().isEmpty()) continue;

                String[] parts = table.split("\\|");

                String date = parts[0].trim();
                String time = parts[1].trim();
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                double amount;
                try {
                    amount = Double.parseDouble(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println(table);
                    continue;
                }
                if (amount < 0) {
                    System.out.printf("%s %s %s %s $%.2f%n",
                            date, time, description, vendor, amount);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void Reportlist() {
        while (true) {
            System.out.println("""
                    ===== REPORTS MENU =====
                      +---Type: 
                      1- Month To Date
                      2- Previous Month
                      3- Year To Date
                      4- Previous Year
                      5- Search by Vendor
                      0- Back
                    
                    
                    """);
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    MonthToDate();
                    break;
                case "2":
                    PreviousMonth();
                    break;
                case "3":
                    YearToDate();
                    break;
                case "4":
                    PreviousYear();
                    break;
                case "5":
                    SearchByVendor();
                case "0":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }

    }

    private static void MonthToDate() {
        String filePath = "transactions.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;

            System.out.println("date | description | vendor | amount");
            boolean found = false;

            bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 4) continue;

                LocalDate date = LocalDate.parse(parts[0].trim(), formatter);

                if (!date.isBefore(firstDayOfMonth) && !date.isAfter(today)) {
                    System.out.println(String.join(" | ", parts));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions found for Month To Date.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    private static void PreviousMonth() {
        String filePath = "transactions.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPrevMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPrevMonth = firstDayOfPrevMonth.withDayOfMonth(firstDayOfPrevMonth.lengthOfMonth());

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            System.out.println("date | description | vendor | amount");
            boolean found = false;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0].trim(), formatter);

                if (!date.isBefore(firstDayOfPrevMonth) && !date.isAfter(lastDayOfPrevMonth)) {
                    System.out.println(String.join(" | ", parts));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions found for the previous month.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }

    private static void YearToDate() {
        String filePath = "transactions.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;


            bufferedReader.readLine();

            System.out.println("date | description | vendor | amount");
            boolean found = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0].trim(), formatter);
                if (!date.isBefore(firstDayOfYear) && !date.isAfter(today)) {
                    System.out.println(String.join(" | ", parts));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No transactions found for Year To Date.");
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void PreviousYear() {
        String filePath = "transactions.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() - 1;
        LocalDate firstDayOfPrevYear = LocalDate.of(previousYear, 1, 1);
        LocalDate lastDayOfPrevYear = LocalDate.of(previousYear, 12, 31);

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            bufferedReader.readLine();

            System.out.println("date | description | vendor | amount");
            boolean found = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("\\|");

                LocalDate date = LocalDate.parse(parts[0].trim(), formatter);
                if (!date.isBefore(firstDayOfPrevYear) && !date.isAfter(lastDayOfPrevYear)) {
                    System.out.println(String.join(" | ", parts));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No transactions found for Previous Year.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }


    private static void SearchByVendor() {
        String filePath = "transactions.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter vendor name to search: ");
        String vendorSearch = scanner.nextLine().trim().toLowerCase();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            bufferedReader.readLine();

            System.out.println("date | description | vendor | amount");
            boolean found = false;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split("\\|");
                String vendor = parts[3].trim().toLowerCase();

                if (vendor.contains(vendorSearch)) {
                    System.out.println(String.join(" | ", parts));
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No transactions found for vendor: " + vendorSearch);
            }

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
