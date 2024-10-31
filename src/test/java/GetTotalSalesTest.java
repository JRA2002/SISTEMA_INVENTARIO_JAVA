import static org.junit.jupiter.api.Assertions.assertEquals;

import org.inventory_system.DAO.SalesDAOImpl;
import org.inventory_system.interfaces.SalesDAO;
import org.junit.jupiter.api.Test;
public class GetTotalSalesTest {

    @Test
    public void testGetTotalSales(){
        SalesDAO salesDAO = new SalesDAOImpl();
        int result = salesDAO.getTotalSales();
        assertEquals(37,result);
    }
}
