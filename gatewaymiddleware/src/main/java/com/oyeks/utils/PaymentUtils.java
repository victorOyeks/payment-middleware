package com.oyeks.utils;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

@Service
public class PaymentUtils {

    @Value("${bank.service.url}")
    private String bankServiceApi;

    public <T> String marshalRequest(T request, Class<T> clazz) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(request, stringWriter);
        return stringWriter.toString().replace(" standalone=\"yes\"", "");
    }

    public String convertXmlToJson(String xmlResponse) {
        XMLSerializer xml = new XMLSerializer();
        JSON jObj = xml.read(xmlResponse);
        return jObj.toString();
    }

    public String contactBankService(String xmlRequest) throws UnirestException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(bankServiceApi)
                .header("Content-Type", "text/plain")
                .body(xmlRequest)
                .asString();
        return response.getBody();
    }
}