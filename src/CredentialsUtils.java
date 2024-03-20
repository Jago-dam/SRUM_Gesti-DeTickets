public class CredentialsUtils {
    /**
     * Adds credentials for a new user at the specified index in the credentials array.
     *
     * @param credentials The 2D array containing user credentials.
     * @param index       The index where the credentials should be added.
     * @param username    The username for the new user.
     * @param password    The password for the new user.
     * @throws IllegalArgumentException If a user already exists at the specified index.
     */
    public static void addCredential(String[][] credentials, int index, String username, String password) throws IllegalArgumentException {
        if (credentials[index][0] == null) {
            credentials[index] = new String[]{username, password};
        } else {
            throw new IllegalArgumentException("User already exists at index " + index);
        }
    }

    /**
     * Checks if a user with the specified username exists in the credentials array.
     *
     * @param credentials The 2D array containing user credentials.
     * @param username    The username to search for.
     * @return {@code true} if the user exists, {@code false} otherwise.
     */
    public static boolean userExists(String[][] credentials, String username) {
        for (String[] credential : credentials) {
            if (credential[0] != null && credential[0].equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the provided credentials match any existing user credentials.
     *
     * @param credentials The 2D array containing user credentials.
     * @param username    The username to check.
     * @param password    The password to check.
     * @return {@code true} if the credentials are correct, {@code false} otherwise.
     */
    public static boolean correctCredentials(String[][] credentials, String username, String password) {
        for (String[] credential : credentials) {
            if (credential[0] != null && credential[0].equals(username) && credential[1].equals(password)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the index of the user with the specified username in the credentials array.
     *
     * @param credentials The 2D array containing user credentials.
     * @param username    The username to search for.
     * @return The index of the user if found, otherwise -1.
     */
    public static int getUserIndex(String[][] credentials, String username) {
        for (int i = 0; i < credentials.length; i++) {
            if (credentials[i][0] != null && credentials[i][0].equals(username)) {
                return i;
            }
        }
        return -1;
    }


}
