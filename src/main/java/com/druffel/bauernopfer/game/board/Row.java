/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.game.board File:
 * Row.java Created: Sep 22, 2017 Author: AmonDruffel (Sophos Technology GmbH)
 * Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b><br>
 * Model for the a single row of the chessboard
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
public class Row
{
    private int index;

    private List<Square> squares;

    public Row(int index)
    {
        this.index = index;
        this.squares = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            squares.add(new Square(i, index, null));
        }
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public List<Square> getSquares()
    {
        return squares;
    }

    public void setSquares(List<Square> squares)
    {
        this.squares = squares;
    }

}
