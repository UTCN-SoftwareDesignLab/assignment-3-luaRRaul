package com.stockhelt.backend.messagingstompwebsocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.util.HtmlUtils;

import static com.stockhelt.backend.UrlMapping.TOPIC;
import static com.stockhelt.backend.UrlMapping.WS;

@CrossOrigin(origins = "*", maxAge = 3600)
@Controller
public class MessageController {
    @MessageMapping("/arrival")
    @SendTo(TOPIC+"/arrivals")
    public PatientArrivedResponse patientArrivedResponse(PatientArrivedMessage message) throws InterruptedException{
        Thread.sleep(1000);
        System.out.println(message.getPatientId());
        return new PatientArrivedResponse("Hello doctor, your patient has arrived"+ HtmlUtils.htmlEscape(message.getPatientId()));
    }
}
