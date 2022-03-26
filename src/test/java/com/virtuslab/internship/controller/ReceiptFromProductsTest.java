package com.virtuslab.internship.controller;

import com.virtuslab.internship.product.ProductDb;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReceiptFromProductsTest extends AbstractTest{
    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldApply15And10PercentDiscount() throws Exception {
        // Given
        String uri = "/products/fifteenAndTenDiscount";
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Bread", 10);
        products.put("Cereals", 2);

        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var cereals = productDb.getProduct("Cereals");
        var expectedTotalPrice = bread.price().multiply((BigDecimal.valueOf(10)))
                .add(cereals.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.85))
                .multiply(BigDecimal.valueOf(0.9));

        String inputJson = super.mapToJson(products);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var totalPrice = BigDecimal.valueOf(content.getDouble("totalPrice"));

        assertThat(expectedTotalPrice,  Matchers.comparesEqualTo(totalPrice));
        assertEquals(200, status);
    }

    @Test
    public void shouldApply10andNot15PercentDiscount() throws Exception {
        // Given
        String uri = "/products/tenDiscount";
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Pork", 10);
        products.put("Steak", 2);

        var productDb = new ProductDb();
        var pork = productDb.getProduct("Pork"); // no grains
        var steak = productDb.getProduct("Steak");
        var expectedTotalPrice = pork.price().multiply((BigDecimal.valueOf(10)))
                .add(steak.price().multiply(BigDecimal.valueOf(2)))
                .multiply(BigDecimal.valueOf(0.9));

        String inputJson = super.mapToJson(products);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var totalPrice = BigDecimal.valueOf(content.getDouble("totalPrice"));

        assertThat(expectedTotalPrice,  Matchers.comparesEqualTo(totalPrice));
        assertEquals(200, status);
    }

    @Test
    public void shouldApply15AndNot10PercentDiscount() throws Exception {
        // Given
        String uri = "/products/fifteenDiscount";
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Bread", 10);

        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread");
        var expectedTotalPrice = bread.price().multiply((BigDecimal.valueOf(10)))
                .multiply(BigDecimal.valueOf(0.85));

        String inputJson = super.mapToJson(products);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var totalPrice = BigDecimal.valueOf(content.getDouble("totalPrice"));

        assertThat(expectedTotalPrice,  Matchers.comparesEqualTo(totalPrice));
        assertEquals(200, status);
    }

    @Test
    public void shouldNotApply15Nor10PercentDiscount() throws Exception {
        // Given
        String uri = "/products/noneDiscount";
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Potato", 5);
        products.put("Banana", 5);

        var productDb = new ProductDb();
        var potato = productDb.getProduct("Potato");
        var banana = productDb.getProduct("Banana");
        var expectedTotalPrice = potato.price().multiply((BigDecimal.valueOf(5)))
                .add(banana.price().multiply(BigDecimal.valueOf(5)));

        String inputJson = super.mapToJson(products);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var totalPrice = BigDecimal.valueOf(content.getDouble("totalPrice"));

        assertThat(expectedTotalPrice,  Matchers.comparesEqualTo(totalPrice));
        assertEquals(200, status);
    }

    @Test
    public void shouldAcceptEmptyBasket() throws Exception {
        // Given
        String uri = "/products/noneDiscount";
        HashMap<String, Integer> products = new HashMap<>();

        String inputJson = super.mapToJson(products);
        var expectedTotalPrice = BigDecimal.valueOf(0);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var totalPrice = BigDecimal.valueOf(content.getDouble("totalPrice"));

        assertThat(expectedTotalPrice,  Matchers.comparesEqualTo(totalPrice));
        assertEquals(200, status);
    }

    private boolean JSONhasValue(JSONArray json, String value) throws JSONException {
        for(int i = 0; i < json.length(); i++) {
            if (json.get(i).equals(value)) return true;
        }
        return false;
    }

    @Test
    public void shouldContainAppliedDiscounts() throws Exception {
        // Given
        String uri = "/products/fifteenAndTenDiscount";
        HashMap<String, Integer> products = new HashMap<>();
        products.put("Bread", 10);
        products.put("Cereals", 2);

        String inputJson = super.mapToJson(products);

        // When
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(inputJson)).andReturn();

        // Then
        int status = mvcResult.getResponse().getStatus();
        JSONObject content = new JSONObject(mvcResult.getResponse().getContentAsString());
        var discounts = content.getJSONArray("discounts");

        assertTrue(JSONhasValue(discounts, "FifteenPercentDiscount"));
        assertTrue(JSONhasValue(discounts, "TenPercentDiscount"));

    }

}
