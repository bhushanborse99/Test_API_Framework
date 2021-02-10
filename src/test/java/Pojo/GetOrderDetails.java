package Pojo;

public class GetOrderDetails {
	
	//request body params
	private int orderNumber;
	private String customerNumber;
	private String requestingUserID;
	private String organization;
	
	//Response boy params
	private String equipmentDateRequiredOn;
	private String orderStatus;
	private String routeSCAC;
	
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerNumber() {
		return customerNumber;
	}
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}
	public String getRequestingUserID() {
		return requestingUserID;
	}
	public void setRequestingUserID(String requestingUserID) {
		this.requestingUserID = requestingUserID;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	

	public String getRouteSCAC() {
		return routeSCAC;
	}
	public void setRouteSCAC(String routeSCAC) {
		this.routeSCAC = routeSCAC;
	}
	public String getEquipmentDateRequiredOn() {
		return equipmentDateRequiredOn;
	}
	public void setEquipmentDateRequiredOn(String equipmentDateRequiredOn) {
		this.equipmentDateRequiredOn = equipmentDateRequiredOn;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	

}
