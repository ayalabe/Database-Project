package ajbc.crud;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.models.Customer;
import ajbc.models.Hotel;
import ajbc.models.Order;
import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Accumulators.sum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class PojoMapping {

	private MongoDatabase DB;
	private MongoCollection<Hotel> hotelsCollection;
	private MongoCollection<Customer> customersCollection;
	private MongoCollection<Order> ordersCollection;
	

	public PojoMapping(MongoDatabase DB) {
		this.DB = DB;
		this.hotelsCollection = DB.getCollection("hotels", Hotel.class);
		this.customersCollection = DB.getCollection("customers", Customer.class);
		this.ordersCollection = DB.getCollection("orders", Order.class);
	}

	
	public Hotel getHotelById(ObjectId id) {
		return hotelsCollection.find(eq("_id", id)).first();
	}

	public Customer getCustomerById(ObjectId id) {
		return customersCollection.find(eq("_id", id)).first();
	}
	
	public Order getOrderById(ObjectId id) {
		return ordersCollection.find(eq("_id", id)).first();
	}
	
	public List<Hotel> getAllHotels(){
		return hotelsCollection.find().into(new ArrayList<>());
	}
	
	public List<Customer> getAllCustomers(){
		return customersCollection.find().into(new ArrayList<>());
	} 
	
	//1
	public List<Order> getOrdersByCustomerId(ObjectId customerId){
		return ordersCollection.find(eq("customer_id", customerId)).into(new ArrayList<>());
	}
	
	//2
	public List<Hotel> getHotelsByCity(String city){
		return hotelsCollection.find(eq("address.city", city)).into(new ArrayList<>());
	}
	
	// 3
		public boolean isHotelAvailable(ObjectId hotelId, LocalDate date) {
			Hotel hotel = getHotelById(hotelId);
			Bson match = match(eq("hotel_id", hotelId));
			Bson match2 = match(gte("end_date", date));
			Bson match3 = match(lte("start_date", date));
		
			List<Order> orders = ordersCollection.aggregate(Arrays.asList(match,match2,match3)).into(new ArrayList<>());
			System.out.println( orders.size());
			return hotel.getRooms().size() > orders.size();
		}

		//4
		public InsertOneResult createOrder(Order order) {
			Hotel hotel = getHotelById(order.getHotelId());
			System.out.println(hotel);
			
			if (hotel.getPeopleInRoom() < order.getNumPeople()
					|| !isHotelAvailable(order.getHotelId(), order.getStartDate())) 
				return null;
			
				
			order.setTotalPrice(order.getNumNights() * hotel.getPricePerNight());
			InsertOneResult result = ordersCollection.insertOne(order);
			Bson updateHotel = addToSet("orders", order.getId());
			hotelsCollection.updateOne(Filters.eq("_id", hotel.getId()), updateHotel);

			Bson updateCustomer = addToSet("orders", order.getId());
			customersCollection.updateOne(Filters.eq("_id", order.getCustomerId()), updateCustomer);

			return result;
		}
		
		//5
		public DeleteResult deleteOrder(Order order) {
			Hotel hotel = getHotelById(order.getHotelId());
			Bson filter = eq("_id", order.getId());
			
			Bson updateHotel = pull("orders", order.getId());
			hotelsCollection.updateOne(Filters.eq("_id", hotel.getId()), updateHotel);

			Bson updateCustomer = pull("orders", order.getId());
			customersCollection.updateOne(Filters.eq("_id", order.getCustomerId()), updateCustomer);
			
            DeleteResult result = ordersCollection.deleteOne(filter);
            return result;
		}
	
		//6
		public void sortTotalIncom() {
			MongoCollection<Document> orders = DB.getCollection("orders");
			Bson pipeline = lookup("hotels","hotel_id","_id", "hotel");
			Bson group = group("$hotel._id", sum("total_income", "$total_price"));
			Bson sort = sort(Sorts.descending("total_income"));
			Bson project = project(Projections.fields(Projections.excludeId(),Projections.include("total_income"),Projections.computed("name", "$hotel.name")));
			List<Document> joined = orders.aggregate(Arrays.asList(pipeline,group,sort)).into(new ArrayList<>());
			joined.forEach(printDocuments());
			
		}
		
		
		private static Consumer<Document> printDocuments() {
			return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
		}
		
		//7
		public void  totalPricesAllOrders() {
			MongoCollection<Document> docOrders = DB.getCollection("orders");
			Bson group = group(null, sum("total_prices_all_orders", "$total_price"));
			Bson project = project(Projections.fields(Projections.excludeId()));
			List<Document> joined = docOrders.aggregate(Arrays.asList(group, project)).into(new ArrayList<>());
			joined.forEach(printDocuments());
		}
	
	
}