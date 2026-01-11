/** Represents a user in a social network. A user is characterized by a name,
 * a list of user names that s/he follows, and the list's size. */
public class User {

    // Maximum number of users that a user can follow
    static int maxfCount = 10;

    private String name;       // name of this user
    private String[] follows;  // array of user names that this user follows
    private int fCount;        // actual number of followees (must be <= maxfCount)

    /** Creates a user with an empty list of followees. */
    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; // fixed-size array for storing followees
        fCount = 0;                      // initial number of followees
    }

    /** Creates a user with some followees. The only purpose of this constructor is 
     * to allow testing the toString and follows methods, before implementing other methods. */
    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    /** Returns the name of this user. */
    public String getName() {
        return name;
    }

    /** Returns the follows array. */
    public String[] getfFollows() {
        return follows;
    }

    /** Returns the number of users that this user follows. */
    public int getfCount() {
        return fCount;
    }

    /** If this user follows the given name, returns true; otherwise returns false. */
    public boolean follows(String name) {
        // Loop through the current followees. 
        // Use .equals() for string comparison[cite: 132, 139].
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                return true;
            }
        }
        return false;
    }

    /** Makes this user follow the given name. If successful, returns true. 
     * If this user already follows the given name, or if the follows list is full, does nothing and returns false; */
    public boolean addFollowee(String name) {
        // Check constraints: list is full OR user already follows this name
        if (fCount == maxfCount || follows(name)) {
            return false;
        }
        
        // Add name to the next available spot and increment count [cite: 90, 91]
        follows[fCount] = name;
        fCount++;
        return true;
    }

    /** Removes the given name from the follows list of this user. If successful, returns true.
     * If the name is not in the list, does nothing and returns false. */
    public boolean removeFollowee(String name) {
        // Step 1: Find the index of the name to remove
        int indexToRemove = -1;
        for (int i = 0; i < fCount; i++) {
            if (follows[i].equals(name)) {
                indexToRemove = i;
                break;
            }
        }

        // If name not found, return false
        if (indexToRemove == -1) {
            return false;
        }

        // Step 2: "Close the gap" by shifting elements left [cite: 93, 150]
        for (int i = indexToRemove; i < fCount - 1; i++) {
            follows[i] = follows[i + 1];
        }

        // Step 3: Set the last element to null and decrement count [cite: 94, 154]
        follows[fCount - 1] = null;
        fCount--;
        
        return true;
    }

    /** Counts the number of users that both this user and the other user follow.
    /* Notice: This is the size of the intersection of the two follows lists. */
    public int countMutual(User other) {
        int mutualCount = 0;
        // Iterate over this user's follows list
        for (int i = 0; i < fCount; i++) {
            // Check if the 'other' user follows the same person
            if (other.follows(follows[i])) {
                mutualCount++;
            }
        }
        return mutualCount;
    }

    /** Checks is this user is a friend of the other user.
     * (if two users follow each other, they are said to be "friends.") */
    public boolean isFriendOf(User other) {
        // Check mutual following: I follow him AND he follows me [cite: 157]
        // Note: we use this.name for 'my' name, and other.getName() for 'his' name
        if (this.follows(other.getName()) && other.follows(this.name)) {
            return true;
        }
        return false;
    }

    /** Returns this user's name, and the names that s/he follows. */
    public String toString() {
        String ans = name + " -> ";
        for (int i = 0; i < fCount; i++) {
            ans = ans + follows[i] + " ";
        }
        return ans;
    }
}