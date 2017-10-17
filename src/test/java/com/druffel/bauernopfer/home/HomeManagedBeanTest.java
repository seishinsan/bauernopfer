/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.home File:
 * HomeManagedBeanTest.java Created: Oct 2, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.home;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.game.board.Square;

@RunWith(MockitoJUnitRunner.class)
public class HomeManagedBeanTest
{

    @Spy
    HomeManagedBean classToTest;

    @Before
    public void setUp()
    {
        List<Row> rows = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            rows.add(new Row(i));
        }

        classToTest.setRows(rows);
    }
    
    @Test
    public void testPlaceBlackFigures()
    {

        classToTest.placeBlackRook();

        boolean foundPiece = false;

        for (Row row : classToTest.getRows())
        {
            for (Square square : row.getSquares())
            {
                if (square.getPiece() != null)
                {
                    foundPiece = true;
                }
            }
        }

        assertTrue(foundPiece);

    }

}
