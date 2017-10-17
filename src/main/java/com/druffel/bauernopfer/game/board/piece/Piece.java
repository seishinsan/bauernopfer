/*
 * Project:		bauernopfer
 * Package:		com.druffel.bauernopfer.game.board
 * File: 		Piece.java
 *
 * Created:		Sep 22, 2017
 * Author:		AmonDruffel (Sophos Technology GmbH)
 * Copyright:	(C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.game.board.piece;

import java.util.List;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.util.Position;

public interface Piece
{
    public abstract COLOR getColor();
    public abstract Position move(int currentRow, int currentSquare);
    public abstract List<Position> generateMoves(int currentRow, int currentSquare); 
    public abstract Piece setOptimalMovement(Position blackPos, Position myPosition, List<Row> map);
    public abstract Piece setOptimalMovementSimple(Position blackPos, Position myPosition, List<Row> map);
}
