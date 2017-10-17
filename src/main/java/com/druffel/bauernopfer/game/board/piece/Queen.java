/*
 * Project:		bauernopfer
 * Package:		com.druffel.bauernopfer.game.board.piece
 * File: 		Queen.java
 *
 * Created:		Oct 11, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board.piece;

import java.util.List;
import java.util.Random;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.util.Position;

public class Queen implements Piece
{
    
    private PIECE_ID id;

    private COLOR color;

    private final String image_white = "/resources/img/black_rook_white.jpg";

    private final String image_black = "/resources/img/black_rook_black.jpg";

    public Queen(PIECE_ID id, COLOR color)
    {
        this.setId(id);
        this.color = color;
    }
    
    @Override
    public COLOR getColor()
    {
        return color;
    }

    @Override
    public Position move(int currentRow, int currentSquare)
    {
        List<Position> moves = generateMoves(currentRow, currentSquare);
        int move = new Random().nextInt(moves.size());
        move = (move == 0) ? 1:move;
        for (int i = 0; i < moves.size(); i++)
        {
            if (i == move)
            {
                return moves.get(i);
            }
        }
        return null;
    }

    @Override
    public List<Position> generateMoves(int currentRow, int currentSquare)
    {
        return null;
    }

    public PIECE_ID getId()
    {
        return id;
    }

    public void setId(PIECE_ID id)
    {
        this.id = id;
    }

    public String getImage_white()
    {
        return image_white;
    }

    public String getImage_black()
    {
        return image_black;
    }

   
    @Override
    public Piece setOptimalMovement(Position blackPos, Position myPosition, List<Row> map)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Piece setOptimalMovementSimple(Position blackPos, Position myPosition, List<Row> map)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
