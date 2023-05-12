import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class ItemDAO {
	private Connection con=null;
	
	public ItemDAO() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Receipt","admin","Nyan123#");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void close(Connection con,PreparedStatement pst,ResultSet rs) throws SQLException {
		if(con!=null) con.close();
		if(pst!=null) pst.close();
		if(rs!=null) rs.close();
	}
	
	
	public ArrayList<Item> showAllItem() throws SQLException {
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill");
		pst.executeQuery();
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item;
	}
	
	public ArrayList<Item> showItemByCategory(String cat) throws SQLException{
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill where Category=?");
		pst.setString(1,cat);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item;
	}
	
	public ArrayList<Item> showItemUnderRLevel() throws SQLException {
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill where Quantity <= Reorder_Level");
		pst.executeQuery();
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item;
	}
	
	public int checkItemNo(String iNo) throws SQLException {
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill where Item_No=?");
		pst.setString(1, iNo);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item.size();
	}
	
	public String addItem(Item item) throws SQLException {
		String msg=null;
		PreparedStatement pst=con.prepareStatement("insert into Bill values(?,?,?,?,?,?)");
		pst.setString(1, item.getItemNo());
		pst.setString(2, item.getItemName());
		pst.setString(3, item.getCategory());
		pst.setInt(4, item.getPrice());
		pst.setInt(5, item.getQty());
		pst.setInt(6, item.getRlevel());
		int i=pst.executeUpdate();
		if(i>0)
			msg = "Save Successfully for "+i+" rows";
		pst.close();
		
		return msg;
	}
	
	public ArrayList<Item> searchByKeyWord(String kword) throws SQLException{
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill where Item_Name like ?");
		pst.setString(1,'%'+kword+'%');
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item;
	}
	
	public ArrayList<Item> sortItemData(int order,int fieldType) throws SQLException{
		ArrayList<Item> item = new ArrayList<Item>();
		String sql;
		if(order == 1) {
			switch(fieldType) {
				case 1:sql="select * from Bill order by Item_No";break;
				case 2:sql="select * from Bill order by Item_Name";break;
				case 3:sql="select * from Bill order by Category";break;
				case 4:sql="select * from Bill order by Price";break;
				case 5:sql="select * from Bill order by Quantity";break;
				case 6:sql="select * from Bill order by Reorder_Level";break;
				default:sql="select * from Bill";
			}
		}
		else if(order == 2) {
			switch(fieldType) {
				case 1:sql="select * from Bill order by Item_No desc";break;
				case 2:sql="select * from Bill order by Item_Name desc";break;
				case 3:sql="select * from Bill order by Category desc";break;
				case 4:sql="select * from Bill order by Price desc";break;
				case 5:sql="select * from Bill order by Quantity desc";break;
				case 6:sql="select * from Bill order by Reorder_Level desc";break;
				default:sql="select * from Bill";
			}
		}
		else {
			sql="select * from Bill";
		}
		
		PreparedStatement pst=con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		
		return item;
	}
	
	public String deleteByKeyWord(String kword) throws SQLException{
		PreparedStatement pst = con.prepareStatement("delete from Bill where Item_Name like ?");
		pst.setString(1,'%'+kword+'%');
		int i = pst.executeUpdate();
	
		String msg = null;
		if(i>0)
			msg = "Delete Successfully for "+i+" rows";
		pst.close();
		return msg;
	}
	
	public boolean checkItemQty(String itemNo,int q) throws SQLException{
		boolean result = false;
		int qty = 0;
		PreparedStatement pst=con.prepareStatement("select Quantity from Bill where Item_No=?");
		pst.setString(1, itemNo);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			qty=rs.getInt(1);
		}
		if(qty>=q) {
			pst=con.prepareStatement("update Bill set Quantity=? where Item_No=?");
			pst.setInt(1, qty-q);
			pst.setString(2, itemNo);
			pst.executeUpdate();
			
			result=true;
		}
		pst.close();
		rs.close();
		return result;
	}
	
	public ArrayList<Item> searchItemNo(String itemNo) throws SQLException{
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst=con.prepareStatement("select * from Bill where Item_no=?");
		pst.setString(1, itemNo);
		ResultSet rs=pst.executeQuery();
		while(rs.next())
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		close(null,pst,rs);
		return item;
	}
	
	public ArrayList<Item> UpdatingPrice(String itemNo,int p) throws SQLException {
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("Update Bill set Price=? where Item_No=?");
		pst.setInt(1, p);
		pst.setString(2, itemNo);
		pst.executeUpdate();
		
		pst.close();
	
		return item;
	}
	
	public boolean checkItemNoExist(String itemNo) throws SQLException {
		boolean t=false;
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = con.prepareStatement("select * from Bill where Item_No=?");
		pst.setString(1, itemNo);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
			t=true;
		}
		pst.close();
		rs.close();
		return t;
	}

	
	public ArrayList<Item> updateQuantity(String itemNo,int q) throws SQLException{
		
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst=con.prepareStatement("update Bill set Quantity=? where Item_No=?");
		pst.setInt(1, q);
		pst.setString(2, itemNo);
		pst.executeUpdate();
		
		pst.close();
		return item;
	}
	
	public void updateItemQuantity(String itemNo,int q) throws SQLException{
		PreparedStatement pst=con.prepareStatement("select Quantity from Bill where Item_No=?");
		pst.setString(1, itemNo);
		ResultSet rs=pst.executeQuery();
		
		if(rs.next()) {
			int currentQty = rs.getInt("quantity");
			int updateQty = currentQty+q;
			updateQuantity(itemNo,updateQty);
			System.out.println("Quantity updated successfully!!");
		}
		else {
			System.out.println("Item does not exist!");
		}
	}
}
