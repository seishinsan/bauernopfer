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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
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
@Named("challengeFour")
@ApplicationScoped
public class ChallengeFourManagedBean
{

    private final Logger logger = Logger.getLogger(getClass().getName());

    private List<Row> rows;

    private List<Square> occupiedFields;

    private COLOR currentPlayer;

    private List<Integer> lastMovedWhite;

    private boolean autoplay;

    private int amountOfPawns;
    
    private int amountOfRounds;
    
    private int playedRounds;

    @PostConstruct
    public void init()
    {
        playedRounds = 0;
        if (amountOfPawns == 0)
        {
            amountOfPawns = 8;
        }
        
        if (amountOfRounds == 0)
        {
            amountOfRounds = 9999;
        }
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

    public void apply()
    {
        init();
    }

    public void autoplayToggle()
    {
        autoplay = (autoplay) ? false : true;
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
        int row = 4;
        for (int i = 0; i < amountOfPawns; i++)
        {
                 
            if (i < 8)
            {
                Pawn pawn = new Pawn(PIECE_ID.PAWN, COLOR.WHITE, i);
                Position pos = new Position(i, row);
                rows.get(pos.getY()).getSquares().get(pos.getX()).setPiece(pawn);
                occupiedFields.add(rows.get(pos.getY()).getSquares().get(pos.getX()));
                logger.info("Placed pawn at: X:" + pos.getX() + " Y:" + pos.getY());
            }
            else
            {
                Pawn pawn = new Pawn(PIECE_ID.PAWN, COLOR.WHITE, i);
                Position pos = new Position(i%8, new Random().nextInt(8));
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

        for (int i = currentPos.getX() + 1; i < 8; i++)
        {
            if (isSpotEmpty(new Position(i, currentPos.getY())))
            {
                emptySpots.add(new Position(i, currentPos.getY()));
            }
            else if (Movement.isFieldOccupiedByWhite(new Position(i, currentPos.getY()), rows))
            {
                break;
            }
        }

        for (int i = currentPos.getX() - 1; i > -1; i--)
        {
            if (isSpotEmpty(new Position(i, currentPos.getY())))
            {
                emptySpots.add(new Position(i, currentPos.getY()));
            }
            else if (Movement.isFieldOccupiedByWhite(new Position(i, currentPos.getY()), rows))
            {
                break;
            }
        }

        for (int i = currentPos.getY() + 1; i < 8; i++)
        {
            if (isSpotEmpty(new Position(currentPos.getX(), i)))
            {
                emptySpots.add(new Position(currentPos.getX(), i));
            }
            else if (Movement.isFieldOccupiedByWhite(new Position(currentPos.getX(), i), rows))
            {
                break;
            }
        }

        for (int i = currentPos.getY() - 1; i > -1; i--)
        {
            if (isSpotEmpty(new Position(currentPos.getX(), i)))
            {
                emptySpots.add(new Position(currentPos.getX(), i));
            }
            else if (Movement.isFieldOccupiedByWhite(new Position(currentPos.getX(), i), rows))
            {
                break;
            }
        }

        return (emptySpots.size() == 0)
                ? getPiece(BoardUtil.getBlackFigure(occupiedFields).getY(), BoardUtil.getBlackFigure(occupiedFields).getX())
                        .setOptimalMovement(getWhiteFigure(occupiedFields), BoardUtil.getBlackFigure(occupiedFields), rows)
                        .move(BoardUtil.getBlackFigure(occupiedFields).getY(), BoardUtil.getBlackFigure(occupiedFields).getX())
                : emptySpots.get(new Random().nextInt(emptySpots.size()));
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
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage("Black", "Black player is thinking."));
            Position oldPosition = BoardUtil.getBlackFigure(occupiedFields);
            if (oldPosition != null)
            {
                Piece piece = getPiece(oldPosition.getY(), oldPosition.getX());
                Position newPosition = findEmptySpot(oldPosition);
                int i = 0;
                while (Movement.isFieldOccupied(newPosition, rows) || Movement.fieldNextToWhite(newPosition, rows))
                {
                    i++;
                    newPosition = getSquare(oldPosition.getY(), oldPosition.getX()).getPiece().move(oldPosition.getY(), oldPosition.getX());
                    if(i==20)
                    {
                        restart();
                        autoplay = false;
                    }
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
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage("White", "White player is thinking."));
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

        playedRounds++;
        if (BoardUtil.isGameOver(occupiedFields) || playedRounds == amountOfRounds)
        {
            autoplay = false;
            FacesContext.getCurrentInstance().addMessage(null,  new FacesMessage("Game end", "Game came to an End."));
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

    public int getAmountOfPawns()
    {
        return amountOfPawns;
    }

    public void setAmountOfPawns(int amountOfPawns)
    {
        this.amountOfPawns = amountOfPawns;
    }

    public int getPlayedRounds()
    {
        return playedRounds;
    }

    public void setPlayedRounds(int playedRounds)
    {
        this.playedRounds = playedRounds;
    }

    public int getAmountOfRounds()
    {
        return amountOfRounds;
    }

    public void setAmountOfRounds(int amountOfRounds)
    {
        this.amountOfRounds = amountOfRounds;
    }
    
}
