
public class SaleItem {
	private String itemName;
	private int price;
	private int qty;
	
	public SaleItem() {}
	public SaleItem(String name,int p,int q) {
		itemName = name;
		price = p;
		qty = q;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
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
}
