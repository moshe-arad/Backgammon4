package org.moshe.arad.initializers;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.moshe.arad.kafka.KafkaUtils;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.consumers.config.GetAllGameRoomsEventAckConfig;
import org.moshe.arad.kafka.consumers.config.GetGameUpdateViewAckEventConfig;
import org.moshe.arad.kafka.consumers.config.GetLobbyUpdateViewAckEventConfig;
import org.moshe.arad.kafka.consumers.config.GetUsersUpdateViewAckEventConfig;
import org.moshe.arad.kafka.consumers.config.NewGameRoomOpenedEventAckConfig;
import org.moshe.arad.kafka.consumers.config.SimpleConsumerConfig;
import org.moshe.arad.kafka.consumers.config.UserAddedAsWatcherEventAckConfig;
import org.moshe.arad.kafka.consumers.events.GetAllGameRoomsEventAckConsumer;
import org.moshe.arad.kafka.consumers.events.GetGameUpdateViewAckEventConsumer;
import org.moshe.arad.kafka.consumers.events.GetLobbyUpdateViewAckEventConsumer;
import org.moshe.arad.kafka.consumers.events.GetUsersUpdateViewAckEventConsumer;
import org.moshe.arad.kafka.consumers.events.NewGameRoomOpenedEventAckConsumer;
import org.moshe.arad.kafka.consumers.events.UserAddedAsWatcherEventAckConsumer;
import org.moshe.arad.kafka.consumers.events.UserEmailAckEventConsumer;
import org.moshe.arad.kafka.consumers.events.UserNameAckEventConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppInit implements IAppInitializer, ApplicationContextAware	 {
	
	private UserNameAckEventConsumer userNameAvailabilityCheckedEventConsumer;
	
	@Resource(name = "UserNameAvailabilityCheckedEventConfig")
	private SimpleConsumerConfig userNameAvailabilityCheckedEventConfig;
	
	private UserEmailAckEventConsumer userEmailAvailabilityCheckedEventConsumer;
	
	@Resource(name = "UserEmailAvailabilityCheckedEventConfig")
	private SimpleConsumerConfig userEamilAvailabilityCheckedEventConfig;
	
	private NewGameRoomOpenedEventAckConsumer newGameRoomOpenedEventAckConsumer;
	
	@Autowired
	private NewGameRoomOpenedEventAckConfig newGameRoomOpenedEventAckConfig;
	
	private UserAddedAsWatcherEventAckConsumer userAddedAsWatcherEventAckConsumer;
	
	@Autowired
	private UserAddedAsWatcherEventAckConfig userAddedAsWatcherEventAckConfig;
	
	private GetAllGameRoomsEventAckConsumer getAllGameRoomsEventAckConsumer;
	
	@Autowired
	private GetAllGameRoomsEventAckConfig getAllGameRoomsEventAckConfig;
	
	private GetLobbyUpdateViewAckEventConsumer getLobbyUpdateViewAckEventConsumer;
	
	@Autowired
	private GetLobbyUpdateViewAckEventConfig getLobbyUpdateViewAckEventConfig;
	
	private GetUsersUpdateViewAckEventConsumer getUsersUpdateViewAckEventConsumer;
	
	@Autowired
	private GetUsersUpdateViewAckEventConfig getUsersUpdateViewAckEventConfig;
	
	private GetGameUpdateViewAckEventConsumer getGameUpdateViewAckEventConsumer;
	
	@Autowired
	private GetGameUpdateViewAckEventConfig getGameUpdateViewAckEventConfig;
	
	private ExecutorService executor = Executors.newFixedThreadPool(6);
	
	private Logger logger = LoggerFactory.getLogger(AppInit.class);
	
	private ApplicationContext context;
	
	public static final int NUM_CONSUMERS = 3;
	
	public AppInit() {
		
	}

	@Override
	public void initKafkaCommandsConsumers() {
		
	}

	@Override
	public void initKafkaEventsConsumers() {
		for(int i=0; i<NUM_CONSUMERS; i++){
			userNameAvailabilityCheckedEventConsumer = context.getBean(UserNameAckEventConsumer.class);
			userEmailAvailabilityCheckedEventConsumer = context.getBean(UserEmailAckEventConsumer.class);
			newGameRoomOpenedEventAckConsumer = context.getBean(NewGameRoomOpenedEventAckConsumer.class);
			userAddedAsWatcherEventAckConsumer = context.getBean(UserAddedAsWatcherEventAckConsumer.class);
			getAllGameRoomsEventAckConsumer = context.getBean(GetAllGameRoomsEventAckConsumer.class);
			getLobbyUpdateViewAckEventConsumer = context.getBean(GetLobbyUpdateViewAckEventConsumer.class);
			getUsersUpdateViewAckEventConsumer = context.getBean(GetUsersUpdateViewAckEventConsumer.class);
			getGameUpdateViewAckEventConsumer = context.getBean(GetGameUpdateViewAckEventConsumer.class);
			
			logger.info("Initializing user name availability checked event consumer...");		
			initSingleConsumer(userNameAvailabilityCheckedEventConsumer, KafkaUtils.USER_NAME_AVAILABILITY_CHECKED_EVENT_TOPIC, userNameAvailabilityCheckedEventConfig);		
			logger.info("Initialize user name availability checked event consumer, completed...");
			
			logger.info("Initializing user email avialability checked event consumer...");		
			initSingleConsumer(userEmailAvailabilityCheckedEventConsumer, KafkaUtils.EMAIL_AVAILABILITY_CHECKED_EVENT_TOPIC, userEamilAvailabilityCheckedEventConfig);				
			logger.info("Initialize user email avialability checked event consumer, completed...");
						
			initSingleConsumer(newGameRoomOpenedEventAckConsumer, KafkaUtils.NEW_GAME_ROOM_OPENED_EVENT_ACK_TOPIC, newGameRoomOpenedEventAckConfig);
			
			initSingleConsumer(userAddedAsWatcherEventAckConsumer, KafkaUtils.USER_ADDED_AS_WATCHER_EVENT_ACK_TOPIC, userAddedAsWatcherEventAckConfig);
						
			initSingleConsumer(getAllGameRoomsEventAckConsumer, KafkaUtils.GET_ALL_GAME_ROOMS_EVENT_ACK_TOPIC, getAllGameRoomsEventAckConfig);
			
			initSingleConsumer(getLobbyUpdateViewAckEventConsumer, KafkaUtils.GET_LOBBY_UPDATE_VIEW_ACK_EVENT_TOPIC, getLobbyUpdateViewAckEventConfig);
			
			initSingleConsumer(getUsersUpdateViewAckEventConsumer, KafkaUtils.GET_USERS_UPDATE_VIEW_ACK_EVENT_TOPIC, getUsersUpdateViewAckEventConfig);
			
			initSingleConsumer(getGameUpdateViewAckEventConsumer, KafkaUtils.GET_GAME_UPDATE_VIEW_ACK_EVENT_TOPIC, getGameUpdateViewAckEventConfig);
			
			executeProducersAndConsumers(Arrays.asList(userEmailAvailabilityCheckedEventConsumer,
					userNameAvailabilityCheckedEventConsumer, 
					newGameRoomOpenedEventAckConsumer,
					userAddedAsWatcherEventAckConsumer,
					getAllGameRoomsEventAckConsumer,
					getLobbyUpdateViewAckEventConsumer,
					getUsersUpdateViewAckEventConsumer,
					getGameUpdateViewAckEventConsumer));
		}
	}	

	@Override
	public void initKafkaCommandsProducers() {
		
	}

	@Override
	public void initKafkaEventsProducers() {
		
	}
	
	@Override
	public void engineShutdown() {
		logger.info("about to do shutdown.");
		shutdownSingleConsumer(userNameAvailabilityCheckedEventConsumer);
		shutdownSingleConsumer(userEmailAvailabilityCheckedEventConsumer);
		selfShutdown();
		logger.info("shutdown compeleted.");
		
	}
	
	private void initSingleConsumer(ISimpleConsumer consumer, String topic, SimpleConsumerConfig consumerConfig) {
		consumer.setTopic(topic);
		consumer.setSimpleConsumerConfig(consumerConfig);
		consumer.initConsumer();	
	}
	
	private void shutdownSingleConsumer(ISimpleConsumer consumer) {
		consumer.setRunning(false);
		consumer.getScheduledExecutor().shutdown();
		consumer.closeConsumer();
	}
	
	private void selfShutdown(){
		this.executor.shutdown();
	}
	
	private void executeProducersAndConsumers(List<Runnable> jobs){
		for(Runnable job:jobs)
			executor.execute(job);
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
}
