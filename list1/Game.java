import java.util.*;
import static java.lang.Math.*;
import java.util.Random;

class Game{  
    //[][] first - row, second - column
    public static void main(String args[]){   

        int board_side = 4;
        Board target_board = new Board(board_side, false);
        Board mixed_board = new Board(board_side, false);
        List<Integer> switch_history1 = new ArrayList<Integer>();
        List<Integer> switch_history2 = new ArrayList<Integer>();

        //mixing
        mix_x_times(15, mixed_board, switch_history1, target_board);
        System.out.println("-------------------------------------------------");

        //unmixing xD
        int taxicabs = 1;
        while (taxicabs != 0) {
            taxicabs = make_next_move(switch_history2, mixed_board, target_board);
            //System.out.println("taxicabs: "+taxicabs);
            //mixed_board.print();
        }
        print_switch_history(switch_history1);
        print_switch_history(switch_history2);

    }  

    private static void mix_x_times(int how_many_times, Board mixed_board,  List<Integer> switch_history, Board target_board) {
        for(int j = 0; j<how_many_times; j++) {
            //mixed_board.print();
            mix(switch_history, mixed_board, target_board);
        }
    }

    private static void mix(List<Integer> switch_history, Board mixed_board, Board target_board) {

        Random rand = new Random();
        List<Integer> blank_neighborhood = get_blank_neighborhood(mixed_board, switch_history);
        int random = rand.nextInt(blank_neighborhood.size());
        switch_history.add(blank_neighborhood.get(random));
        switch_with_blank(blank_neighborhood.get(random), mixed_board, target_board);
    }


    private static void switch_with_blank(int value, Board current_board, Board target_board) {

            int[] blank_position = current_board.get_blank_position();
            int[] neighbor_position = get_current_position(value, current_board, target_board);
            current_board.board[neighbor_position[0]][neighbor_position[1]] = 0;
            current_board.board[blank_position[0]][blank_position[1]] = value;
    }


    private static int make_next_move(List<Integer> switch_history, Board current_board, Board target_board) {

        List<Integer> blank_neighborhood = get_blank_neighborhood(current_board, switch_history);
        List<Integer> taxicabs = new ArrayList<Integer>();
        List<Integer> taxicabs_plus = new ArrayList<Integer>();

        List<Integer> matches = new ArrayList<Integer>();
        List<Integer> h = new ArrayList<Integer>();
        
        for (int i =0; i<blank_neighborhood.size(); i++) {
            taxicabs.add(calculate_taxicab_for_case(false, blank_neighborhood.get(i), current_board, target_board));
            taxicabs_plus.add(calculate_taxicab_for_case(true, blank_neighborhood.get(i), current_board, target_board));
            matches.add(calculate_matches_for_case(blank_neighborhood.get(i), current_board, target_board));
        }

        for (int j=0; j<matches.size(); j++) {
            //h.add(taxicabs.get(j));
            h.add(matches.get(j));
            //h.add(matches.get(j) + taxicabs.get(j));
            //h.add(matches.get(j) + taxicabs_plus.get(j));

        }
        
        int minVal = Collections.min(h);
        int best_switch = h.indexOf(minVal); 

        switch_history.add(blank_neighborhood.get(best_switch));
        switch_with_blank(blank_neighborhood.get(best_switch), current_board, target_board);
        return minVal;

    }


    private static int calculate_taxicab_for_case(boolean with_right_el_check,int value, Board current_board, Board target_board) {

            int[] blank_position = current_board.get_blank_position();
            int[] neighbor_position = get_current_position(value, current_board, target_board);
            int taxicab = 0;
            current_board.board[neighbor_position[0]][neighbor_position[1]] = 0;
            current_board.board[blank_position[0]][blank_position[1]] = value;
            if (with_right_el_check){
                taxicab = calculate_taxicabs_plus(current_board, target_board);
            } else {
                taxicab = calculate_taxicabs(current_board, target_board);
            }
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
        if (switch_history.size() > 0) {
            a = switch_history.get(switch_history.size()-1);
        }
        int num = 0;
        if(blank_position[1]+1 <= 3) {
            num = current_board.board[blank_position[0]][blank_position[1]+1];
            if (num != a) {
                surroundings.add(num);
            }
        }
        if(blank_position[1]-1 >= 0) {
            num = current_board.board[blank_position[0]][blank_position[1]-1];
            if (num != a) {
                surroundings.add(num);
            }
        }
        if(blank_position[0]+1 <= 3) {
            num = current_board.board[blank_position[0]+1][blank_position[1]];
            if (num != a) {
                surroundings.add(num);
            }
        }
        if(blank_position[0]-1 >= 0) {
            num = current_board.board[blank_position[0]-1][blank_position[1]];
            if (num != a) {
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

        private static int calculate_taxicabs_plus(Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        int sum = 0;

        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                if (current_board.board[i][j] != 0) {
                    int[] target_position = get_target_position(current_board.board[i][j], current_board, target_board);
                    sum = sum + Math.abs(i-target_position[0])+Math.abs(j-target_position[1]);
                
                    if (i+1<side) {
                        int[] target_position_right = get_target_position(current_board.board[i+1][j], current_board, target_board);
                        if (i+1 == target_position_right[0] && j == target_position_right[1]) {
                            sum = sum-1;                   
                        }
                    }
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

    private static void  print_switch_history (List<Integer> switch_history) {
        System.out.printf(switch_history.size()+" switches: \n");
        for (int i=0; i< switch_history.size(); i++) {
            System.out.printf(switch_history.get(i)+" ");
        }
        System.out.printf("\n\n");
    }

}  

