//package com.mechyam.service;
//
//import java.io.File;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Service;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
////import java.io.File;
//
//@Service
//public class EmailService {
//    
//    @Autowired
//    private JavaMailSender mailSender;
//    
//    @Value("${app.manager.email}")
//    private String managerEmail;
//    
//    @Value("${app.email.subject.prefix}")
//    private String emailSubjectPrefix;
//    
//    @Autowired
//    private FileStorageService fileStorageService;
//    
//    public void sendContactNotification(String name, String email, String phone, 
//                                      String service, String message) {
//        SimpleMailMessage mailMessage = new SimpleMailMessage();
//        mailMessage.setTo(managerEmail);
//        mailMessage.setSubject(emailSubjectPrefix + " New Contact Form Submission");
//        mailMessage.setText(createEmailContent(name, email, phone, service, message));
//        
//        mailSender.send(mailMessage);
//    }
//    
//    private String createEmailContent(String name, String email, String phone, 
//                                    String service, String message) {
//        return String.format(
//            "New Contact Form Submission Received:\n\n" +
//            "Name: %s\n" +
//            "Email: %s\n" +
//            "Phone: %s\n" +
//            "Service: %s\n" +
//            "Message: %s\n\n" +
//            "This message was sent automatically from Mechyam Contact System.",
//            name, email, phone != null ? phone : "Not provided", 
//            service != null ? service : "Not specified", message
//        );
//        
//    }
//    
//    // i have added these for jobApplictions
// // Add these methods to your existing EmailService class
//
//    public void sendJobApplicationNotification(String jobTitle, String applicantName, 
//            String applicantEmail, String applicantPhone,
//            String resumeFilePath, String resumeFileName) {
//try {
//MimeMessage message = mailSender.createMimeMessage();
//MimeMessageHelper helper = new MimeMessageHelper(message, true); // true for multipart
//
//helper.setTo(managerEmail);
//helper.setSubject(emailSubjectPrefix + " New Job Application - " + jobTitle);
//helper.setText(createJobApplicationEmailContent(jobTitle, applicantName, applicantEmail, applicantPhone));
//
//// Attach resume file
//if (resumeFilePath != null && !resumeFilePath.isEmpty()) {
//File resumeFile = fileStorageService.loadFile(resumeFilePath).toFile();
//if (resumeFile.exists()) {
//helper.addAttachment(resumeFileName, resumeFile);
//}
//}
//
//mailSender.send(message);
//} catch (MessagingException e) {
//System.err.println("Failed to send email with attachment: " + e.getMessage());
//// Fallback: send without attachment
//sendJobApplicationNotificationWithoutAttachment(jobTitle, applicantName, applicantEmail, applicantPhone);
//} catch (Exception e) {
//System.err.println("Failed to send email: " + e.getMessage());
//}
//}
//    
//    // Fallback method without attachment
//    private void sendJobApplicationNotificationWithoutAttachment(String jobTitle, String applicantName, 
//                                                                String applicantEmail, String applicantPhone) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(managerEmail);
//            mailMessage.setSubject(emailSubjectPrefix + " New Job Application - " + jobTitle);
//            mailMessage.setText(createJobApplicationEmailContent(jobTitle, applicantName, applicantEmail, applicantPhone));
//            
//            mailSender.send(mailMessage);
//        } catch (Exception e) {
//            System.err.println("Failed to send fallback email: " + e.getMessage());
//        }
//    } 
//
//    private String createJobApplicationEmailContent(String jobTitle, String applicantName, 
//            String applicantEmail, String applicantPhone) {
//return String.format(
//"New Job Application Received:\n\n" +
//"Job Position: %s\n" +
//"Applicant Name: %s\n" +
//"Email: %s\n" +
//"Phone: %s\n\n" +
//"Resume has been attached to this email.\n\n" +
//"This message was sent automatically from Mechyam Career System.",
//jobTitle, applicantName, applicantEmail, applicantPhone != null ? applicantPhone : "Not provided"
//);
//}
//    // this code i have implemented fot admin part
// // Add to EmailService.java
//    public void sendOTPEmail(String email, String name, String otpCode) {
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            
//            helper.setTo(email);
//            helper.setSubject(emailSubjectPrefix + " Admin Login OTP");
//            helper.setText(createOTPEmailContent(name, otpCode), true); // true for HTML
//            
//            mailSender.send(message);
//            System.out.println("OTP email sent successfully to: " + email);
//        } catch (MessagingException e) {
//            System.err.println("Failed to send OTP email: " + e.getMessage());
//            // Fallback to simple email
//            sendSimpleOTPEmail(email, name, otpCode);
//        }
//    }
//
//    private void sendSimpleOTPEmail(String email, String name, String otpCode) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(email);
//            mailMessage.setSubject(emailSubjectPrefix + " Admin Login OTP");
//            mailMessage.setText(createSimpleOTPEmailContent(name, otpCode));
//            
//            mailSender.send(mailMessage);
//        } catch (Exception e) {
//            System.err.println("Failed to send simple OTP email: " + e.getMessage());
//        }
//    }
//
//    private String createOTPEmailContent(String name, String otpCode) {
//        return String.format(
//            "<!DOCTYPE html>" +
//            "<html>" +
//            "<head>" +
//            "    <style>" +
//            "        body { font-family: Arial, sans-serif; }" +
//            "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
//            "        .otp-code { font-size: 32px; font-weight: bold; color: #2563eb; text-align: center; margin: 20px 0; }" +
//            "        .note { color: #6b7280; font-size: 14px; }" +
//            "    </style>" +
//            "</head>" +
//            "<body>" +
//            "    <div class='container'>" +
//            "        <h2>Mechyam Admin Login OTP</h2>" +
//            "        <p>Hello <strong>%s</strong>,</p>" +
//            "        <p>Your One-Time Password (OTP) for admin login is:</p>" +
//            "        <div class='otp-code'>%s</div>" +
//            "        <p class='note'>This OTP is valid for 5 minutes. Do not share it with anyone.</p>" +
//            "        <p>If you didn't request this OTP, please ignore this email.</p>" +
//            "        <br>" +
//            "        <p>Best regards,<br>Mechyam Team</p>" +
//            "    </div>" +
//            "</body>" +
//            "</html>",
//            name, otpCode
//        );
//    }
//
//    private String createSimpleOTPEmailContent(String name, String otpCode) {
//        return String.format(
//            "Mechyam Admin Login OTP\n\n" +
//            "Hello %s,\n\n" +
//            "Your One-Time Password (OTP) for admin login is:\n\n" +
//            "%s\n\n" +
//            "This OTP is valid for 5 minutes. Do not share it with anyone.\n\n" +
//            "If you didn't request this OTP, please ignore this email.\n\n" +
//            "Best regards,\n" +
//            "Mechyam Team",
//            name, otpCode
//        );
//    } 
//}


