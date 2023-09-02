package com.lucassdalmeida.bowlingkata.notdd;

import java.util.Arrays;
import java.util.Objects;

public abstract class Frame {
    public static  final int MAX_NUMBER_OF_PINS = 10;
    private final int[] knockedDownPinsPerRoll = new int[getMaxNumberOfRolls()];
    private int numberOfRolls = 0;
    private int bonusPoints = 0;

    public abstract int getMaxNumberOfRolls();

    public abstract int getMaxNumberOfKnockedDownPins();

    public final int getScore() {
        return getTotalNumberOfKnockedDownPins() + bonusPoints;
    }

    public abstract boolean isNotAllowedToRoll();

    public boolean hasAllRollsBeenPlayed() {
        return numberOfRolls == getMaxNumberOfRolls();
    }

    public final boolean isStrike() {
        return numberOfRolls == 1 && hasAllPinsBeenKnockedDown();
    }

    public final boolean isSpare() {
        return numberOfRolls == 2 && hasAllPinsBeenKnockedDown();
    }

    private boolean hasAllPinsBeenKnockedDown() {
        return getTotalNumberOfKnockedDownPins() == getMaxNumberOfKnockedDownPins();
    }

    public final void roll(int knockedDownPins) {
        if (knockedDownPins < 0)
            throw new IllegalArgumentException("It is impossible to knock down a negative number of pins!");
        if (isNotAllowedToRoll())
            throw new IllegalStateException("The max number of rolls has already been reached!");
        if (getMaxNumberOfRolls() + knockedDownPins > getMaxNumberOfKnockedDownPins())
            throw new IllegalArgumentException("Unable to knocked down more than " + getMaxNumberOfKnockedDownPins() +
                    " pins!");

        knockedDownPinsPerRoll[numberOfRolls++] = knockedDownPins;
    }

    public final void addBonus(int bonus) {
        if (bonus <= 0)
            throw new IllegalArgumentException("Adding a non-positive number of bonus points has no meaning at all!");
        if (bonusPoints + bonus > 10)
            throw new IllegalArgumentException("Unable to have more than " + MAX_NUMBER_OF_PINS + " bonus points!");

        bonusPoints += bonus;
    }

    public final int getKnockedDownPinsAt(int roll) {
        if (roll < 0 || roll >= getMaxNumberOfRolls())
            throw new IllegalArgumentException("There is not a roll with index <" + roll + ">!");
        return knockedDownPinsPerRoll[roll];
    }

    public final int getTotalNumberOfKnockedDownPins() {
        return Arrays.stream(knockedDownPinsPerRoll)
                .sum();
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Frame frame = (Frame) o;
        return numberOfRolls == frame.numberOfRolls && bonusPoints == frame.bonusPoints &&
                getTotalNumberOfKnockedDownPins() == frame.getTotalNumberOfKnockedDownPins();
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfRolls, bonusPoints, getTotalNumberOfKnockedDownPins());
    }

    @Override
    public String toString() {
        return "Frame {" +
                "numberOfRolls=" + numberOfRolls +
                ", bonusPoints=" + bonusPoints +
                ", knockedDownPins" + getTotalNumberOfKnockedDownPins() +
                '}';
    }
}
