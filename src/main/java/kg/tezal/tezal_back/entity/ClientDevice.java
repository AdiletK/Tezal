package kg.tezal.tezal_back.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * Device that client use to login to the application
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client_device")
public class ClientDevice {

    /**
     * Identification
     */
    @Id
    @SequenceGenerator(name = "client_device_seq", sequenceName = "client_device_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_device_seq")
    private Long id;

    /**
     * Client
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Phone number on 996ХХХУУУУУУ format
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * Password
     * Requirements for the password will be set later
     */
    @Column(name = "password")
    private String password;

    /**
     * Imei code
     */
    @Column(name = "imei")
    private String imei;

    /**
     * Last enter date
     */
    @Column(name = "last_enter_date")
    private Date lastEnterDate;

    /**
     * Status
     * active = true / false
     */
    @Column(name = "status", columnDefinition = "boolean default true")
    private Boolean status;

}
