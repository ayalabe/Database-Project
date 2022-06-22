package ajbc.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Hotel {
	
	private ObjectId id;
	private HotelsName name;
	private Address address;
	private int rank;
	private List<Room> rooms;
	@BsonProperty(value="price_per_night")
	private double pricePerNight;
	private List<ObjectId> orders;
	@BsonProperty(value="People_in_room")
	private int peopleInRoom;
	
	public Hotel(){}
	
	public Hotel(HotelsName name, Address address, int rank, List<Room> rooms, double pricePerNight,int peopleInRoom) {
		setName(name);
		setAddress(address);
		setRank(rank);
		setRooms(rooms);
		setPricePerNight(pricePerNight);
		this.orders = new ArrayList<>();
		setPeopleInRoom(peopleInRoom);
	}
	
	
	public void setPeopleInRoom(int peopleInRoom) {
		this.peopleInRoom = peopleInRoom;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void addOrder(ObjectId order)
	{
		this.orders.add(order);
	}
	
	public HotelsName getName() {
		return name;
	}
	public void setName(HotelsName name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public double getPricePerNight() {
		return pricePerNight;
	}
	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}
	public List<ObjectId> getOrders() {
		return orders;
	}
	public void setOrders(List<ObjectId> orders) {
		this.orders = orders;
	}
	public ObjectId getId() {
		return id;
	}

	public int getPeopleInRoom() {
		return peopleInRoom;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", address=" + address + ", rank=" + rank + ", rooms=" + rooms
				+ ", pricePerNight=" + pricePerNight + ", orders=" + orders + ", PeopleInRoom=" + peopleInRoom + "]";
	}
	
	

	

	
	

}