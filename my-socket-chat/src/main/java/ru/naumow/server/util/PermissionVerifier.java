package ru.naumow.server.util;

//very simple
public class PermissionVerifier {

    public static void allowIfAdmin(String role) {
        if (!isAdmin(role))
            throw new SecurityException("you are not admin");
    }

    public static void allowIfUserOrAdmin(String role) {
        if (!isUser(role) && !isAdmin(role))
            throw new SecurityException("you are not user or admin, you're: " + role);
    }

    private static boolean isUser(String role) {
        return role.equals("user");
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private static boolean isAdmin(String role) {
        return role.equals("admin");
    }

}
