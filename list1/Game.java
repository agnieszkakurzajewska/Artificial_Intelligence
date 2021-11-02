import java.util.*;

class Game{  
    //[][] first - row, second - column
    public static void main(String args[]){   

        int board_side = 4;
        Board current_board = new Board(board_side, true);
        current_board.print();
        Board target_board = new Board(board_side, false);
        // target_board.print();
        get_blank_neighborhood(current_board);
        calculate_taxicabs(current_board, target_board);

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
        // for (int s : surroundings)
        // System.out.println(s);
        return surroundings;
    }
// Math.abs(a)
    private static void calculate_taxicabs(Board current_board, Board target_board) {

        int side = current_board.get_board_side();
        for (int i=0; i<side; i++) {
            for (int j=0; j<side; j++) {
                System.out.println("Domyślne miejsce dla wartości");
                System.out.println(current_board.board[i][j]);
                int[] ee = get_target_position(current_board.board[i][j], current_board, target_board);
                System.out.println(ee[0]);
                                System.out.println(ee[1]);

            }
        }
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
}  

