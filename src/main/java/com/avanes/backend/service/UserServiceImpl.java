package com.avanes.backend.service;

import com.avanes.backend.models.User;
import com.avanes.backend.repository.UserRepository;
import com.avanes.backend.service.interfaces.UserService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String[] CSV_HEADERS = {
            "userId", "username", "password", "role", "fullName",
            "email", "phone", "nic", "dateOfBirth"
    };

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + user.getUserId()));

        // Update fields
        existingUser.setUsername(user.getUsername());
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setNic(user.getNic());
        existingUser.setDateOfBirth(user.getDateOfBirth());
        existingUser.setRole(user.getRole());

        // Only update password if provided
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void importUsersFromCsv(MultipartFile file) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<User> users = new ArrayList<>();

            for (CSVRecord csvRecord : csvParser) {
                User user = new User();

                // Skip header row if it exists
                if (csvParser.getCurrentLineNumber() == 1) {
                    continue;
                }

                // Try to get userId for update if present and valid
                String userIdStr = csvRecord.get("userId");
                if (!userIdStr.isEmpty()) {
                    try {
                        Integer userId = Integer.parseInt(userIdStr);
                        user.setUserId(userId);
                    } catch (NumberFormatException e) {
                        // Skip invalid userId
                    }
                }

                user.setUsername(csvRecord.get("username"));

                // Handle password (only encode if it's a new password)
                String password = csvRecord.get("password");
                if (!password.isEmpty()) {
                    user.setPassword(passwordEncoder.encode(password));
                } else if (user.getUserId() != null) {
                    // Existing user, keep current password
                    User existingUser = userRepository.findById(user.getUserId())
                            .orElse(null);
                    if (existingUser != null) {
                        user.setPassword(existingUser.getPassword());
                    }
                }

                user.setRole(csvRecord.get("role"));
                user.setFullName(csvRecord.get("fullName"));
                user.setEmail(csvRecord.get("email"));
                user.setPhone(csvRecord.get("phone"));
                user.setNic(csvRecord.get("nic"));
                user.setDateOfBirth(csvRecord.get("dateOfBirth"));

                users.add(user);
            }

            userRepository.saveAll(users);
        }
    }

    @Override
    public byte[] exportUsersToCsv() throws IOException {
        List<User> users = userRepository.findAll();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (CSVPrinter csvPrinter = new CSVPrinter(new OutputStreamWriter(out), CSVFormat.DEFAULT.withHeader(CSV_HEADERS))) {
            for (User user : users) {
                csvPrinter.printRecord(
                        user.getUserId(),
                        user.getUsername(),
                        "", // Don't export passwords
                        user.getRole(),
                        user.getFullName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getNic(),
                        user.getDateOfBirth()
                );
            }
        }

        return out.toByteArray();
    }
}