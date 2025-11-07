package com.example.cp6_java.entity;

import com.example.cp6_java.entity.enun.StatusEmail;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_EMAIL")

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class EmailEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID emailId;

    @Column(name = "owner_ref")
    private String ownerRef;

    @Column(name = "email_from")
    private String emailFrom;

    @Column(name = "email_to")
    private String emailTo;

    @Column(name = "subject")
    private String subject;

    @Column(columnDefinition = "CLOB", name = "text")
    private String text;

    @Column(name = "send_date_email")
    private LocalDateTime sendDateEmail;

    @Column(name = "status_email")
    @Enumerated(EnumType.STRING)
    private StatusEmail statusEmail;
}
