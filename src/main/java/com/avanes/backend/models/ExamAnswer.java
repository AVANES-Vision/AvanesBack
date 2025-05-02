//package com.avanes.backend.models;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
////@Entity
////@Data
////@NoArgsConstructor
////@AllArgsConstructor
////public class ExamAnswer {
////    @Id
////    @GeneratedValue(strategy = GenerationType.IDENTITY)
////    private Long id;
////
////    @ManyToOne
////    @JoinColumn(name = "exam_id")
////    private Exam exam;
////
////    @ManyToOne
////    @JoinColumn(name = "user_id")
////    private User user;
////
////    @ManyToOne
////    @JoinColumn(name = "question_id")
////    private Question question;
////
////    @ManyToOne
////    @JoinColumn(name = "answer_id")
////    private Answer answer;
////}