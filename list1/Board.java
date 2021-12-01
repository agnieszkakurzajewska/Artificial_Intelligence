import java.util.*;

class Board{  


    int board_side;
    int []where_is_blank = new int[2]; 
    int[][] board;

    public Board(int side, boolean should_be_mixed) {
        this.board_side = side;
        this.where_is_blank[0] = this.where_is_blank[1] = 3;
        this.board = fill_board_random(board_side, generate_permutation(board_side, should_be_mixed));
    }

    private static List<Integer> generate_permutation(int board_side, boolean should_be_mixed) {

        List<Integer> permutation = new ArrayList<>();

        for (int i = 1; i < Math.pow(board_side, 2); i++) {
            permutation.add(i);
        }
        if (should_be_mixed) {
            Collections.shuffle(permutation);
        }

        return permutation; 
    }

    private static int[][] fill_board_random(int board_side, List<Integer> permutation) {

        int[][] board = new int[board_side][board_side];
        int permutation_counter = 0;

        for (int i=0; i<board_side; i++) {
            for (int j=0; j<board_side; j++) {
                if (i == board_side-1 && j == board_side-1) {
                    board[i][j] = 0;
                } else {
                    board[i][j] = permutation.get(permutation_counter++);
                }
            }
        }
        return board;
    }

    public void print() {

        System.out.println("\n");

        for (int i=0; i<this.board_side; i++) {
            for (int j=0; j<this.board_side; j++) {
                if(board[i][j] == 0) {
                    System.out.printf("[__] ");
                } else {
                    System.out.printf("[%02d] ", board[i][j]);
                }        
            }
            System.out.println("\n");
        }
    }


    public int[] get_blank_position() {

        int[] blank_position = {3, 3};
        for (int i=0; i<get_board_side(); i++) {
            for (int j=0; j<get_board_side(); j++) {
                if (board[i][j] == 0) {
                    blank_position[0] = i;
                    blank_position[1] = j;
                }
            }
        }  
        return blank_position;
    } 

    public int get_board_side() {
        return this.board_side;
    }
}