package kz.project.carrental.util;

import junit.framework.TestCase;
import kz.project.carrental.entity.Access;
import kz.project.carrental.entity.User;

import java.util.ArrayList;
import java.util.List;

public class AccessUtilTest extends TestCase {

    public AccessUtilTest() {
    }

    public AccessUtilTest(String name) {
        super(name);
    }

    public void testHasAccess() {
        final String ACCESS_VALUE = "test.access";
        final String NO_ACCESS_VALUE = "no.test.access";

        Access access = new Access();
        access.setName("Test access");
        access.setValue(ACCESS_VALUE);

        User user = new User();
        List<Access> accesses = new ArrayList<Access>();
        accesses.add(access);
        user.setAccesses(accesses);

        assertEquals(AccessUtil.hasAccess(user, ACCESS_VALUE), true);
        assertEquals(AccessUtil.hasAccess(user, NO_ACCESS_VALUE), false);
    }


}
