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

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.util.Position;

public class Queen implements Piece
{

    private PIECE_ID id;

    private COLOR color;

    private final String image_white = "/resources/img/black_rook_white.jpg";

    private final String image_black = "/resources/img/black_rook_black.jpg";

    private final int moveSpeed = 1;

    private char optimalDirection;

    public Queen(PIECE_ID id, COLOR color)
    {
        this.setId(id);
        this.color = color;
        optimalDirection = 'n';
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
        int move = new SecureRandom().nextInt(moves.size());
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
        List<Position> moves = new ArrayList<>();

        if (currentRow + 1 < 8 && optimalDirection == 's')
        {
            for (int i = currentRow + 1; i < 8; i++)
            {
                moves.add(new Position(currentSquare, i));
            }

        }

        if (currentRow - 1 > -1 && optimalDirection == 'n')
        {
            for (int i = currentRow - 1; i > -1; i--)
            {
                moves.add(new Position(currentSquare, i));
            }
        }

        if (currentSquare + 1 < 8)
        {
            for (int i = currentSquare + 1; i < 8; i++)
            {
                moves.add(new Position(i, currentRow));
            }
        }

        if (currentSquare - 1 > -1)
        {
            for (int i = currentSquare - 1; i > -1; i--)
            {
                moves.add(new Position(i, currentRow));
            }

        }

        return moves;
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
    public Piece setOptimalMovement(Position enemyPos, Position myPosition, List<Row> map)
    {
        if (enemyPos.getY() > myPosition.getY())
        {
            setOptimalDirection('n');
        }
        else
        {
            setOptimalDirection('s');
        }
        return this;
    }

    private void setOptimalDirection(char c)
    {
        this.optimalDirection = c;

    }

    @Override
    public Piece setOptimalMovementSimple(Position blackPos, Position myPosition, List<Row> map)
    {
        // TODO Auto-generated method stub
        return null;
    }

    public int getMoveSpeed()
    {
        return moveSpeed;
    }

}
