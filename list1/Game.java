import java.util.*;
import static java.lang.Math.*;

class Game{  
    //[][] first - row, second - column
    public static void main(String args[]){   

        int board_side = 4;
        Board current_board = new Board(board_side, true);
        Board target_board = new Board(board_side, false);
        List<Integer> switch_history = new ArrayList<Integer>();
        //while(calculate_taxicabs(current_board, target_board) != 0) {
        for(int j = 0; j<300; j++) {

            current_board.print();
            make_next_move(switch_history, current_board, target_board);
        }


            for (int i=0; i< switch_history.size(); i++) {
            System.out.printf(switch_history.get(i)+", ");

            }
            System.out.println("");


    }  

    private static void make_next_move(List<Integer> switch_history, Board current_board, Board target_board) {

        List<Integer> blank_neighborhood = get_blank_neighborhood(current_board, switch_history);
        List<Integer> taxicabs = new ArrayList<Integer>();
        List<Integer> matches = new ArrayList<Integer>();
        List<Integer> h = new ArrayList<Integer>();
        
        for (int i =0; i<blank_neighborhood.size(); i++) {
            taxicabs.add(calculate_taxicab_for_case(blank_neighborhood.get(i), current_board, target_board));
            matches.add(calculate_matches_for_case(blank_neighborhood.get(i), current_board, target_board));
        }

        for (int j=0; j<matches.size(); j++) {
            h.add(matches.get(j) + taxicabs.get(j));
        }
        int minVal = Collections.min(h);
        int best_switch = h.indexOf(minVal); 

        switch_history.add(blank_neighborhood.get(best_switch));
        switch_with_blank(blank_neighborhood.get(best_switch), current_board, target_board);
        System.out.println("taxicab + matches: "+minVal);

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

    private static int calculate_matches_for_case(int value, Board current_board, Board target_board) {

            int[] blank_position = current_board.get_blank_position();
            int[] neighbor_position = get_current_position(value, current_board, target_board);
            int matches = 0;
            current_board.board[neighbor_position[0]][neighbor_position[1]] = 0;
            current_board.board[blank_position[0]][blank_position[1]] = value;
            matches = calculate_matches(current_board, target_board);
            current_board.board[neighbor_position[0]][neighbor_position[1]] = value;
            current_board.board[blank_position[0]][blank_position[1]] = 0;

            return matches;
    }
    private static List<Integer> get_blank_neighborhood(Board current_board, List<Integer> switch_history) {

        List<Integer> neighborhood = new ArrayList<>();
        List<Integer> surroundings = new ArrayList<>();

        int[] blank_position = current_board.get_blank_position();
        
        int a = 0;
        int b = 0;
        if (switch_history.size() == 1) {
            a = switch_history.get(switch_history.size()-1);
        }
        else if (switch_history.size() >= 2) {
            a = switch_history.get(switch_history.size()-1);
            b = switch_history.get(switch_history.size()-1);

        }
        int num = 0;
        if(blank_position[1]+1 <= 3) {
            num = current_board.board[blank_position[0]][blank_position[1]+1];
            if (num != a && num != b) {
                surroundings.add(num);
            }
        }
        if(blank_position[1]-1 >= 0) {
            num = current_board.board[blank_position[0]][blank_position[1]-1];
            if (num != a && num != b) {
                surroundings.add(num);
            }
        }
        if(blank_position[0]+1 <= 3) {
            num = current_board.board[blank_position[0]+1][blank_position[1]];
            if (num != a && num != b) {
                surroundings.add(num);
            }
        }
        if(blank_position[0]-1 >= 0) {
            num = current_board.board[blank_position[0]-1][blank_position[1]];
            if (num != a && num != b) {
                surroundings.add(num);
            }
        }

        return surroundings;
    }

    private static int calculate_taxicabs(Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        int sum = 0;

        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                if (current_board.board[i][j] != 0) {
                    int[] target_position = get_target_position(current_board.board[i][j], current_board, target_board);
                    sum = sum + Math.abs(i-target_position[0])+Math.abs(j-target_position[1]);
                }
            }
        }
        return sum;
    }

    private static int calculate_matches(Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        int sum = 0;

        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                if (current_board.board[i][j] == target_board.board[i][j]) {
                    sum++;
                }
            }
        }
        return 16-sum;
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

