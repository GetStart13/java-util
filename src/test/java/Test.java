import com.fqq.utils.entity.EntityUtil;
import entity.Grandson;
import entity.Parent;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }

    void test() {
        entityUtilTest();
        testSome();
        testToUpperCase();
    }

    private void entityUtilTest() {
        Parent parent = new Parent();
//        parent.setParent("parent");
        parent.setIiFlag(true);
        Grandson grandson = new Grandson();
        grandson.setParent("parentC");
        grandson.setGrandson("grandson");
        EntityUtil.copyFromFather(parent, grandson, true);
        System.out.println(grandson + ": " + grandson.getParent() + " - " + grandson.getGrandson());
        System.out.println(grandson.getParent());
        System.out.println(grandson.getGrandson());
        System.out.println(grandson.isIiFlag());

        Parent parent1 = new Parent();
        parent1.setParent("parent1");
        EntityUtil.copyFromFather(parent, parent1);
        System.out.println(parent1 + ": " + parent1.getParent());
        System.out.println(parent1.getParent());
        System.out.println(parent1.isIiFlag());

    }

    void testSome() {
        try {
            Parent parent = new Parent();
            parent.setIiFlag(true);
            parent.setParent("is not null");
//            Object iiFlag = EntityUtil.findFieldValue(parent, "iiFlag1");
//            System.out.println(iiFlag);

            Object iiFlag1 = EntityUtil.getFieldValue(parent, "iiFlag");
            System.out.println(iiFlag1);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    void testToUpperCase() {
        String columnName = "__name__a_dd__  _flag__";
        String toUpperCamelCase = EntityUtil.toUpperCamelCase(columnName);
        System.out.println(toUpperCamelCase);
        String caseRegex = EntityUtil.toUpperCamelCaseRegex(columnName);
        System.out.println(caseRegex);
    }
}
