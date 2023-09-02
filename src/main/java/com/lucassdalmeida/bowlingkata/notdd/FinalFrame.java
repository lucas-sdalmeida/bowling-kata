package com.lucassdalmeida.bowlingkata.notdd;

public final class FinalFrame extends Frame {
    @Override
    public boolean isNotAllowedToRoll() {
        return (isStrike() || isSpare()) && hasAllRollsBeenPlayed() || getNumberOfRolls() == 2;
    }

    @Override
    public int getMaxNumberOfRolls() {
        return 3;
    }

    @Override
    public int getMaxNumberOfKnockedDownPins() {
        return MAX_NUMBER_OF_PINS * 3;
    }
}
