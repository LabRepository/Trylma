package Trylma;

import static org.junit.Assert.*;

import org.junit.Test;

public class testingFields {

    @Test
    public void testSettingAStateOutOfList() {
        try {
            Fields field = new Fields("NOTALLOWEDNAME");
        } catch (RuntimeException r) {
            assertEquals("stateOutOfPossibleStatesList", r.getMessage());
        }
    }

    @Test
    public void testGetField() {
        try {
            Fields field = new Fields("WHITEPAWN");
            assertEquals("WHITEPAWN", field.getState());
        } catch (RuntimeException r) {
        }
    }
}
