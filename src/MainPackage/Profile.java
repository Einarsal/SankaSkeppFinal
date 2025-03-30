package MainPackage;

public class Profile {
    String profileName;
    int score = 0;

    protected Profile(String userName, int score) {
        this.profileName = userName;
        this.score = score;
    }

    public String toString() {
        return profileName + " " + score;
    }
}
