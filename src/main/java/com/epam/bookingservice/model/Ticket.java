package com.epam.bookingservice.model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "eventId", "userId", "category", "place"})
@Entity
public class Ticket {

    @Id
    @XmlAttribute
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @XmlAttribute
    @Column(name = "event_id")
    private Long eventId;
    @NonNull
    @XmlAttribute
    @Column(name = "user_id")
    private Long userId;
    @NonNull
    @XmlAttribute
    private Integer place;
    @NonNull
    @XmlAttribute
    private Category category;

    public enum Category {STANDARD, PREMIUM, BAR;}
    //    @ManyToOne
//    @JoinColumn(name="event_id", referencedColumnName = "id")
//    public Event event;
//    @ManyToOne
//    @JoinColumn(name="user_id", referencedColumnName = "id")
//    public User user;


    public Ticket(Long eventId, Long userId, Integer place, Category category) {
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
        this.place = place;
    }
}


