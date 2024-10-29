import static org.junit.jupiter.api.Assertions.assertEquals;

import org.inventory_system.DAO.SalesDAOImpl;
import org.inventory_system.interfaces.SalesDAO;
import org.junit.jupiter.api.Test;

public class GetSalesIDTest {
    @Test
    public void testGetSalesID(){
        SalesDAO salesDAO = new SalesDAOImpl();
        int result = salesDAO.getSalesId();
        assertEquals(66,result);
    }
}
