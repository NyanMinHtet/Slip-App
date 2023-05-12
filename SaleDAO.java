import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaleDAO {
	private Connection con;
	public SaleDAO() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Receipt","admin","Nyan123#");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection con,PreparedStatement pst,ResultSet rs) throws SQLException {
		if(con!=null) con.close();
		if(pst!=null) con.close();
		if(rs!=null) rs.close();
	}
	
	public int addSale(String cname) throws SQLException {
		int sid=0;
		ResultSet rs = null;
		PreparedStatement pst = con.prepareStatement("insert into Sale(casher_name) values(?)");
		pst.setString(1, cname);
		int i = pst.executeUpdate();
		if(i>0) {
			pst = con.prepareStatement("select last_insert_id()");
			rs = pst.executeQuery();
			while(rs.next()) sid=rs.getInt(1);
		}
		pst.close();
		rs.close();
		return sid;
	}
	
	public int addSaleDetail(int sid,String itemid,int qty) throws SQLException {
		PreparedStatement pst=con.prepareStatement("insert into Sale_detail values(?,?,?)");
		pst.setInt(1, sid);
		pst.setString(2, itemid);
		pst.setInt(3, qty);
		int i=pst.executeUpdate();
		
		return i;
	}
	
	public ArrayList<SaleItem> getSaleDataByID(int sid) throws SQLException{
		ArrayList<SaleItem> sitem=new ArrayList<SaleItem>();
		PreparedStatement pst=con.prepareStatement("select Bill.Item_Name,Bill.Price,Sale_detail.qty from Bill,Sale_detail where Bill.Item_No=Sale_detail.itemid and saleid=?");
		pst.setInt(1, sid);
		ResultSet rs=pst.executeQuery();
		while(rs.next())
			sitem.add(new SaleItem(rs.getString(1),rs.getInt(2),rs.getInt(3)));
		pst.close();
		rs.close();
		
		return sitem;
	}
	
}
