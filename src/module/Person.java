package module;

public class Person extends AbstractBaseEntity {
    //encapsulation used (using private access modifiers)
    private String personName;
    private String email;
    private String phoneNumber;
    private String address;

    // Default Constructor
    public Person() {
        super(0); // // Call the superclass constructor(assuming a default ID of 0 for now)
    }

    // Parameterized Constructor
    public Person(int id, String personName, String email, String phoneNumber, String address) {
        super(id);// Call the superclass constructor
        this.personName = personName.trim();
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters
    public String getPersonName() {
        return personName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "module.Person{" +
                "id=" + getId() +
                ", personName='" + personName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", createdDate=" + getCreatedDate() +
                '}';
    }
}
