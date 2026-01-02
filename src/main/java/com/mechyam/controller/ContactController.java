package com.mechyam.controller;

import com.mechyam.dto.ApiResponse;
import com.mechyam.dto.ContactRequest;
import com.mechyam.entity.Contact;
import com.mechyam.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@CrossOrigin(origins = "*")
public class ContactController {
    
    @Autowired
    private ContactService contactService;
    
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitContactForm(@Valid @RequestBody ContactRequest contactRequest) {
        try {
            Contact savedContact = contactService.saveContact(contactRequest);
            return ResponseEntity.ok(ApiResponse.success(
                "Contact form submitted successfully", 
                savedContact.getId()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to submit contact form: " + e.getMessage()));
        }
    }
    
    //pk
    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllContacts(
            @RequestParam(defaultValue = "1") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        try {
            var contactsPage = contactService.getAllContacts(pageNo, pageSize);
            return ResponseEntity.ok(ApiResponse.success(
                "Contacts retrieved successfully",
                contactsPage
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve contacts: " + e.getMessage()));
        }
    }//pk

    
    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getContactsByStatus(@PathVariable String status) {
        try {
            List<Contact> contacts = contactService.getContactsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(
                "Contacts retrieved successfully", 
                contacts
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve contacts: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getContactById(@PathVariable Long id) {
        try {
            Contact contact = contactService.getContactById(id);
            return ResponseEntity.ok(ApiResponse.success(
                "Contact retrieved successfully", 
                contact
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve contact: " + e.getMessage()));
        }
    }
}