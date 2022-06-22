package ajbc.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

import ajbc.crud.PojoMapping;
import ajbc.models.Address;
import ajbc.models.Customer;
import ajbc.models.Hotel;
import ajbc.models.HotelsName;
import ajbc.models.Order;
import ajbc.models.Room;

public class SeedDB {

	public static InsertManyResult createCustomersCollection(MongoDatabase DB) {
		MongoCollection<Document> customersCol = DB.getCollection("customers");
		InsertManyResult result = customersCol.insertMany(Arrays.asList(
				new Document("first_name", "Hodaya").append("last_name", "david").append("country", "Israel").append("orders",new ArrayList<>()),
				new Document("first_name", "Shira").append("last_name", "ben").append("country", "China").append("orders",new ArrayList<>()),
				new Document("first_name", "noam").append("last_name", "Levi").append("country", "Israel").append("orders",new ArrayList<>()),
				new Document("first_name", "hila").append("last_name", "David").append("country", "Japan").append("orders",new ArrayList<>())));

		return result;
	}
	

	public static InsertManyResult createHotelsCollection(MongoDatabase DB) {
		Address add1 = new Address("levona", 8, "jerusalem", "Israel");
		List<Room> rooms1 = Arrays.asList(new Room(101,true), new Room(102,false), new Room(103, false), new Room(104, true));
		Hotel hermoso = new Hotel(HotelsName.HERMOSO, add1, 4, rooms1, 350,2);
		
		Address add2 = new Address("yafo", 15, "jerusalem", "Israel");
		List<Room> rooms2 = Arrays.asList(new Room(1,true), new Room(2,true), new Room(3,false));
		Hotel lindo = new Hotel(HotelsName.LINDO, add2, 3, rooms2, 200,4);
		
		Address add3 = new Address("shazar",3, "Tiberias", "Israel");
		List<Room> rooms3 = Arrays.asList(new Room(1,true), new Room(2,false));
		Hotel bello = new Hotel(HotelsName.BELLO, add3, 5, rooms3, 700,3);
		
		MongoCollection<Document> hotelsCol = DB.getCollection("hotels");
		
		InsertManyResult result = hotelsCol.insertMany(Arrays.asList(
				new Document("name", hermoso.getName()).append("address", hermoso.getAddress())
				.append("rank", hermoso.getRank()).append("rooms", hermoso.getRooms()).append("price_per_night", hermoso.getPricePerNight())
				.append("orders", hermoso.getOrders()).append("People_in_room", hermoso.getPeopleInRoom()),
				
				new Document("name", lindo.getName()).append("address", lindo.getAddress())
				.append("rank", lindo.getRank()).append("rooms", lindo.getRooms()).append("price_per_night", lindo.getPricePerNight())
				.append("orders", lindo.getOrders()).append("People_in_room", lindo.getPeopleInRoom()),
				
				new Document("name", bello.getName()).append("address", bello.getAddress())
				.append("rank", bello.getRank()).append("rooms", bello.getRooms()).append("price_per_night", bello.getPricePerNight())
				.append("orders", bello.getOrders()).append("People_in_room", bello.getPeopleInRoom())));
		
		return result;
	}

	
	public static void createOrdersCollection(MongoDatabase DB){
		
		PojoMapping dao = new PojoMapping(DB);
		
		List<Hotel> hotels = dao.getAllHotels();
		ObjectId hermosoId = hotels.get(0).getId();
		ObjectId lindoId = hotels.get(1).getId();
		ObjectId belloId = hotels.get(2).getId();
	
		List<Customer> customers = dao.getAllCustomers();
		ObjectId cust1Id = customers.get(0).getId();
		ObjectId cust2Id = customers.get(1).getId();
		ObjectId cust3Id = customers.get(2).getId();
		
		System.out.println(hermosoId);
		dao.createOrder(new Order(hermosoId, cust1Id, LocalDate.now(), LocalDate.of(2022, 8, 1), 3, 2));
		dao.createOrder(new Order(lindoId, cust2Id, LocalDate.now(), LocalDate.of(2019, 1, 1), 4, 4));
		dao.createOrder(new Order(belloId, cust3Id, LocalDate.now(), LocalDate.of(2020, 2, 5), 2, 3));

	}
}