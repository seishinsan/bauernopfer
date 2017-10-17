/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.game.board.piece File:
 * RookTest.java Created: Oct 2, 2017 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board.piece;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.bauernopfer.util.Position;

@RunWith(MockitoJUnitRunner.class)
public class RookTest
{

    Rook classToTest = new Rook(PIECE_ID.ROOK, COLOR.BLACK);

    @Test
    public void testGenerateMoves()
    {
        List<Position> moves = classToTest.generateMoves(5, 5);
        assertEquals(4, moves.size());
        moves = classToTest.generateMoves(0, 5);
        assertEquals(3, moves.size());
        moves = classToTest.generateMoves(0, 0);
        assertEquals(2, moves.size());
    }

}
