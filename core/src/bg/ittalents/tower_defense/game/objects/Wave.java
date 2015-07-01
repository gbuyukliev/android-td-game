package bg.ittalents.tower_defense.game.objects;

public class Wave {
    private int number;
    private int numOfCreeps;
    private String typeOfCreeps;

    public Wave() {
        setTypeOfCreeps("");
    }

    public Wave(int number) {
        this();
        setNumber(number);

        if (number % 10 == 0) {
            setNumOfCreeps(1);
            setTypeOfCreeps("boss");
        } else if (number % 7 == 0) {
            setNumOfCreeps(6);
            setTypeOfCreeps("flying");
        } else {
            setNumOfCreeps(6);
            setTypeOfCreeps("basic");
        }
    }

    public Wave(int numOfCreeps, String typeOfCreeps) {
        setNumOfCreeps(numOfCreeps);
        setTypeOfCreeps(typeOfCreeps);
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        if (number > 0) {
            this.number = number;
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
