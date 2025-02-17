package com.eg.HousingLibrary.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {
    public void sendOverdueReminder(String email) {
        try {
            Thread.sleep(3000); // Simulating delay
            log.info("Reminder sent to "+ email);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
