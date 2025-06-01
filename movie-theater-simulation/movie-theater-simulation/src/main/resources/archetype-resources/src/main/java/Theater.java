package org.example;

public class Theater {
    private final Seat[][] seats;

    public Theater(int rows, int cols) {
        seats = new Seat[rows][cols];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                seats[i][j] = new Seat(i, j);
    }

    public boolean reserveSeat(int row, int col) {
        if (seats[row][col].isReserved()) {
            System.out.println("Seat is taken. Suggesting an available seat...");
            suggestSeat();
            return false;
        }
        seats[row][col].reserve();
        System.out.println("Seat reserved successfully.");
        return true;
    }

    public void cancelSeat(int row, int col) {
        if (seats[row][col].isReserved()) {
            seats[row][col].cancel();
            System.out.println("Seat reservation cancelled.");
        } else {
            System.out.println("Seat is already available.");
        }
    }

    public void suggestSeat() {
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                if (!seats[i][j].isReserved()) {
                    System.out.println("Suggested Seat: Row " + i + ", Col " + j);
                    return;
                }
            }
        }
        System.out.println("No available seats.");
    }

    public void printSeatingChart() {
        System.out.println("Seating Chart:");
        for (Seat[] row : seats) {
            for (Seat seat : row) {
                System.out.print(seat + " ");
            }
            System.out.println();
        }
    }
}
