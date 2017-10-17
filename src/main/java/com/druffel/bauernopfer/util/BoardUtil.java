/*
 * Project:		bauernopfer
 * Package:		com.druffel.bauernopfer.util
 * File: 		BoardUtil.java
 *
 * Created:		Oct 17, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.util;

import java.util.List;

import com.druffel.bauernopfer.game.board.Square;
import com.druffel.bauernopfer.game.board.piece.COLOR;
import com.druffel.bauernopfer.game.board.piece.Pawn;

public class BoardUtil
{

    /**
     * Returns the position of the current black figure
     * 
     * @return
     */
    public static Position getBlackFigure(List<Square> occupiedFields)
    {
        for (Square square : occupiedFields)
        {
            if (square.getPiece().getColor() == COLOR.BLACK)
            {
                return new Position(square.getIndex(), square.getRow());
            }
        }
        return null;
    }
    
    public static Position getWhiteFigure(List<Square> occupiedFields, List<Integer> lastMovedWhite)
    {
        for (Square square : occupiedFields)
        {
            if (square.getPiece().getColor() == COLOR.WHITE)
            {
                Pawn p = (Pawn) square.getPiece();
                for(Integer i : lastMovedWhite)
                {
                    if(p.getIndex() != i.intValue())
                    {
                        return new Position(square.getIndex(), square.getRow());
                    }
                }
                if(lastMovedWhite.size() == 0)
                {
                    return new Position(square.getIndex(), square.getRow());
                }
            }
        }
        return null;
    }
    
    public static boolean isGameOver(List<Square> occupiedFields)
    {
        for(Square square : occupiedFields)
        {
            if(square.getPiece().getColor() == COLOR.BLACK)
            {
                return false;
            }
        }
        return true;
    }
}
