package kz.project.carrental.util;

import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;

import java.util.List;

/**
 * Provides methods for working with user access rights.
 */
public class AccessUtil {

    /**
     * Checks whether the user access.
     *
     * @param user        is checked.
     * @param accessValue value of access.
     * @return true - if user has access, or else false.
     */
    public static boolean hasAccess(User user, String accessValue) {
        if (user != null) {
            List<Access> accesses = user.getAccesses();
            if (accesses != null) {
                for (Access currentAccess : accesses) {
                    if (currentAccess.getValue().equals(accessValue)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

}
