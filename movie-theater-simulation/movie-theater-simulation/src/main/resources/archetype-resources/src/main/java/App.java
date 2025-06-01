package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Theater theater = new Theater(5, 5); // 5x5 seating chart

        while (true) {
            System.out.println("\n1. Reserve Seat\n2. Cancel Seat\n3. View Seating Chart\n4. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter row and column to reserve: ");
                    int row = scanner.nextInt();
                    int col = scanner.nextInt();
                    theater.reserveSeat(row, col);
                    break;
                case 2:
                    System.out.print("Enter row and column to cancel: ");
                    row = scanner.nextInt();
                    col = scanner.nextInt();
                    theater.cancelSeat(row, col);
                    break;
                case 3:
                    theater.printSeatingChart();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
