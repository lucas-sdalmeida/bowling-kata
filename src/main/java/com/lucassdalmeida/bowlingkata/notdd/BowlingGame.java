package com.lucassdalmeida.bowlingkata.notdd;

import java.util.Arrays;
import java.util.Objects;

public final class BowlingGame {
    private static final int MAX_NUMBER_OF_FRAMES = 10;
    private final Frame[] frames = new Frame[MAX_NUMBER_OF_FRAMES];
    private int currentNumberOfFrames = 0;

    public BowlingGame() {
        startNewFrame();
    }

    public boolean isFinishedGame() {
        return currentNumberOfFrames == 10 && frames[currentNumberOfFrames - 1].isNotAllowedToRoll();
    }

    public void roll(int knockedDownPins) {
        if (knockedDownPins < 0)
            throw new IllegalArgumentException("It is impossible to knock down  negative number of pins!");
        if (isFinishedGame())
            throw new IllegalStateException("All frames have been played!");

        Frame currentFrame = getCurrentFrame();
        currentFrame.roll(knockedDownPins);

        if (currentFrame.isNotAllowedToRoll()) {
            computeBonusPoints();
            startNewFrame();
        }
    }

    public int score() {
        if (currentNumberOfFrames < MAX_NUMBER_OF_FRAMES)
            throw new IllegalStateException("Not every frame has been played yet!");
        return Arrays.stream(frames)
                .mapToInt(Frame::getScore)
                .sum();
    }

    private Frame getCurrentFrame() {
        return frames[currentNumberOfFrames - 1];
    }

    private void startNewFrame() {
        if (currentNumberOfFrames == MAX_NUMBER_OF_FRAMES - 1)
            frames[currentNumberOfFrames++] = new FinalFrame();
        frames[currentNumberOfFrames++] = new NormalFrame();
    }

    private void computeBonusPoints() {
        if (currentNumberOfFrames == 1)
            return;

        Frame currentFrame = getCurrentFrame();
        Frame previousFrame = frames[currentNumberOfFrames - 2];
        int bonusPoints = 0;

        if (previousFrame.isSpare())
            bonusPoints += currentFrame.getKnockedDownPinsAt(0);
        if (previousFrame.isStrike())
            bonusPoints += currentFrame.getKnockedDownPinsAt(1);

        previousFrame.addBonus(bonusPoints);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BowlingGame that = (BowlingGame) o;
        return currentNumberOfFrames == that.currentNumberOfFrames && Arrays.equals(frames, that.frames);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(currentNumberOfFrames);
        result = 31 * result + Arrays.hashCode(frames);
        return result;
    }

    @Override
    public String toString() {
        return "BowlingGame{" +
                "frames=" + Arrays.toString(frames) +
                ", currentNumberOfFrames=" + currentNumberOfFrames +
                '}';
    }
}
