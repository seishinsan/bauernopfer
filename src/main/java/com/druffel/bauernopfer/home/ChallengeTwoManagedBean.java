/*
 * Project: bauernopfer Package: com.druffel.bauernopfer.home File:
 * HomeManagedBean.java Created: Sep 22, 2017 Author: AmonDruffel (Sophos
 * Technology GmbH) Copyright: (C) 2017 Sophos Technology GmbH
 */
package com.druffel.bauernopfer.home;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import com.druffel.bauernopfer.game.board.Row;
import com.druffel.bauernopfer.game.board.Square;
import com.druffel.bauernopfer.game.board.piece.COLOR;
import com.druffel.bauernopfer.game.board.piece.PIECE_ID;
import com.druffel.bauernopfer.game.board.piece.Pawn;
import com.druffel.bauernopfer.game.board.piece.Piece;
import com.druffel.bauernopfer.game.board.piece.Rook;
import com.druffel.bauernopfer.game.rule.Movement;
import com.druffel.bauernopfer.util.BoardUtil;
import com.druffel.bauernopfer.util.Position;

/**
 * <b>Description:</b><br>
 * provides information for the home view. Displays the Chessboard and stores
 * game logic
 *
 * @author AmonDruffel, &copy; 2017 Sophos Technology GmbH
 */
