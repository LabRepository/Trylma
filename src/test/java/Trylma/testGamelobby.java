package Trylma;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testGamelobby {

    Gamelobby gl;
    private void setup(){
        gl = new Gamelobby();
    }

    @Test
    public void rungamestateoneplayer(){
        setup();
        gl.addbot();
        gl.rungame();
        assert(!gl.getstate());
    }


    @Test
    public void rungamestatetwoplayer(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(gl.getstate());
    }

    @Test
    public void rungamestatethreeplayer(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(gl.getstate());
    }

    @Test
    public void rungamestatefourplayer(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(gl.getstate());
    }
    @Test
    public void rungamestatefiveplayer(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(!gl.getstate());
    }

    @Test
    public void testturningtrue(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(gl.isturn(Color.BLACKPAWN));
    }

    @Test
    public void testturningfalse(){
        setup();
        gl.addbot();
        gl.addbot();
        gl.rungame();
        assert(!gl.isturn(Color.WHITEPAWN));
    }

    @Test
    public void removebot(){
        setup();
        gl.addbot();
        gl.removebot();
        assertEquals(0,gl.getNoBots());
    }


}
