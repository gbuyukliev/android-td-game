package bg.ittalents.tower_defense.game.objects;

public abstract class WaveManager {
    protected String typeOfCreeps;
    protected int numOfCreeps;

    public static WaveManager createWave(int number) {
        if (number % 10 == 0) {
            return new WaveBoss();
        } else if (number % 5 == 0) {
            return new WaveSlow();
        } else if (number % 7 == 0) {
            return new WaveSpecial();
        } else {
            return new WaveBasic();
        }
    }

    public int getNumOfCreeps() {
        return numOfCreeps;
    }

    public void setNumOfCreeps(int numOfCreeps) {
        if (numOfCreeps > 0) {
            this.numOfCreeps = numOfCreeps;
        }
    }

    public String getTypeOfCreeps() {
        return typeOfCreeps;
    }

    public void setTypeOfCreeps(String typeOfCreeps) {
        if (typeOfCreeps != null) {
            this.typeOfCreeps = typeOfCreeps;
        }
    }
}
