/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.game.board.piece File:
 * Rook.java Created: Sep 22, 2017 Author: AmonDruffel (Sophos Technology GmbH)
 * Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board.piece;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.util.Position;

public class Rook implements Piece
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    private PIECE_ID id;

    private COLOR color;

    private final String image_white = "/resources/img/black_rook_white.jpg";

    private final String image_black = "/resources/img/black_rook_black.jpg";

    private final int moveSpeed = 1;

    private char optimalDirection;

    public Rook(PIECE_ID id, COLOR color)
    {
        this.setId(id);
        this.color = color;
        optimalDirection = 'n';
    }

    @Override
    public Position move(int currentRow, int currentSquare)
    {
        List<Position> moves = generateMoves(currentRow, currentSquare);
        int move = new SecureRandom().nextInt(moves.size());
        logger.info("Possible moves black: " + moves.size());
        logger.info("" + move);
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
            for(int i = currentRow+1; i < 8; i++)
            {
                moves.add(new Position(currentSquare, i));
            }
            
        }

        if (currentRow - 1 > -1 && optimalDirection == 'n')
        {
            for(int i = currentRow-1; i > -1; i--)
            {
                moves.add(new Position(currentSquare, i));
            }
        }

        if (currentSquare + 1 < 8)
        {
            for(int i = currentSquare+1; i < 8; i++)
            {
                moves.add(new Position(i, currentRow));
            }
        }

        if (currentSquare - 1 > -1)
        {
            for(int i = currentSquare-1; i > -1; i--)
            {
                moves.add(new Position(i, currentRow));
            }
            
        }

        return moves;
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

    public PIECE_ID getId()
    {
        return id;
    }

    public void setId(PIECE_ID id)
    {
        this.id = id;
    }

    @Override
    public COLOR getColor()
    {
        return color;
    }

    public String getImage_white()
    {
        return image_white;
    }

    public String getImage_black()
    {
        return image_black;
    }

    public int getMoveSpeed()
    {
        return moveSpeed;
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
        // TODO Auto-generated method stub
        return null;
    }


}
