import java.util.*;

class Game{  

    public static void main(String args[]){   

        Board current_board = new Board(4, true);
        current_board.print();
        Board target_board = new Board(4, false);
        target_board.print();
        //get_blank_neighborhood(board, board_side, where_is_blank);

    }  






    private static void get_blank_neighborhood(int[][] board, int board_side, int[] where_is_blank) {
        // for (int i=0; i<board_side; i++) {
        //     for (int j=0; j<board_side; j++) {
        //         if(board[i][j] == 0) {
        //             System.out.printf("[__] ");
        //         } else {
        //             System.out.printf("[%02d] ", board[i][j]);
        //         }        
        //     }
        //     System.out.println("\n");

        // }
        int[][] neighborhood = new int[board_side][board_side];


    }

}  

