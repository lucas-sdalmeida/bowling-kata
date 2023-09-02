package com.lucassdalmeida.bowlingkata.notdd;

public final class NormalFrame extends Frame {
    @Override
    public boolean isNotAllowedToRoll() {
        return hasAllRollsBeenPlayed() || isStrike();
    }

    @Override
    public int getMaxNumberOfRolls() {
        return 2;
    }

    @Override
    public int getMaxNumberOfKnockedDownPins() {
        return MAX_NUMBER_OF_PINS;
    }
}
