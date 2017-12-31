package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;

//RUN SERVER!
public class testGamelobby {

    Gamelobby gl;
    private void setup(){
        gl = new Gamelobby();
    }

    @Test
    public void rungamestateoneBot(){
        setup();
        gl.addbot();
        gl.rungame();
        assertFalse(gl.getstate());
    }


    @Test
    public void rungamestatetwoBot(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assertFalse(gl.getstate());
    }

    @Test
    public void rungamestatethreeBot(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assertFalse(gl.getstate());
    }

    @Test
    public void rungamestatefourBot(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assertFalse(gl.getstate());
    }
    @Test
    public void rungamestatefiveBot(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assertFalse(gl.getstate());
    }

    @Test
    public void removebot(){
        setup();
        gl.addbot();
        gl.removebot();
        assertEquals(0,gl.getNoBots());
    }

    @Test
    public void singletontest(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        Game g1 = gl.game;
        gl.rungame();
        Game g2 = gl.game;
        assertSame(g1,g2);
    }


}
