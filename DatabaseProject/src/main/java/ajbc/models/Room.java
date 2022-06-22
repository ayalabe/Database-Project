package ajbc.models;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class Room {
	
	private static int counter = 0;
	private int id;
	private int number;
	@BsonProperty(value="has_bath")
	private boolean hasBath;

	
	public Room(int number, boolean hasBath) {
		setNumber(number);
		setHasBath(hasBath);
		setId(counter++);
	}

	public Room(){}
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isHasBath() {
		return hasBath;
	}

	public void setHasBath(boolean hasBath) {
		this.hasBath = hasBath;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", hasBath=" + hasBath + "]";
	}
	
	

}