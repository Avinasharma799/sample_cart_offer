package com.springboot;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.controller.ApplyOfferRequest;
import com.springboot.controller.OfferRequest;
import com.springboot.controller.SegmentResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CartOfferApplicationTests {


	@Test
	public void checkFlatXForOneSegment() throws Exception {
		List<String> segments = new ArrayList<>();
		segments.add("p1");
		OfferRequest offerRequest = new OfferRequest(1,"FLATX",10,segments);
		boolean result = addOffer(offerRequest);
		Assert.assertEquals(result,true); // able to add offer
	}
	
	@Test
    public void testApplyValidFlatAmountOfferForP1() throws Exception {
        List<String> segments = new ArrayList<>();
		segments.add("p1");
		OfferRequest offerRequest = new OfferRequest(7,"FLATX",10,segments);
		
		// Add Offer
		boolean result = addOffer(offerRequest);
		Assert.assertEquals(result,true); 
		
		// Apply Offer
		ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300,7,1);
		String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int updatedCartValue = responseJson.get("cart_value").asInt();
        int expectedCartValue = 300 - 10; 
        Assert.assertEquals(expectedCartValue, updatedCartValue);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }
	
	@Test
	public void testApplyValidFlatAmountOfferForP2() throws Exception {
	    List<String> segments = new ArrayList<>();
	    segments.add("p2");
	    OfferRequest offerRequest = new OfferRequest(7, "FLATX", 20, segments);
	    
	    // Add Offer
	    boolean result = addOffer(offerRequest);
	    Assert.assertEquals(result, true);
	    ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 7, 2);
	    
	    // Apply Offer
	    String responseMessage = applyCartOffer(applyOfferRequest);
	    System.out.println("Response Message: " + responseMessage);
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode responseJson = objectMapper.readTree(responseMessage);
	    int updatedCartValue = responseJson.get("cart_value").asInt();
	    int expectedCartValue = 300 - 20; 
	    Assert.assertEquals(expectedCartValue, updatedCartValue);
	    System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
	}
	
	@Test
	public void testApplyValidFlatAmountOfferForP3() throws Exception {
	    List<String> segments = new ArrayList<>();
	    segments.add("p3");
	    OfferRequest offerRequest = new OfferRequest(7, "FLATX", 30, segments);
	    
	    boolean result = addOffer(offerRequest);
	    Assert.assertEquals(result, true);
	    ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 7, 3);
	    
	    String responseMessage = applyCartOffer(applyOfferRequest);
	    System.out.println("Response Message: " + responseMessage);
	    ObjectMapper objectMapper = new ObjectMapper();
	    JsonNode responseJson = objectMapper.readTree(responseMessage);
	    int updatedCartValue = responseJson.get("cart_value").asInt();
	    int expectedCartValue = 300 - 30; 
	    Assert.assertEquals(expectedCartValue, updatedCartValue);
	    System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
	}
	
	@Test
	public void testApplyValidFlatPercentOfferForP1() throws Exception {
		List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(8, "FLATX%", 10, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 8, 1);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 300 - (0.1 * 300); 
        Assert.assertEquals(expectedCartValue, updatedCartValue, 0.01);
	    System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
	}
	
	@Test
    public void testApplyValidFlatPercentOfferForP2() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p2");
        OfferRequest offerRequest = new OfferRequest(8, "FLATX%", 20, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 8, 2);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 300 - (0.2 * 300); 
        Assert.assertEquals(expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyValidFlatPercentOfferForP3() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p3");
        OfferRequest offerRequest = new OfferRequest(8, "FLATX%", 30, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 8, 3);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 300 - (0.3 * 300); 
        Assert.assertEquals(expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyValidFlatAmountOfferForP1WithMinimumCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(9, "FLATX", 10, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(1, 9, 1);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 0; // Flat discount calculation
        Assert.assertEquals( expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }
    
    @Test
    public void testApplyValidFlatPercentOfferForP2WithMinimumCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p2");
        OfferRequest offerRequest = new OfferRequest(9, "FLATX%", 10, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(1, 9, 2);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asDouble();
        double expectedCartValue = 1 - (0.1 * 1); 
        Assert.assertEquals( expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyValidFlatAmountOfferForP3WithMaximumCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p3");
        OfferRequest offerRequest = new OfferRequest(9, "FLATX", 50, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(2147483647, 9, 3);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int updatedCartValue = responseJson.get("cart_value").asInt();
        long expectedCartValue = 2147483647 - 50;
        Assert.assertEquals( expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

//    @Test
//    public void testApplyValidFlatAmountOfferForP3WithMaximumCartValuePlusOne() throws Exception {
//        List<String> segments = new ArrayList<>();
//        segments.add("p3");
//        OfferRequest offerRequest = new OfferRequest(9, "FLATX", 50, segments);
//        
//        boolean result = addOffer(offerRequest);
//        Assert.assertEquals(result, true);
//        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(2147483647, 9, 3);
//        
//        String responseMessage = applyCartOffer(applyOfferRequest);
//        System.out.println("Response Message: " + responseMessage);
//        // Expecting a 400 status code for bad request
//        Assert.assertEquals("Bad Request", responseMessage );
//    }

    @Test
    public void testApplyValidFlatPercentOfferForP1WithMaximumCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(10, "FLATX%", 50, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(2147483647, 10, 1);
        
        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 2147483647 - (0.5 * 2147483647);
        Assert.assertEquals( expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

//    @Test
//    public void testApplyValidFlatPercentOfferForP1WithMaximumCartValuePlusOne() throws Exception {
//        List<String> segments = new ArrayList<>();
//        segments.add("p1");
//        OfferRequest offerRequest = new OfferRequest(10, "FLATX%", 25, segments);
//        
//        boolean result = addOffer(offerRequest);
//        Assert.assertEquals(result, true);
//        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(2147483647, 10, 1);
//        
//        String responseMessage = applyCartOffer(applyOfferRequest);
//        System.out.println("Response Message: " + responseMessage);
//        // Expecting a 400 status code for bad request
//        Assert.assertEquals("Bad Request", responseMessage );
//    }
    
    @Test
    public void testApplyOfferWithZeroCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(9, "FLATX", 10, segments);

        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(0, 9, 1);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int updatedCartValue = responseJson.get("cart_value").asInt();
        int expectedCartValue = 0; // Flat discount calculation
        Assert.assertEquals(expectedCartValue, updatedCartValue);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyOfferWithInvalidOfferType() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p2");
        OfferRequest offerRequest = new OfferRequest(10, "INVALID", 50, segments);

        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(1000, 10, 2);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int cartStatusCode=0;
        try {
         cartStatusCode = responseJson.get("status").asInt();
        }catch(Exception e) {
        	System.out.println("Eror, Status code node found");
           	Assert.assertEquals(true, false);
        }
        Assert.assertEquals(400, cartStatusCode);
        System.out.println("Invalid Offer Type: Status Code 400");
    }

    @Test
    public void testApplyOfferForInvalidSegement() throws Exception {
        
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(1000, 10, 4);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int cartStatusCode=0;
        try {
            cartStatusCode = responseJson.get("status").asInt();
           }catch(Exception e) {
           	System.out.println("Eror, Status code node found");
           	Assert.assertEquals(true, false);
           }

        Assert.assertEquals(400, cartStatusCode);
        System.out.println("Invalid Segment: Status Code 400");
    }

    @Test
    public void testApplyOfferForMultipleSegementP1() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest1 = new OfferRequest(7, "FLATX", 10, segments);
        OfferRequest offerRequest2 = new OfferRequest(7, "FLATX%", 5, segments);

        boolean result1 = addOffer(offerRequest1);
        Assert.assertEquals(result1, true);
        boolean result2 = addOffer(offerRequest2);
        Assert.assertEquals(result2, true);

        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(300, 7, 1);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        double updatedCartValue = responseJson.get("cart_value").asDouble();
        double expectedCartValue = 300 - 0.5 * 300; // Flat discount calculation
        Assert.assertEquals(expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyOfferWithDiscountExceedingCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p3");
        OfferRequest offerRequest = new OfferRequest(10, "FLATX%", 300, segments);
        
        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(200, 3, 10);
        
        // Debugging output
        System.out.println("Set Offer Request: " + offerRequest);
        System.out.println("Apply Cart Offer: " + applyOfferRequest);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);
        
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int updatedCartValue = responseJson.get("cart_value").asInt();
        double expectedCartValue = 0; // Flat discount calculation
        Assert.assertEquals(expectedCartValue, updatedCartValue, 0.01);
        System.out.println("Updated Cart Value after Applying Offer: " + updatedCartValue);
    }

    @Test
    public void testApplyFlatNegativePercentOfferForCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(11, "FLATX%", -100, segments);

        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(200, 1, 11);

        // Debugging output
        System.out.println("Set Offer Request: " + offerRequest);
        System.out.println("Apply Cart Offer: " + applyOfferRequest);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int cartStatusCode=0;
        try {
         cartStatusCode = responseJson.get("status").asInt();
        }catch(Exception e) {
        	System.out.println("Eror, Status code node found");
           	Assert.assertEquals(true, false);
        }
        Assert.assertEquals(400, cartStatusCode);
        System.out.println("Bad Request: Negative Flat Percentage Offer");
    }

    @Test
    public void testApplyOfferWithMultipleUserInSameSegement() throws Exception {
        List<String> segments1 = new ArrayList<>();
        segments1.add("p1");
        OfferRequest offerRequest1 = new OfferRequest(12, "FLATX", 20, segments1);
        
        List<String> segments2 = new ArrayList<>();
        segments2.add("p2");
        OfferRequest offerRequest2 = new OfferRequest(12, "FLATX", 20, segments2);
        
        List<String> segments3 = new ArrayList<>();
        segments3.add("p3");
        OfferRequest offerRequest3 = new OfferRequest(12, "FLATX", 20, segments3);

        boolean result1 = addOffer(offerRequest1);
        Assert.assertEquals(result1, true);
        boolean result2 = addOffer(offerRequest2);
        Assert.assertEquals(result2, true);
        boolean result3 = addOffer(offerRequest3);
        Assert.assertEquals(result3, true);

        ApplyOfferRequest applyOfferRequest1 = new ApplyOfferRequest(200, 1, 12);
        ApplyOfferRequest applyOfferRequest2 = new ApplyOfferRequest(200, 2, 12);
        ApplyOfferRequest applyOfferRequest3 = new ApplyOfferRequest(200, 3, 12);

        // Debugging output
        System.out.println("Offer Request 1: " + offerRequest1);
        System.out.println("Apply Cart Offer 1: " + applyOfferRequest1);
        System.out.println("Offer Request 2: " + offerRequest2);
        System.out.println("Apply Cart Offer 2: " + applyOfferRequest2);
        System.out.println("Offer Request 3: " + offerRequest3);
        System.out.println("Apply Cart Offer 3: " + applyOfferRequest3);

        String responseMessage1 = applyCartOffer(applyOfferRequest1);
        System.out.println("Response Message 1: " + responseMessage1);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson1 = objectMapper.readTree(responseMessage1);
        int updatedCartValue1 = responseJson1.get("cart_value").asInt();
        double expectedCartValue1 = 200 - 20; // Flat discount calculation
        Assert.assertEquals(expectedCartValue1, updatedCartValue1, 0.01);
        System.out.println("Updated Cart Value 1 after Applying Offer: " + updatedCartValue1);

        String responseMessage2 = applyCartOffer(applyOfferRequest2);
        System.out.println("Response Message 2: " + responseMessage2);
        ObjectMapper objectMapper2 = new ObjectMapper();
        JsonNode responseJson2 = objectMapper2.readTree(responseMessage2);
        int updatedCartValue2 = responseJson2.get("cart_value").asInt();
        double expectedCartValue2 = 200 - 20; // Flat discount calculation
        Assert.assertEquals(expectedCartValue2, updatedCartValue2, 0.01);
        System.out.println("Updated Cart Value 2 after Applying Offer: " + updatedCartValue2);

        String responseMessage3 = applyCartOffer(applyOfferRequest3);
        System.out.println("Response Message 3: " + responseMessage3);
        ObjectMapper objectMapper3 = new ObjectMapper();
        JsonNode responseJson3 = objectMapper3.readTree(responseMessage3);
        int updatedCartValue3 = responseJson3.get("cart_value").asInt();
        double expectedCartValue3 = 200 - 20; // Flat discount calculation
        Assert.assertEquals(expectedCartValue3, updatedCartValue3, 0.01);
        System.out.println("Updated Cart Value 3 after Applying Offer: " + updatedCartValue3);
    }

    @Test
    public void testApplyFlatAmountOfferForNegativeCartValue() throws Exception {
        List<String> segments = new ArrayList<>();
        segments.add("p1");
        OfferRequest offerRequest = new OfferRequest(12, "FLATX", 20, segments);

        boolean result = addOffer(offerRequest);
        Assert.assertEquals(result, true);
        ApplyOfferRequest applyOfferRequest = new ApplyOfferRequest(-50, 1, 12);

        // Debugging output
        System.out.println("Set Offer Request: " + offerRequest);
        System.out.println("Apply Cart Offer: " + applyOfferRequest);

        String responseMessage = applyCartOffer(applyOfferRequest);
        System.out.println("Response Message: " + responseMessage);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(responseMessage);
        int cartStatusCode=0;
        try {
         cartStatusCode = responseJson.get("status").asInt();
        }catch(Exception e) {
        	System.out.println("Eror, Status code node found");
           	Assert.assertEquals(true, false);
        }
        Assert.assertEquals(400, cartStatusCode);
        System.out.println("Bad Request: Negative Flat Amount Offer");
    }

	public boolean addOffer(OfferRequest offerRequest) throws Exception {
		String urlString = "http://localhost:9001/api/v1/offer";
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();

		String POST_PARAMS = mapper.writeValueAsString(offerRequest);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request did not work.");
		}
		return true;
	}
	
	public String applyCartOffer(ApplyOfferRequest applyOfferRequest) throws Exception {
		String urlString = "http://localhost:9001/api/v1/cart/apply_offer";
		URL url = new URL(urlString);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setRequestProperty("Content-Type", "application/json");

		ObjectMapper mapper = new ObjectMapper();

		String POST_PARAMS = mapper.writeValueAsString(applyOfferRequest);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		StringBuffer response = null;
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request did not work.");
		}
		return response.toString();
	}
}
