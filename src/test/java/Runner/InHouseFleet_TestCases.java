package Runner;

import static io.restassured.RestAssured.given;

import java.util.List;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Repositories.RequestPayload_InhouseFleet;
import Utils.Configuration;
import Utils.DataBaseUtils;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class InHouseFleet_TestCases extends TestSuite {
	public static int inhouseIdentifier;

	@Test(priority = 0, enabled = true)
	public void createInhouseFleet() {
		logger = extent.startTest("createInhouseFleet");
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		given().log().all().header("Authorization", authToken).header("Content-Type", "application/json")
				.body(RequestPayload_InhouseFleet.payload_createInhousefleet()).when().log().all()
				.post(Configuration.createInhouseFleetResouce).then().assertThat().log().all().statusCode(200);

		inhouseIdentifier = DataBaseUtils.getInhouseFleetIdentifier(Configuration.testEnv, Configuration.kcsmdb);
		System.out.println(inhouseIdentifier);

	}

	@Test(priority = 1, enabled = true)
	public void addUpdateInhouseFleet() {
		logger = extent.startTest("createInhouseFleet");
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		given().log().all().header("Authorization", authToken).header("Content-Type", "application/json")
				.body(RequestPayload_InhouseFleet.addUpdateInhouseFleetPayload()).when().log().all()
				.post(Configuration.updateInhouseFleetResouce).then().assertThat().log().all().statusCode(200);

	}

	@Test(priority = 2, enabled = true)
	public void getInhouseFleet() {
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json")
				.body(RequestPayload_InhouseFleet.payload_GetInhousefleetDeatils()).when().log().all()
				.post(Configuration.getInhouseFleetResouce).then().assertThat().log().all().statusCode(200).extract()
				.response();

		String res = resp.asString();
		JsonPath js = new JsonPath(res);
		char eqpInitial = js.getChar("inHouseFleet.get(0).inHouseFleetEquipment.get(0).equipmentInitial");
		int eqpNo = js.getInt("inHouseFleet.get(0).inHouseFleetEquipment.get(0).equipmentNumber");
		System.out.println(eqpInitial);
		System.out.println(eqpNo);

	}

	@Test(priority = 3, enabled = false)
	public void inHouseFleetEnquiry() {
		RestAssured.baseURI = "https://cm-kcsr-ecs-qa-api.cloudmoyo.com";

		Response resp = given().log().all().header("Authorization", authToken)
				.header("Content-Type", "application/json")
				.body(RequestPayload_InhouseFleet.inhouseFleetEnquiry_Payload()).when().log().all()
				.post(Configuration.inhouseFleetInquiryResouce).then().assertThat().log().all().statusCode(200)
				.extract().response();

		String res = resp.asString();
		JsonPath js = new JsonPath(res);
		// String fleetName1 = js.getString("inHouseFleet.get(0).fleetName");
		List<String> fleetName_list = js.getList("inHouseFleet");

		for (String flt : fleetName_list) {
			System.out.println(flt);
		}

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
