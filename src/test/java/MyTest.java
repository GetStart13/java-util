import com.fqq.utils.entity.EntityUtils;
import entity.Grandson;
import entity.Parent;
import org.junit.jupiter.api.Test;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;

class MyTest {

    @Test
    void entityUtilTest() {
        Parent parent = new Parent();
//        parent.setParent("parent");
        parent.setIiFlag(true);
        Grandson grandson = new Grandson();
        grandson.setParent("parentC");
        grandson.setGrandson("grandson");
        EntityUtils.copyFromFather(parent, grandson, true);
        System.out.println(grandson + ": " + grandson.getParent() + " - " + grandson.getGrandson());
        System.out.println(grandson.getParent());
        System.out.println(grandson.getGrandson());
        System.out.println(grandson.isIiFlag());

        Parent parent1 = new Parent();
        parent1.setParent("parent1");
        EntityUtils.copyFromFather(parent, parent1);
        System.out.println(parent1 + ": " + parent1.getParent());
        System.out.println(parent1.getParent());
        System.out.println(parent1.isIiFlag());

    }

    @Test
    void testSome() {
        try {
            Parent parent = new Parent();
            parent.setIiFlag(true);
            parent.setParent("is not null");
//            Object iiFlag = EntityUtils.findFieldValue(parent, "iiFlag1");
//            System.out.println(iiFlag);

            Object iiFlag1 = EntityUtils.getFieldValue(parent, "iiFlag");
            System.out.println(iiFlag1);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void testToUpperCase() {
        String columnName = "__name__a_dd__  _flag__";
        String toUpperCamelCase = EntityUtils.toUpperCamelCase(columnName);
        System.out.println(toUpperCamelCase);
        String caseRegex = EntityUtils.toUpperCamelCaseRegex(columnName);
        System.out.println(caseRegex);
    }
}
