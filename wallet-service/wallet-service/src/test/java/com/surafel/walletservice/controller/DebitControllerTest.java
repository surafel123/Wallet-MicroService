package com.surafel.walletservice.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.surafel.walletservice.model.TransactionsList;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:file:./src/test/resources/walletService",
                "spring.jpa.hibernate.ddl-auto=create-drop"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DebitControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private JacksonTester<TransactionsList>   json; 
    
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Sql("/insert_to_userAccountInfo.sql")
    @DisplayName("Successful debit transaction")
    void debit() {
        long transactionId = 501; //transactionId not used before
        long userIdInTestDb = 1111; //UserAccountInfo found in the database
        double amount = 300.0;
        double balanceInTestDb = 5000.0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("transactionId", transactionId);
        map.add("amount", amount);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);


        double balance =
                testRestTemplate.postForObject(
                        "/wallet/debit/{userId}",
                        httpEntity,
                        Double.class,
                        userIdInTestDb);

        double expected = balanceInTestDb - amount;

        assertEquals(expected, balance);
    }

    @Test
    @Sql("/insert_to_transaction.sql")
    @DisplayName("Unsuccessful debit transaction because of duplicate transactionId")
    void duplicatedTransactionId() throws IOException {
        long transactionIdInTestDb = 2222; //transactionId used before
        long userIdInTestDb = 1111; //UserAccountInfo found in the database
        double amount = 300.0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("transactionId", transactionIdInTestDb);
        map.add("amount", amount);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);


       TransactionsList transaction =
                testRestTemplate.postForObject("/wallet/debit/{transactionId}", httpEntity, TransactionsList.class, transactionIdInTestDb);

        String expected_json = "{\"transactionId\":" + transactionIdInTestDb +
                ",\"type\":\"DEBIT\",\"amount\":" + amount +
                ",\"success\":\"TRANSACTION_ID_ALREADY_USED\",\"balance\":null}";

        assertEquals(expected_json, json.write(transaction).getJson());
        //TODO Assert False
    }

    @Test
    @Disabled
    @DisplayName("Unsuccessful debit transaction because of userId not found")
    void userNotFound() {
        long transactionId = 501; //transactionId not used before
        long userIdNotInTestDb = 11; //UserAccountInfo not found in the database
        double amount = 300.0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("transactionId", transactionId);
        map.add("amount", amount);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);


        double balance =
                testRestTemplate.postForObject(
                        "/wallet/debit/{userId}",
                        httpEntity,
                        Double.class,
                        userIdNotInTestDb);

        //TODO
    }

    @Test
    @Sql("/insert_to_userAccountInfo.sql")
    @DisplayName("Unsuccessful debit transaction because of insufficient balance")
    void insufficient_balance() {
        long transactionId = 501; //transactionId not used before
        long userIdInTestDb = 1; //UserAccountInfo found in the database
        double amount = 6000.0;
        double balanceInTestDb = 5000.0;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        map.add("transactionId", transactionId);
        map.add("amount", amount);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(map, headers);


        double balance =
                testRestTemplate.postForObject(
                        "/wallet/debit/{userId}",
                        httpEntity,
                        Double.class,
                        userIdInTestDb);

        assertTrue(balance > balance);
    }
}