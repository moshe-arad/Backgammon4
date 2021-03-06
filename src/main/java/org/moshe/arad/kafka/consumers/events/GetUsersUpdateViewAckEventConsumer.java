package org.moshe.arad.kafka.consumers.events;

import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.moshe.arad.kafka.consumers.ISimpleConsumer;
import org.moshe.arad.kafka.events.GetUsersUpdateViewAckEvent;
import org.moshe.arad.kafka.events.NewGameRoomOpenedEventAck;
import org.moshe.arad.kafka.events.UserEmailAckEvent;
import org.moshe.arad.services.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@Scope("prototype")
public class GetUsersUpdateViewAckEventConsumer extends SimpleEventsConsumer implements ISimpleConsumer {

	@Autowired
	private HomeService homeService;
	
	private Logger logger = LoggerFactory.getLogger(GetUsersUpdateViewAckEventConsumer.class);
	
	public GetUsersUpdateViewAckEventConsumer() {
	}

	@Override
	public void consumerOperations(ConsumerRecord<String,String> record) {
		GetUsersUpdateViewAckEvent getUsersUpdateViewAckEvent = convertJsonBlobIntoEvent(record.value());
		Object locker = homeService.getEventsPoll().getGetUsersUpdateViewLockers().get(getUsersUpdateViewAckEvent.getUuid().toString());
		
		if(locker!= null){
			synchronized (locker) {
				logger.info("Get Users Update View Ack Event record recieved, " + record.value());
				logger.info("passing event to home service queue...");
				homeService.getEventsPoll().addEventToPool(getUsersUpdateViewAckEvent);
				logger.info("New Game Room Opened Event Ack record passed to home service...");
				locker.notifyAll();
			}
		}
						
	}
	
	private GetUsersUpdateViewAckEvent convertJsonBlobIntoEvent(String JsonBlob){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(JsonBlob, GetUsersUpdateViewAckEvent.class);
		} catch (IOException e) {
			logger.error("Falied to convert Json blob into Event...");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}




	