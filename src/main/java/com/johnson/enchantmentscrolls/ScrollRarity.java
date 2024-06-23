package com.johnson.enchantmentscrolls;

public class ScrollRarity {
    private final double successRate;
    private final String particle;
    private final String sound;

    public ScrollRarity(double successRate, String particle, String sound) {
        this.successRate = successRate;
        this.particle = particle;
        this.sound = sound;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public String getParticle() {
        return particle;
    }

    public String getSound() {
        return sound;
    }
}
