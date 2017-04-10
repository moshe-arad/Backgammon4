package org.moshe.arad.controllers;

import javax.validation.Valid;

import org.moshe.arad.entities.BackgammonUser;
import org.moshe.arad.services.HomeService;
import org.moshe.arad.validators.BackgammonUserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UsersController {
	
	private final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private HomeService homeService;
	
//	@RequestMapping(value = "", method = RequestMethod.GET)
//	public ResponseEntity<BasicUser> isUserAuthenticated(Principal user){
//		BasicUser result = new BasicUser();
//		
//		if(user != null){
//			logger.info("User how made this request is currently logged in.");
//			result.setUserName(homeService.getUserNameOfLoggedUser());
//			HttpHeaders header = new HttpHeaders();
//			header.add("Content-Type", "text/html");
//			return new ResponseEntity<BasicUser>(result, header, HttpStatus.OK);
//		}
//		else{
//			logger.info("User how made this request is not logged in.");
//			return new ResponseEntity<BasicUser>(HttpStatus.NO_CONTENT);
//		}
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> createNewUser(@Valid @RequestBody BackgammonUser backgammonUser, Errors errors){
		if(errors.hasErrors()){
			logger.info("Some errors occured while trying to bind backgammon user");
			logger.info("There are " + errors.getErrorCount() + " errors.");
			
			for(FieldError error:errors.getFieldErrors()){
				logger.warn("Field name:  " + error.getField());
				logger.warn(error.getDefaultMessage());
			}
			
			if(!BackgammonUserValidator.acceptableErrors(errors)){
				logger.info("Routing for home page.");
				HttpHeaders header = new HttpHeaders();
				header.add("Content-Type", "application/json");
				return new ResponseEntity<String>(header, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		logger.info("The GameUser bind result: " + backgammonUser);
		
		return null;
		
//		try{
//			if(homeService.registerNewUser(gameUser)){
//				String code = oauth2RestService.getAuthorizationCode(gameUser);
//				
//				if(!StringUtils.isEmpty(code)){
//					JsonAccessToken token = oauth2RestService.getAccessToken(code, gameUser);	
//					JsonBasicUser jsonBasicUser = usersDataRestService.findBasicUser(gameUser);
//				
//					if(jsonBasicUser != null && usersDataRestService.saveAccessToken(jsonBasicUser, token)){
//							HttpHeaders header = new HttpHeaders();
//							header.add("Content-Type", "application/json");
//							ObjectMapper mapper = new ObjectMapper();
//							BasicUser basicUser = new BasicUser(gameUser.getBasicUser().getUserName(), 
//									gameUser.getBasicUser().getPassword(), gameUser.getBasicUser().getEnabled());
//							
//							return new ResponseEntity<String>(mapper.writeValueAsString(basicUser), header, HttpStatus.CREATED);
//					}	
//				}
//			}
//			
//			logger.info("Failed to add and create new user.");
//			HttpHeaders header = new HttpHeaders();
//			header.add("Content-Type", "application/json");
//			return new ResponseEntity<String>(header, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//		catch(Exception ex){
//			logger.info("User register failed.");
//			logger.info("Routing for home page.");
//			logger.error(ex.getMessage());
//			logger.error(ex.toString());
//			HttpHeaders header = new HttpHeaders();
//			header.add("Content-Type", "application/json");
//			return new ResponseEntity<String>(header, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	@RequestMapping(value = "/user_name/{userName}", method = RequestMethod.GET)
	public ResponseEntity<String> isUserNameAvailable(@PathVariable String userName){
		try{
			logger.info("User name bind result: " + userName);
			if(homeService.isUserNameAvailable(userName)){
				logger.info("User name available for registeration.");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html");
				return new ResponseEntity<String>("", headers,HttpStatus.OK);
				
			}
			else {
				logger.info("User name not available can't register.");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html");
				return new ResponseEntity<String>("User name is not available.", headers,HttpStatus.OK);
			}
		}
		catch (Exception ex) {
			logger.error(ex.getMessage());
			logger.error(ex.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html");
			return new ResponseEntity<String>("Ajax call encountred a server error.", headers,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/email/{email}/", method = RequestMethod.GET)
	public ResponseEntity<String> isUserEmailAvailable(@PathVariable String email){
		try{
			logger.info("Email bind result: " + email);
			if(homeService.isEmailAvailable(email)){
				logger.info("Email available for registeration.");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html");
				return new ResponseEntity<>("", headers, HttpStatus.OK);
			}
			else{
				logger.info("Email not available can't register.");
				HttpHeaders headers = new HttpHeaders();
				headers.add("Content-Type", "text/html");
				return new ResponseEntity<>("Email is not available.", headers, HttpStatus.OK);
			}
		}
		catch(Exception ex){
			logger.error(ex.getMessage());
			logger.error(ex.toString());
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "text/html");
			return new ResponseEntity<String>("Ajax call encountred a server error.", headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		binder.addValidators(new BackgammonUserValidator());
	}
}