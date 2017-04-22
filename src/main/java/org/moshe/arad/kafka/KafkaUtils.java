package org.moshe.arad.kafka;

public class KafkaUtils {

	public static final String SERVERS = "192.168.1.4:9092,192.168.1.4:9093,192.168.1.4:9094";
	public static final String CREATE_NEW_USER_COMMAND_GROUP = "CreateNewUserCommandGroup";
	public static final String KEY_STRING_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
	public static final String KEY_STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer";
	public static final String CREATE_NEW_USER_COMMAND_DESERIALIZER = "org.moshe.arad.kafka.deserializers.CreateNewUserCommandDeserializer";
	public static final String NEW_USER_CREATED_EVENT_SERIALIZER = "org.moshe.arad.kafka.serializers.NewUserCreatedEventSerializer";
	public static final String CREATE_NEW_USER_COMMAND_TOPIC = "Create-New-User-Command";
	public static final String CHECK_USER_EMAIL_AVAILABILITY_COMMANDS_TOPIC = "Check-User-Email-Availability-Command";
	public static final String CHECK_USER_NAME_AVAILABILITY_COMMAND_TOPIC = "Check-User-Name-Availability-Command";
	public static final String CREATE_NEW_USER_COMMAND_SERIALIZER = "org.moshe.arad.kafka.serializers.CreateNewUserCommandSerializer";
	public static final String CHECK_USER_NAME_AVAILABILITY_COMMAND_SERIALIZER = "org.moshe.arad.kafka.serializers.CheckUserNameAvailabilityCommandSerializer";
	public static final String USER_NAME_AVAILABILITY_CHECKED_EVENT_TOPIC = "User-Name-Availability-Checked-Event";
	public static final String USER_NAME_AVAILABILITY_CHECKED_EVENT_GROUP = "UserNameAvailabilityCheckedEventGroup";
	public static final Object USER_NAME_AVAILABILITY_CHECKED_EVENT_DESERIALIZER = "org.moshe.arad.kafka.deserializers.UserNameAvailabilityCheckedEventDeserializer";
}