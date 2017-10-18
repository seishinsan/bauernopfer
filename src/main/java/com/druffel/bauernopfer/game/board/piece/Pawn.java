/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.game.board.piece File:
 * Pawn.java Created: Sep 22, 2017 Author: AmonDruffel (Sophos Technology GmbH)
 * Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board.piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.game.rule.Movement;
import com.druffel.bauernopfer.util.Position;

public class Pawn implements Piece
{

    private PIECE_ID id;

    private COLOR color;

    private final String image_white = "/resources/img/white_pawn_white.jpg";

    private final String image_black = "/resources/img/white_pawn_black.jpg";

    private int index;

    private char optimalDirection;

    public Pawn(PIECE_ID id, COLOR color, int i)
    {
        this.setId(id);
        this.color = color;
        this.index = i;
        optimalDirection = 'n';
    }

    @Override
    public Piece setOptimalMovement(Position enemyPos, Position myPosition, List<Row> map)
    {
        if (enemyPos.getX() > myPosition.getX())
        {
            optimalDirection = 'e';
        }
        else if (enemyPos.getX() < myPosition.getX())
        {
            optimalDirection = 'w';
        }
        else
        {
            if (enemyPos.getY() > myPosition.getY())
            {
                optimalDirection = 's';
            }
            else
            {
                optimalDirection = 'n';
            }
        }

        if (optimalDirection == 'e' || optimalDirection == 'w')
        {
            if (Movement.isFieldOccupied(move(myPosition.getY(), myPosition.getX()), map))
            {
                if (enemyPos.getY() > myPosition.getY())
                {
                    optimalDirection = 's';
                }
                else
                {
                    optimalDirection = 'n';
                }
            }
        }

        return this;
    }

    @Override
    public Position move(int currentRow, int currentSquare)
    {
        List<Position> moves = generateMoves(currentRow, currentSquare);

        int move = new Random().nextInt(moves.size());
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
            moves.add(new Position(currentSquare, currentRow + 1));
        }

        if (currentRow - 1 > -1 && optimalDirection == 'n')
        {
            moves.add(new Position(currentSquare, currentRow - 1));
        }

        if (currentSquare + 1 < 8 && optimalDirection == 'e')
        {
            moves.add(new Position(currentSquare + 1, currentRow));
        }

        if (currentSquare - 1 > -1 && optimalDirection == 'w')
        {
            moves.add(new Position(currentSquare - 1, currentRow));
        }

        return moves;
    }

    @Override
    public COLOR getColor()
    {
        return color;
    }

    public String getImage_black()
    {
        return image_black;
    }

    public String getImage_white()
    {
        return image_white;
    }

    public PIECE_ID getId()
    {
        return id;
    }

    public void setId(PIECE_ID id)
    {
        this.id = id;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public char getOptimalDirection()
    {
        return optimalDirection;
    }

    public void setOptimalDirection(char optimalDirection)
    {
        this.optimalDirection = optimalDirection;
    }

    @Override
    public Piece setOptimalMovementSimple(Position blackPos, Position myPosition, List<Row> map)
    {
        if (blackPos.getY() > myPosition.getY())
        {
            optimalDirection = 's';
        }
        else
        {
            optimalDirection = 'n';
        }

        return this;
    }

}
