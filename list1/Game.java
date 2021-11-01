import java.util.*;

class Game{  

    public static void main(String args[]){   

        int board_side = 4;
        List<Integer> permutation = generate_permutation(board_side);
        int[][] board = fill_board(board_side, permutation);
        print_board(board, board_side);

    }  

    private static List<Integer> generate_permutation(int board_side) {

        List<Integer> permutation = new ArrayList<>();

        for (int i = 0; i < Math.pow(board_side, 2); i++) {
            permutation.add(i);
        }

        Collections.shuffle(permutation); 
        return permutation; 
    }

    private static int[][] fill_board(int board_side, List<Integer> permutation) {

        int[][] board = new int[board_side][board_side];
        int permutation_counter = 0;

        for (int i=0; i<board_side; i++) {
            for (int j=0; j<board_side; j++) {
                if (i == board_side-1 && j == board_side-1) {
                    board[i][j] = 0;
                }
                board[i][j] = permutation.get(permutation_counter++);
            }
        }

        return board;
    }

        private static void print_board(int[][] board, int board_side) {

        System.out.println("\n");

        for (int i=0; i<board_side; i++) {

            for (int j=0; j<board_side; j++) {
                System.out.printf("[%02d] ", board[i][j]);
            }
            System.out.println("\n");

        }
    }
}  

