import java.util.*;
import static java.lang.Math.*;

class Game{  
    //[][] first - row, second - column
    public static void main(String args[]){   

        int board_side = 4;
        Board current_board = new Board(board_side, true);
        Board target_board = new Board(board_side, false);
        
        while(calculate_taxicabs(current_board, target_board) != 0) {
            current_board.print();
            make_next_move(current_board, target_board);
        }

            //         current_board.print();
            // make_next_move(current_board, target_board);
            //             current_board.print();
            // make_next_move(current_board, target_board);
            //              current_board.print();
            // make_next_move(current_board, target_board);
            //             current_board.print();
            // make_next_move(current_board, target_board);
            //             current_board.print();
            // make_next_move(current_board, target_board);
            //             current_board.print();
            // make_next_move(current_board, target_board);

    }  

    private static void make_next_move(Board current_board, Board target_board) {

        List<Integer> blank_neighborhood = get_blank_neighborhood(current_board);
        // for (int m = 0; m< blank_neighborhood.size(); m++) {
        //     System.out.println(blank_neighborhood.get(m));

        // }
        List<Integer> taxicabs = new ArrayList<Integer>();

        for (int i =0; i<blank_neighborhood.size(); i++) {
                    taxicabs.add(calculate_taxicab_for_case(blank_neighborhood.get(i), current_board, target_board));
        }
        int minVal = Collections.min(taxicabs); // should return 7
        int best_switch = taxicabs.indexOf(minVal); 
        switch_with_blank(blank_neighborhood.get(best_switch), current_board, target_board);
    }

    private static void switch_with_blank(int value, Board current_board, Board target_board) {

            int[] blank_position = current_board.get_blank_position();
            int[] neighbor_position = get_current_position(value, current_board, target_board);
            current_board.board[neighbor_position[0]][neighbor_position[1]] = 0;
            current_board.board[blank_position[0]][blank_position[1]] = value;
    }

    private static int calculate_taxicab_for_case(int value, Board current_board, Board target_board) {

            int[] blank_position = current_board.get_blank_position();
            int[] neighbor_position = get_current_position(value, current_board, target_board);
            int taxicab = 0;
            current_board.board[neighbor_position[0]][neighbor_position[1]] = 0;
            current_board.board[blank_position[0]][blank_position[1]] = value;
            taxicab = calculate_taxicabs(current_board, target_board);
            current_board.board[neighbor_position[0]][neighbor_position[1]] = value;
            current_board.board[blank_position[0]][blank_position[1]] = 0;

            return taxicab;
    }

    private static List<Integer> get_blank_neighborhood(Board current_board) {

        List<Integer> neighborhood = new ArrayList<>();
        List<Integer> surroundings = new ArrayList<>();

        int[] blank_position = current_board.get_blank_position();
        
        if(blank_position[1]+1 <= 3) {
            surroundings.add(current_board.board[blank_position[0]][blank_position[1]+1]);
        }
        if(blank_position[1]-1 >= 0) {
            surroundings.add(current_board.board[blank_position[0]][blank_position[1]-1]);
        }
        if(blank_position[0]+1 <= 3) {
            surroundings.add(current_board.board[blank_position[0]+1][blank_position[1]]);
        }
        if(blank_position[0]-1 >= 0) {
            surroundings.add(current_board.board[blank_position[0]-1][blank_position[1]]);
        }

        return surroundings;
    }

    private static int calculate_taxicabs(Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        int sum = 0;

        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                int[] target_position = get_target_position(current_board.board[i][j], current_board, target_board);
                sum = sum + Math.abs(i-target_position[0])+Math.abs(j-target_position[1]);
            }
        }
        return sum;
    }

    private static int[] get_target_position(int value, Board current_board, Board target_board) {

        int[] target_position = {0, 0};
        for (int i=0; i<target_board.get_board_side(); i++) {
            for (int j=0; j<target_board.get_board_side(); j++) {
                if (target_board.board[i][j] == value){
                    target_position[0] = i;
                    target_position[1] = j;
                }
            }
        }
        return target_position;
    }


    private static int[] get_current_position(int value, Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        int[] current_position = {0, 0};

        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                if (current_board.board[i][j] == value){
                    current_position[0] = i;
                    current_position[1] = j;
                }
            }
        }
        return current_position;
    }
}  

