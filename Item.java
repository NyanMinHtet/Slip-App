import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Item {
	private String itemNo;
	private String itemName;
	private String category;
	private int price;
	private int qty;
	private int rlevel;
	static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
	
	public Item() {}
	
	public Item(String no,String name,String cat,int p,int q,int r) {
		itemNo = no;
		itemName = name;
		category = cat;
		price = p;
		qty = q;
		rlevel = r;
	}

	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getRlevel() {
		return rlevel;
	}

	public void setRlevel(int rlevel) {
		this.rlevel = rlevel;
	}
	
	public static void showItemHeader() {
			System.out.print(String.format("%-12s", "Item No"));
			System.out.print(String.format("%-25s", "Item Name"));
			System.out.print(String.format("%-10s", "Category"));
			System.out.print(String.format("%10s", "Price"));
			System.out.print(String.format("%10s","Quantity"));
			System.out.println(String.format("%15s", "Reorder level"));
	}
	
	public void showItem() {
		System.out.print(String.format("%-12s", itemNo));
		System.out.print(String.format("%-25s", itemName));
		System.out.print(String.format("%-10s", category));
		System.out.print(String.format("%10d", price));
		System.out.print(String.format("%10d",qty));
		System.out.println(String.format("%15d", rlevel));
	}
	
	public static void starLine(int n,char ch) {
		for(int i = 0;i<n;i++) {
			System.out.print(ch);
		}
		System.out.println();
	}
	
	public static void showCategory() {
		starLine(30,'*');
		System.out.println("Category list");
		starLine(30,'*');
		System.out.println("1: Cosmetic");
		System.out.println("2: Drink");
		System.out.println("3: Food");
		System.out.println("4: Fruit");
		System.out.println("5: Other");
		starLine(30,'*');
		System.out.print("Choose (1-5) : ");
	}
	
	public static String checkCategory(int opt) {
		String cat;
		switch(opt) {
			case 1:cat="Cosmetic";break;
			case 2:cat="Drink";break;
			case 3:cat="Food";break;
			case 4:cat="Fruit";break;
			case 5:cat="Other";break;
			default:cat="All";
		}
		return cat;
	}
	
	public boolean inputItemNo() throws IOException, SQLException {
		boolean result=false;
		System.out.print("Item No (Eg. Item-0000) \t : ");
		itemNo=br.readLine();
	
		if(itemNo.matches("^Item-[0-9]{4}$")) {
			if(new ItemDAO().checkItemNo(itemNo)==0) {
				result=true;
			}
			else {
				System.out.println("Your Item No already exists");
			}
		}
		else {
			System.out.println("Wrong Input,Your input must be as Item-0000");
		}
		return result;
	}
	
	public boolean inputItemName() throws IOException{
		boolean result = false;
		System.out.print("Item Name \t: ");
		itemName=br.readLine();
		if(itemName.matches("[A-Z]{1}[a-zA-Z0-9]*[[\\s]{1}[a-zA-Z0-9]*]*")) {
			result=true;
		}
		else System.out.println("Wrong Item Name input....");
		return result;
	}
	
	public void inputCategory() throws NumberFormatException, IOException{
		int opt;
		showCategory();
		opt = Integer.parseInt(br.readLine());
		switch(opt) {
			case 1:category="Cosmetic";break;
			case 2:category="Drink";break;
			case 3:category="Food";break;
			case 4:category="Fruit";break;
			default:category="Other";
		}
	}
	
	public boolean inputPrice() throws NumberFormatException, IOException{
		boolean result = false;
		System.out.print("Price \t : ");
		try {
			price = Integer.parseInt(br.readLine());
			if(price <= 0 || price >= 1000) {
				System.out.println("Your input must be within 1-999");
			}
			else result = true;
		}
		catch(Exception e) {
			System.out.println("Your input must be digit");
		}
		return result;
	}
	
	public boolean inputQty() {
		boolean result = false;
		System.out.print("Quantity \t : ");
		try {
			qty = Integer.parseInt(br.readLine());
			if(qty <= 0 || qty >= 1000) {
				System.out.println("Your input must be within 1-999");
			}
			else result = true;
		}
		catch(Exception e) {
			System.out.println("Your input must be digit");
		}
		return result;
	}
	
	public boolean inputRL() {
		boolean result = false;
		System.out.print("Reorder Level \t : ");
		try {
			rlevel = Integer.parseInt(br.readLine());
			if(rlevel <= 0 || rlevel >= 100) {
				System.out.println("Your input must be within 1-99");
			}
			else result = true;
		}
		catch(Exception e) {
			System.out.println("Your input must be digit");
		}
		return result;
	}
	
	public void inputItem() throws IOException, SQLException{
		while(!inputItemNo());
		while(!inputItemName());
		inputCategory();
		while(!inputPrice());
		while(!inputQty());
		while(!inputRL());
	}
	
	public static void showFieldName() {
		starLine(30,'*');
		System.out.println("Select Column Name...");
		starLine(30,'*');
		System.out.println("1: Item No.");
		System.out.println("2: Item Name");
		System.out.println("3: Category");
		System.out.println("4: Price");
		System.out.println("5: Quantity");
		System.out.println("6: Reorder Level");
		starLine(30,'*');
		System.out.print("Choose (1-6) : ");
	}
	
}
