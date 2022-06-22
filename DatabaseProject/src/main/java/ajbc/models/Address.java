package ajbc.models;

public class Address {
	
	private String street;
	private int number;
	private String city;
	private String country;
	
	public Address(String street, int number, String city, String country) {
		setStreet(street);
		setNumber(number);
		setCity(city);
		setCountry(country);
	}
	
	public Address() {}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", number=" + number + ", city=" + city + ", country=" + country + "]";
	}
	
	
}
