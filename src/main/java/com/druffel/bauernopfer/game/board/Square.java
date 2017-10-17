/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.game.board File:
 * Square.java Created: Sep 22, 2017 Author: AmonDruffel (Sophos Technology
 * GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board;

import com.druffel.bauernopfer.game.board.piece.Piece;

/**
 * <b>Description:</b><br>
 * Model for every square in a row
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
public class Square
{

    private int index;

    private int row;

    private Piece piece;

    public Square(int index, int row, Piece piece)
    {
        this.row = row;
        this.index = index;
        this.piece = piece;
    }

    /**
     * @return True if Square is white, False if square is black
     */
    public boolean isWhite()
    {
        if ((index % 2 == 0 && row % 2 == 0) || (index % 2 == 1 && row % 2 == 1))
        {
            return true;
        }
        return false;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public int getRow()
    {
        return row;
    }

    public void setRow(int row)
    {
        this.row = row;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void setPiece(Piece piece)
    {
        this.piece = piece;
    }

}
