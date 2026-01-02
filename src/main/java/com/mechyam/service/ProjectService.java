package com.mechyam.service;


import com.cloudinary.Cloudinary;

import com.cloudinary.utils.ObjectUtils;
import com.mechyam.entity.Project;
import com.mechyam.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

//@Service
//public class ProjectService {
// 
//    @Autowired
//    private ProjectRepository projectRepository;
// 
//    @Autowired
//    private Cloudinary cloudinary;
// 
//    // Create project with image upload
//    public Project createProject(String title, String description, MultipartFile imageFile) throws IOException {
//        // Upload image to Cloudinary
//        Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), 
//            ObjectUtils.asMap(
//                "folder", "projects",
//                "use_filename", true,
//                "unique_filename", true
//            ));
//        
//        String imageUrl = uploadResult.get("url").toString();
//        String publicId = uploadResult.get("public_id").toString();
//        
//        Project project = new Project(title, description, imageUrl, publicId);
//        
//     
//        return projectRepository.save(project);
//    }
// 
//   
//    public List<Project> getAllProjects() {
//        return projectRepository.findAll();
//    }
// 
//    
//    public Project getProjectById(Long id) {
//        return projectRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
//    }
// 
// 
//    public void deleteProject(Long id) throws IOException {
//        Project project = getProjectById(id);
//        
//     
//        
//        if (project.getCloudinaryPublicId() != null) {
//            cloudinary.uploader().destroy(project.getCloudinaryPublicId(), ObjectUtils.emptyMap());
//        }
//        
//   
//        projectRepository.deleteById(id);
//    }
//}

//package com.mechyam.service;
//
//import com.cloudinary.Cloudinary;
//import com.cloudinary.utils.ObjectUtils;
//import com.mechyam.entity.Project;
//import com.mechyam.repository.ProjectRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private Cloudinary cloudinary;

    // Create project with image upload
    public Project createProject(String title, String description, MultipartFile imageFile) throws IOException {
        // Upload image to Cloudinary - FIXED: Added proper generics to Map
        Map<?, ?> uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), 
            ObjectUtils.asMap(
                "folder", "projects",
                "use_filename", true,
                "unique_filename", true
            ));
        
        String imageUrl = uploadResult.get("url").toString();
        String publicId = uploadResult.get("public_id").toString();
        
        Project project = new Project(title, description, imageUrl, publicId);
        
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
    }

    @SuppressWarnings("unused")
	public void deleteProject(Long id) throws IOException {
        Project project = getProjectById(id);
        
        if (project.getCloudinaryPublicId() != null) {
            // FIXED: Added proper generics to emptyMap as well
            cloudinary.uploader().destroy(project.getCloudinaryPublicId(), 
                ObjectUtils.<Object, Object>emptyMap());
        }
        
        projectRepository.deleteById(id);
    }
}
