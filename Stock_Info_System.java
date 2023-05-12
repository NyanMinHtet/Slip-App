import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Stock_Info_System {
	private static Scanner sc=new Scanner(System.in);
	
	public static void starLine(int n,char ch) {
		for(int i = 0;i<n;i++) {
			System.out.print(ch);
		}
		System.out.println();
	}
	
	public static void mainMenu() {
		starLine(70,'*');
		System.out.println("Main Menu for Stock Information SYstem");
		starLine(70,'*');
		System.out.println("1 : Show All Stock Information");
		System.out.println("2 : Show ALl Stock Information by Category");
		System.out.println("3 : Show All Stock Information under reorder level");
		System.out.println("4 : Insert new Stock Information");
		System.out.println("5 : Search Stock by Name");
		System.out.println("6 : Sorting Item Information");
		System.out.println("7 : Delete");
		System.out.println("8 : Selling Item");
		System.out.println("9 : Updating Price");
		System.out.println("10: New Arrival");
		System.out.println("11: Exit");
		starLine(70,'*');
		System.out.print("Choose (1 - 8) :");
	}
	
	public static void main(String[] args) throws SQLException, IOException {
		
		String choice;
		ArrayList<Item> itemList = new ArrayList<Item>();
		ItemDAO  idao=new ItemDAO();
		int mopt;
		int catOpt;
		
		
		a:
		do {
			mainMenu();
			mopt = sc.nextInt();
			switch(mopt) {
				case 1:{
					itemList=idao.showAllItem();
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'*');
				};break;
				case 2:{
					Item.showCategory();
					catOpt=sc.nextInt();
					String category=Item.checkCategory(catOpt);
					if(category.equals("All"))
						itemList=idao.showAllItem();
					else itemList = idao.showItemByCategory(category);
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'*');
				};break;
				case 3:{
					itemList = idao.showItemUnderRLevel();
					starLine(100,'-');
					Item.showItemHeader();
					starLine(100,'-');
					itemList.forEach(Item::showItem);
					starLine(100,'-');
				};break;
				case 4:{
					Item item=new Item();
					item.inputItem();
					System.out.println(idao.addItem(item));
				};break;
				case 5:{
					System.out.print("Enter Search Word for Item Name : ");
					String iwrod = Item.br.readLine();
					itemList=idao.searchByKeyWord(iwrod);
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'*');
				};break;
				case 6:{
					Item.showFieldName();
					mopt=sc.nextInt();
					System.out.println("By");
					System.out.println(" 1. Ascending Order");
					System.out.println(" 2. Decending Order");
					System.out.print(" Choose (1 - 2) :");
					catOpt=sc.nextInt();
					
					itemList=idao.sortItemData(catOpt,mopt);
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'*');
				};break;
				case 7:{
					System.out.print("Enter Delete Word for Item Name : ");
					String iwrod = Item.br.readLine();
					itemList = idao.searchByKeyWord(iwrod);
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'-');
					System.out.println("Are you sure to delete that items (yes|no) : ");
					String ch = sc.next();
					if(ch.equalsIgnoreCase("Yes") || ch.equalsIgnoreCase("y")) {
						String noOfDeletedItems = idao.deleteByKeyWord(iwrod);
						System.out.println("Delete "+noOfDeletedItems+" Items Successfully...");
					}
				};break;
				case 8:{
					SaleDAO sdao = new SaleDAO();
					System.out.print("Enter Casher Name : ");
					String cName = Item.br.readLine();
					int sid = sdao.addSale(cName);
//					System.out.println(new SaleDAO().addSale(cName));
					System.out.println("Select Selling item from following list...");
					
					itemList=idao.showAllItem();
					starLine(100,'*');
					Item.showItemHeader();
					starLine(100,'*');
					itemList.forEach(Item::showItem);
					starLine(100,'*');
					
					while(sellItem(sid).equalsIgnoreCase("yes"));
				};break;
				case 9:{
					System.out.print("Enter Item_No : ");
					String itemNo = Item.br.readLine();
					
					System.out.print("Enter Update Price : ");
					int p = sc.nextInt();
					
					itemList = idao.UpdatingPrice(itemNo, p);
					starLine(100,'-');
					System.out.println("Updated the price of "+itemNo+".");
					itemList=idao.searchItemNo(itemNo);
					starLine(100,'-');
					Item.showItemHeader();
					starLine(100,'-');
					itemList.forEach(Item::showItem);
					starLine(100,'-');
				}break;
				case 10:{
					itemList = idao.showAllItem();
					starLine(100,'-');
					Item.showItemHeader();
					starLine(100,'-');
					itemList.forEach(Item::showItem);
					starLine(90,'-');
					System.out.print("Enter Item_No : ");
					String itemNo = Item.br.readLine();
					boolean Check = idao.checkItemNoExist(itemNo);
					
					if(Check) {
						System.out.println("Item existed");
						System.out.print("Enter Quantity : ");
						int qty=sc.nextInt();
						idao.updateItemQuantity(itemNo, qty);
						System.out.print("Enter Price : ");
						int price=sc.nextInt();
						itemList=idao.UpdatingPrice(itemNo, price);
						System.out.println("Price updated successfully!");
						itemList=idao.showAllItem();
						starLine(90,'-');
						Item.showItemHeader();
						starLine(90,'-');
						itemList.forEach(Item::showItem);
						starLine(90,'-');
					}
					else {
			            System.out.println("Item does not exist!!!");
			        }
				};break;
				case 11:{};break a;
				default:System.out.println("Wrong Option...");
			}
			System.out.print("Continue or not (yes|no) ? : ");
			choice = sc.next();
		}while(choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"));
		
		starLine(100,'*');
		System.out.println("End of the Program");
		starLine(100,'*');
	}
	
	private static String sellItem(int sid) throws SQLException {
		String itemNo;
		boolean isSell=false;
		int iNo,qty;
		do {
			System.out.print("Select Item No. : ");
			itemNo = sc.next();
			iNo=new ItemDAO().checkItemNo(itemNo);
			if(iNo==0) System.out.println("Your Item No. does not exists...");
		}while(iNo==0);
		
		do {
			System.out.print("Enter quantity for "+itemNo+" : ");
			qty=sc.nextInt();
			isSell=new ItemDAO().checkItemQty(itemNo, qty);
			if(!isSell) System.out.println("Your quantity is not availabel...");
		}while(!isSell);
		if(new SaleDAO().addSaleDetail(sid, itemNo, qty)==0) System.out.println("Can't sell "+itemNo);
		System.out.print("Continue or not (yes|no) : ");
		
		return sc.next();
	}
	
	public static void generateSlip(String cname,int sid) throws IOException, SQLException{
		String fname="Slips/Slip_" +Calendar.DATE+"_"+Calendar.MONTH+"_"+Calendar.YEAR+"_"+Calendar.HOUR+"_"+Calendar.MINUTE+"_"+Calendar.SECOND+".txt";
		PrintWriter pw=new PrintWriter(new FileWriter(fname));
//		Heading
		String sdata;
		sdata=String.format("%10s",	"Date : ");
		sdata+=String.format("%-40s", DateFormat.getInstance().format(new Date()));
		sdata+=String.format("%20s", "Casher Name : ");
		sdata+=String.format("%-30s",cname);
		pw.println(sdata);
		
		pw.println(starline('-',90));
		sdata=String.format("%-5s", "No.");
		sdata+=String.format("%-30s","Item Name");
		sdata+=String.format("%10s", "Price");
		sdata+=String.format("%10s", "Quantity");
		sdata+=String.format("%10s", "Amount");
		pw.println(sdata);
		pw.println(starline('-',90));
		
		int total=0,amount=0;
		ArrayList<SaleItem> sitem=new SaleDAO().getSaleDataByID(sid);
		for(int i=0;i<sitem.size();i++) {
			sdata=String.format("%-5s", i+1);
			sdata+=String.format("%-30s", sitem.get(i).getItemName());
			sdata+=String.format("%10s", sitem.get(i).getPrice());
			sdata+=String.format("%10s", sitem.get(i).getQty());
			amount=sitem.get(i).getPrice()*sitem.get(i).getQty();
			sdata+=String.format("%10s", amount);
			total+=amount;
			pw.println(sdata);
		}
		
		pw.println(starline('-',90));
		sdata=String.format("%55s", "Total : ");
		sdata+=String.format("%10s",total);
		pw.println(sdata);
		pw.println(starline('-',90));
		
		pw.close();
	}
	
	public static String starline(char ch,int n){
		String str="";
		for(int i=0;i<n;i++)
			str+=ch;
		
		return str;
	}
}