@Named("challengeTwo")
@ApplicationScoped
public class ChallengeTwoManagedBean
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    private List<Row> rows;

    private List<Square> occupiedFields;

    private COLOR currentPlayer;

    private List<Integer> lastMovedWhite;

    private boolean autoplay;

    @PostConstruct
    public void init()
    {
        setAutoplay(false);
        currentPlayer = COLOR.WHITE;
        occupiedFields = new ArrayList<>();

        this.rows = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        {
            rows.add(new Row(i));
        }

        lastMovedWhite = new ArrayList<>();

        placeFigures();
    }

    public void autoplayToggle()
    {
        autoplay = (autoplay) ? false:true;
    }
    
    public void restart()
    {
        init();
    }

    protected void placeFigures()
    {
        logger.info("Initializing Board");
        placeWhitePawns();
        placeBlackRook();
    }

    /**
     * Placing all black figures on the board in a straight line
     */
    protected void placeWhitePawns()
    {
        logger.info("Placing white pawns");
        int row = new Random().nextInt(8);
        int emptySpot = new Random().nextInt(8);
        for (int i = 0; i < 8; i++)
        {
            if (i != emptySpot)
            {
                Pawn pawn = new Pawn(PIECE_ID.PAWN, COLOR.WHITE, i);
                Position pos = new Position(i, row);
                rows.get(pos.getY()).getSquares().get(pos.getX()).setPiece(pawn);
                occupiedFields.add(rows.get(pos.getY()).getSquares().get(pos.getX()));
                logger.info("Placed pawn at: X:" + pos.getX() + " Y:" + pos.getY());
            }
        }
    }

    /**
     * Placing all black figures on the board, on a random position
     */
    protected void placeBlackRook()
    {
        logger.info("Placing Black rook");
        Rook rook = new Rook(PIECE_ID.ROOK, COLOR.BLACK);

        Position newPosition = new Position(new Random().nextInt(8), new Random().nextInt(8));
        while (Movement.isFieldOccupied(newPosition, rows) || Movement.fieldNextToWhite(newPosition, rows))
        {
            newPosition = new Position(new Random().nextInt(8), new Random().nextInt(8));
        }

        rows.get(newPosition.getY()).getSquares().get(newPosition.getX()).setPiece(rook);
        occupiedFields.add(rows.get(newPosition.getY()).getSquares().get(newPosition.getX()));
        logger.info("Placed rook at: X:" + newPosition.getX() + " Y:" + newPosition.getY());
    }

    /**
     * returns specified square
     * 
     * @param row
     * @param square
     * @return
     */
    protected Square getSquare(int row, int square)
    {
        return rows.get(row).getSquares().get(square);
    }

    /**
     * remove piece from specified square
     * 
     * @param row
     * @param square
     */
    protected void removePiece(int row, int square)
    {
        rows.get(row).getSquares().get(square).setPiece(null);
        occupiedFields.remove(rows.get(row).getSquares().get(square));
    }

    protected void setNewPiecePosition(int row, int square, Piece piece)
    {
        rows.get(row).getSquares().get(square).setPiece(piece);
    }

    protected Piece getPiece(int row, int square)
    {
        return getSquare(row, square).getPiece();
    }

    protected Position findEmptySpot(Position currentPos)
    {
        List<Position> emptySpots = new ArrayList<>();
        for (Square square : rows.get(currentPos.getY()).getSquares())
        {
            if (isSpotEmpty(new Position(square.getIndex(), square.getRow())))
            {
                emptySpots.add(new Position(square.getIndex(), square.getRow()));
            }
        }

        for (int i = 0; i < 8; i++)
        {
            if (isSpotEmpty(new Position(currentPos.getX(), i)))
            {
                emptySpots.add(new Position(currentPos.getX(), i));
            }
        }

        return emptySpots.get(new Random().nextInt(emptySpots.size()));
    }

    protected boolean isSpotEmpty(Position target)
    {
        boolean empty = true;

        for (int i = target.getX(); i < 8; i++)
        {
            if (Movement.isFieldOccupiedByWhite(new Position(i, target.getY()), rows))
            {
                System.out.println(getPiece(target.getY(), i));
                empty = false;
            }
        }

        for (int i = target.getY() + 1; i < 8; i++)
        {
            if (Movement.isFieldOccupiedByWhite(new Position(target.getX(), i), rows))
            {
                empty = false;
            }

        }

        for (int i = target.getY() - 1; i > -1; i--)
        {
            if (Movement.isFieldOccupiedByWhite(new Position(target.getX(), i), rows))
            {
                empty = false;
            }

        }

        return empty;
    }

    public static Position getWhiteFigure(List<Square> occupiedFields)
    {
        Position nearestPosition = null;
        int distance = -999;
        for (Square square : occupiedFields)
        {
            if (square.getPiece().getColor() == COLOR.WHITE)
            {
                if (square.getRow() - BoardUtil.getBlackFigure(occupiedFields).getY() == -1
                        || square.getRow() - BoardUtil.getBlackFigure(occupiedFields).getY() == 1
                        || square.getIndex() - BoardUtil.getBlackFigure(occupiedFields).getX() == 1
                        || square.getIndex() - BoardUtil.getBlackFigure(occupiedFields).getX() == -1)
                {
                    return new Position(square.getIndex(), square.getRow());
                }

                int tempDistance = 0 - (BoardUtil.getBlackFigure(occupiedFields).getX() - square.getIndex());
                if (tempDistance > distance || tempDistance == 0)
                {
                    distance = tempDistance;
                    nearestPosition = new Position(square.getIndex(), square.getRow());
                    if (tempDistance == 0)
                    {
                        return nearestPosition;
                    }
                }
            }
        }
        return nearestPosition;
    }

    /**
     * Moves player to next position
     */
    public void nextMove()
    {

        // BLACK PLAYER
        if (currentPlayer == COLOR.BLACK)
        {
            Position oldPosition = BoardUtil.getBlackFigure(occupiedFields);
            if (oldPosition != null)
            {
                Piece piece = getPiece(oldPosition.getY(), oldPosition.getX());
                Position newPosition = findEmptySpot(oldPosition);
                while (Movement.isFieldOccupied(newPosition, rows) || Movement.fieldNextToWhite(newPosition, rows))
                {
                    newPosition = getSquare(oldPosition.getY(), oldPosition.getX()).getPiece().move(oldPosition.getY(), oldPosition.getX());
                }
                removePiece(oldPosition.getY(), oldPosition.getX());
                setNewPiecePosition(newPosition.getY(), newPosition.getX(), piece);
                occupiedFields.add(getSquare(newPosition.getY(), newPosition.getX()));
                currentPlayer = COLOR.WHITE;
                logger.info("BLACK moved from Row:" + oldPosition.getY() + " Square:" + oldPosition.getX() + "to Row:" + newPosition.getY() + " Square:"
                        + newPosition.getX());
            }
        }
        // WHITE PLAYER
        else
        {
            Position oldPosition = getWhiteFigure(occupiedFields);
            if (oldPosition != null)
            {
                Piece piece = getPiece(oldPosition.getY(), oldPosition.getX());
                Position newPosition = getSquare(oldPosition.getY(), oldPosition.getX()).getPiece()
                        .setOptimalMovement(BoardUtil.getBlackFigure(occupiedFields), oldPosition, rows).move(oldPosition.getY(), oldPosition.getX());
                while (Movement.isFieldOccupiedByWhite(newPosition, rows) && Movement.cannotCapture(newPosition, rows))
                {
                    newPosition = getSquare(oldPosition.getY(), oldPosition.getX()).getPiece().move(oldPosition.getY(), oldPosition.getX());
                }
                removePiece(oldPosition.getY(), oldPosition.getX());
                setNewPiecePosition(newPosition.getY(), newPosition.getX(), piece);
                occupiedFields.add(getSquare(newPosition.getY(), newPosition.getX()));
                currentPlayer = COLOR.BLACK;
                logger.info("WHITE moved from Row:" + oldPosition.getY() + " Square:" + oldPosition.getX() + "to Row:" + newPosition.getY() + " Square:"
                        + newPosition.getX());
                if (lastMovedWhite.size() == 7)
                {
                    lastMovedWhite = new ArrayList<>();
                }
                Pawn p = (Pawn) piece;
                lastMovedWhite.add(p.getIndex());
            }
        }

    }

    public List<Row> getRows()
    {
        return rows;
    }

    public void setRows(List<Row> rows)
    {
        this.rows = rows;
    }

    public List<Square> getOccupiedFields()
    {
        return occupiedFields;
    }

    public void setOccupiedFields(List<Square> occupiedFields)
    {
        this.occupiedFields = occupiedFields;
    }

    public List<Integer> getLastMovedWhite()
    {
        return lastMovedWhite;
    }

    public void setLastMovedWhite(List<Integer> lastMovedWhite)
    {
        this.lastMovedWhite = lastMovedWhite;
    }

    public boolean isAutoplay()
    {
        return autoplay;
    }

    public void setAutoplay(boolean autoplay)
    {
        this.autoplay = autoplay;
    }

}
