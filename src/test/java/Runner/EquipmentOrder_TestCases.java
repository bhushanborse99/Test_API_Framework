package Runner;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Pojo.CreateEquipmentOrder;
import Pojo.GetOrderDetails;

//import com.cm.base.BrowserFactory;
//import com.cm.base.Constant;

import Repositories.RequestPayLoad_EquipmentOrder;
import Repositories.RequestPayload_InhouseFleet;
import Utils.Configuration;
import Utils.Report;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class EquipmentOrder_TestCases extends TestSuite {
	public static int orderNo;

	@Test(priority = 0, enabled = true)
	public void createEqpOrder() {
		
		logger = extent.startTest("createEqpOrders");
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json")
				.body(RequestPayLoad_EquipmentOrder.createOrderPayloadWithOptionalPrams()).when().log().all()
				.post(Configuration.createEOResource).then().assertThat().log().all().statusCode(201).extract()
				.response();
		//extract the value and print using deserelization
		CreateEquipmentOrder ce = resp.as(CreateEquipmentOrder.class);
		System.out.println(ce.getOrderNumber());

		//extracting the value usin JspnPath class
//		String res = resp.asString();
//		JsonPath js = new JsonPath(res);
//		orderNo = js.get("orderNumber");
//		System.out.println(orderNo);

	}

	@Test(priority = 1, enabled = false)
	public static void editEqpOrder() {
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json")
				.body(RequestPayLoad_EquipmentOrder.editOrderRequestPayload()).when().log().all()
				.post(Configuration.editEOResource).then().assertThat().log().all().statusCode(200).extract()
				.response();
		
		  //extract the value and print using deserelization
				CreateEquipmentOrder ce = resp.as(CreateEquipmentOrder.class);	
				System.out.println(ce.getOrderNumber());
				//System.out.println(ce.getRevisedNumberOfCarsOrdered());
        //extracting the values in response using JsonPath class
		String res = resp.asString();
		JsonPath js = new JsonPath(res);
		int orderNo = js.get("orderNumber");
		int revisedCars = js.get("revisedNumberOfCarsOrdered");
		System.out.println(orderNo);
		System.out.println(revisedCars);

	}

	@Test(priority = 2, enabled = false)
	public static void getOrderDetails() {
		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json").body(RequestPayLoad_EquipmentOrder.getOrderDetails()).when()
				.log().all().get(Configuration.getOrderResource).then().assertThat().log().all().statusCode(200)
				.extract().response();
		//Extracting the values using deserilization
		GetOrderDetails go = resp.as(GetOrderDetails.class);
		System.out.println(go.getEquipmentDateRequiredOn());
		System.out.println(go.getRouteSCAC());
		System.out.println(go.getOrderStatus());
		
		//Extracting the parameter using tg
		String res = resp.asString();
		JsonPath js = new JsonPath(res);
		// extracting the order number
		int orderNo = js.get("orderNumber");
		// extracting the routescac from the json
		String routescac = js.getString("destinationRoute.get(0).routeSCAC");
		// extracting the date required on
		String date = js.getString("orderQuantity.get(0).equipmentDateRequiredOn");
		// extracting the order status for getorder
		String eqpOrderStatus = js.getString("orderStatus");
		System.out.println(orderNo);
		System.out.println(routescac);
		System.out.println(date);
		System.out.println(eqpOrderStatus);
	}

	@Test(priority = 3, enabled = false)
	public static void listOfEO() {
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json")
				.body(RequestPayLoad_EquipmentOrder.listOfEOWithOptionalParams()).when().log().all()
				.post(Configuration.listEOResource).then().assertThat().log().all().statusCode(200).extract()
				.response();

		String res = resp.asString();
		JsonPath js = new JsonPath(res);
		int orderNo = js.get("emptyCarOrders.get(0).orderNumber");
		System.out.println(orderNo);
	}

	@Test(priority = 4, enabled = false)
	public static void cancelEO() {
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		given().log().all().header("Authorization", authToken).header("Content-Type", "application/json")
				.body(RequestPayLoad_EquipmentOrder.cancelEqpOrderWithOptionalParams()).when().log().all()
				.put(Configuration.cancelEOResource).then().assertThat().log().all().statusCode(200).extract()
				.response();

	}
	
	@AfterMethod
    public void afterMethod(ITestResult result) {
        if(result.isSuccess()) {
            logger.log(LogStatus.PASS, "API working fine ");
        }
        else if(!result.isSuccess()) {
            logger.log(LogStatus.FAIL, result.getThrowable().getMessage());
        }
       
    }

}
