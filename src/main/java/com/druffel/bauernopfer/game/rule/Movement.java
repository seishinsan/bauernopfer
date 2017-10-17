/*
 * Project:		bauernopfer
 * Package:		com.druffel.bauernopfer.game.rule
 * File: 		Movement.java
 *
 * Created:		Oct 11, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.rule;

import java.util.List;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.game.board.piece.COLOR;
import com.druffel.bauernopfer.util.Position;

public class Movement
{

    public static boolean isFieldOccupied(Position target, List<Row> map)
    {
        return (map.get(target.getY()).getSquares().get(target.getX()).getPiece() == null) ? false : true;
    }

    public static boolean isFieldOccupiedByWhite(Position target, List<Row> map)
    {
        return (map.get(target.getY()).getSquares().get(target.getX()).getPiece() != null
                && map.get(target.getY()).getSquares().get(target.getX()).getPiece().getColor() == COLOR.WHITE) ? true : false;
    }

    public static boolean cannotCapture(Position target, List<Row> map)
    {
        return ((map.get(target.getY()).getSquares().get(target.getX()).getPiece() != null)
                && map.get(target.getY()).getSquares().get(target.getX()).getPiece().getColor() == COLOR.BLACK) ? false : true;
    }

    public static boolean fieldNextToWhite(Position target, List<Row> map)
    {

        boolean nextTo = false;

        try
        {

            if (map.get(target.getY() + 1).getSquares().get(target.getX()).getPiece() != null)
            {
                nextTo = true;
            }
            if (map.get(target.getY() - 1).getSquares().get(target.getX()).getPiece() != null)
            {
                nextTo = true;
            }
            if (map.get(target.getY()).getSquares().get(target.getX() + 1).getPiece() != null)
            {
                nextTo = true;
            }
            if (map.get(target.getY()).getSquares().get(target.getX() - 1).getPiece() != null)
            {
                nextTo = true;
            }
        }
        catch (Exception e)
        {
            // e
        }
        return nextTo;
    }

}
