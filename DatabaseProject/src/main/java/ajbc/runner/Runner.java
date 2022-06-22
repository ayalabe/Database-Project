package ajbc.runner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDate;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import ajbc.connect.MyConnectionString;
import ajbc.crud.PojoMapping;
import ajbc.models.Hotel;
import ajbc.models.Order;
import ajbc.utils.SeedDB;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// prepare codec registry
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);

		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(new ConnectionString(MyConnectionString.uri()))
				// add codec registry
				.codecRegistry(codecRegistry).serverApi(ServerApi.builder().version(ServerApiVersion.V1).build())
				.build();
		
		try (MongoClient mongoClient = MongoClients.create(settings)) {
			MongoDatabase DB = mongoClient.getDatabase("booking_reservations");
//			SeedDB.createCustomersCollection(DB);
//			SeedDB.createHotelsCollection(DB);
//			SeedDB.createOrdersCollection(DB);
			
			
			PojoMapping dao = new PojoMapping(DB);
			//	Q1
//			List<Order> list =  dao.getOrdersByCustomerId(new ObjectId("62b2b6e8b26fba322298fc55"));
//			list.forEach(System.out::println);
			
			//Q2
//			List<Hotel> hotels = dao.getHotelsByCity("jerusalem");
//			hotels.forEach(System.out::println);
			
			//Q4
//			Order order = new Order(new ObjectId("62b2fc65d7290b63e429eae4"), new ObjectId("62b2fc62d7290b63e429eae0"), LocalDate.now(), LocalDate.now().plusDays(2), 3, 2);
//			InsertOneResult orderReturn = dao.createOrder(order);
			
//			Hotel order = dao.getHotelById(new ObjectId("62b2b6ecb26fba322298fc56"));
//			System.out.println(orderReturn.wasAcknowledged());
			
			//Q5
//			Order order = new Order(new ObjectId("62b2fc91e9fd9b106a79b88d"), new ObjectId("62b2fc65d7290b63e429eae4"), new ObjectId("62b2fc62d7290b63e429eae0"), LocalDate.now(), LocalDate.now().plusDays(2), 3, 2);
//			dao.deleteOrder(order);
			
			//Q6
			dao.sortTotalIncom();
			//Q7
//			dao.totalPricesAllOrders();
		}
		
		
		
	}
	
}