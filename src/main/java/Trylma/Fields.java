package Trylma;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Basic class implementing one field on the game's board.
 * It holds the current state of the field, and checks for
 * possible errors when updating it's state.
 *
 * @author Michał Budnik
 */
class Fields {
    //ArrayList which contains the only possible states for a field.
    private ArrayList<String> allowedStates = new ArrayList<>(Arrays.asList("BLOCKED", "EMPTY", "BLACKPAWN", "WHITEPAWN",
            "YELLOWPAWN", "REDPAWN", "GREENPAWN", "BLUEPAWN"));
    private String state;

    Fields(String beginningState) {
        this.state = beginningState;
    }

    /**
     * setState checks if upcoming state is in an array of allowedStates.
     *
     * @return RuntimeException if provided state is incorrect, 0 if state changed successfully.
     */
    void setState(String newState) {
        if (!allowedStates.contains(newState)) {
            throw new RuntimeException("stateOutOfPossibleStatesList");
        }
        this.state = newState;
    }

    /**
     * @return Current state.
     */
    String getState() {
        return this.state;
    }
}