package com.mechyam.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${app.manager.email}")
    private String managerEmail;
    
    @Value("${app.email.subject.prefix}")
    private String emailSubjectPrefix;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    // Existing contact notification method
    public void sendContactNotification(String name, String email, String phone, 
                                      String service, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(managerEmail);
        mailMessage.setSubject(emailSubjectPrefix + " New Contact Form Submission");
        mailMessage.setText(createEmailContent(name, email, phone, service, message));
        
        mailSender.send(mailMessage);
    }
    
    private String createEmailContent(String name, String email, String phone, 
                                    String service, String message) {
        return String.format(
            "New Contact Form Submission Received:\n\n" +
            "Name: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n" +
            "Service: %s\n" +
            "Message: %s\n\n" +
            "This message was sent automatically from Mechyam Contact System.",
            name, email, phone != null ? phone : "Not provided", 
            service != null ? service : "Not specified", message
        );
    }
    
    // Job application methods
    public void sendJobApplicationNotification(String jobTitle, String applicantName, 
            String applicantEmail, String applicantPhone,
            String resumeFilePath, String resumeFileName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(managerEmail);
            helper.setSubject(emailSubjectPrefix + " New Job Application - " + jobTitle);
            helper.setText(createJobApplicationEmailContent(jobTitle, applicantName, applicantEmail, applicantPhone));

            // Attach resume file
            if (resumeFilePath != null && !resumeFilePath.isEmpty()) {
                File resumeFile = fileStorageService.loadFile(resumeFilePath).toFile();
                if (resumeFile.exists()) {
                    helper.addAttachment(resumeFileName, resumeFile);
                }
            }

            mailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send email with attachment: " + e.getMessage());
            // Fallback: send without attachment
            sendJobApplicationNotificationWithoutAttachment(jobTitle, applicantName, applicantEmail, applicantPhone);
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
    
    // Fallback method without attachment
    private void sendJobApplicationNotificationWithoutAttachment(String jobTitle, String applicantName, 
                                                                String applicantEmail, String applicantPhone) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(managerEmail);
            mailMessage.setSubject(emailSubjectPrefix + " New Job Application - " + jobTitle);
            mailMessage.setText(createJobApplicationEmailContent(jobTitle, applicantName, applicantEmail, applicantPhone));
            
            mailSender.send(mailMessage);
        } catch (Exception e) {
            System.err.println("Failed to send fallback email: " + e.getMessage());
        }
    } 

    private String createJobApplicationEmailContent(String jobTitle, String applicantName, 
            String applicantEmail, String applicantPhone) {
        return String.format(
            "New Job Application Received:\n\n" +
            "Job Position: %s\n" +
            "Applicant Name: %s\n" +
            "Email: %s\n" +
            "Phone: %s\n\n" +
            "Resume has been attached to this email.\n\n" +
            "This message was sent automatically from Mechyam Career System.",
            jobTitle, applicantName, applicantEmail, applicantPhone != null ? applicantPhone : "Not provided"
        );
    }
    
    // OTP Email Methods - Clean backend-only version
    public void sendOTPEmail(String email, String otp) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject(emailSubjectPrefix + " Admin Login OTP Verification");
            mailMessage.setText(createOTPEmailContent(otp));
            
            mailSender.send(mailMessage);
            System.out.println("OTP email sent successfully to: " + email);
        } catch (Exception e) {
            System.err.println("Failed to send OTP email: " + e.getMessage());
        }
    }

    // Overloaded method for backward compatibility
    public void sendOTPEmail(String email, String name, String otpCode) {
        sendOTPEmail(email, otpCode);
    }

    private String createOTPEmailContent(String otpCode) {
        return String.format(
            "MECHYAM ADMIN SECURITY VERIFICATION\n\n" +
            "Hello Admin,\n\n" +
            "Your One-Time Password (OTP) for admin login verification is:\n\n" +
            "OTP: %s\n\n" +
            "SECURITY NOTICE:\n" +
            "- This OTP is valid for 5 minutes\n" +
            "- Do not share this code with anyone\n" +
            "- If you didn't request this OTP, please contact your system administrator immediately\n\n" +
            "This is an automated security message from Mechyam Admin System.\n\n" +
            "© 2024 Mechyam. All rights reserved.",
            otpCode
        );
    }
    
//    // Additional method for sending OTP with custom subject
//    public void sendOTPEmail(String email, String otp, String customSubject) {
//        try {
//            SimpleMailMessage mailMessage = new SimpleMailMessage();
//            mailMessage.setTo(email);
//            mailMessage.setSubject(emailSubjectPrefix + " " + customSubject);
//            mailMessage.setText(createOTPEmailContent(otp));
//            
//            mailSender.send(mailMessage);
//        } catch (Exception e) {
//            System.err.println("Failed to send custom OTP email: " + e.getMessage());
//        }
//    }
}