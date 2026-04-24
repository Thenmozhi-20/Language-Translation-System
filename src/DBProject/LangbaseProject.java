package DBProject;

import javafx.application.Application ;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene; 
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class LangbaseProject extends Application{
	public void start(Stage primaryStage)
	{
		
		ImageView welcomeView = getIcon("/images/internet.png");
		Label welcome = new Label("Welcome to LangBase!");
		welcome.getStyleClass().add("welcome");
		
		HBox hbox = new HBox(13,welcomeView,welcome);
		hbox.setAlignment(Pos.CENTER);
		hbox.setPadding(new Insets(10,0,0,0));  // set the space top in the scene
		
		ImageView userMail = getIcon("/images/mail.png");
		Label emailLabel = new Label("Email ");
		TextField mailField = new TextField();
		mailField.setMinSize(15, 38);
		mailField.setPromptText("Enter your Email");
		
		HBox hbox1 = new HBox(15,userMail,emailLabel);
		hbox1.setAlignment(Pos.CENTER);
		emailLabel.getStyleClass().add("email");
		VBox vbox1 = new VBox(15,hbox1,mailField);
		
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setPadding(new Insets(0, 90, 0, 90));  // spaces created top,right,bottom,left
		
		ImageView passwordImage = getIcon("/images/security.png");
		Label userPassword = new Label("Password");
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Enter Password");
		passwordField.setPrefHeight(38);
		passwordField.getStyleClass().add("pass-field");
		
		TextField textField =new TextField();
		textField.setManaged(false);
		textField.setVisible(false);
		textField.setPrefHeight(38);
		textField.setPromptText("Enter Password");
		
		AnchorPane pane = eyeSetting(passwordField,textField);
		
		HBox hbox2 = new HBox(15,passwordImage,userPassword);
		hbox2.setAlignment(Pos.CENTER);
		userPassword.getStyleClass().add("password-field");
		
		VBox vbox2 = new VBox(20,hbox2,pane);
		
		vbox2.setAlignment(Pos.CENTER);
		vbox2.setPadding(new Insets(0,90,0,90));  // spaces created in top,right,bottom,left
		
		ImageView loginView = getIcon("/images/user.png");
		Button loginButton = new Button("Login");
		HBox login = new HBox(8,loginView,loginButton);
		login.setAlignment(Pos.CENTER_LEFT);
		
		ImageView signUp = getIcon("/images/add-user.png");
		Button signUpButton = new Button("Sign Up");
		HBox sign_up = new HBox(8,signUp,signUpButton);
		sign_up.setAlignment(Pos.CENTER);
		
		ImageView forgotPd = getIcon("/images/security.png");
		Button forgotButton = new Button("Forgot Password");
		HBox forgotPassword = new HBox(8,forgotPd,forgotButton);
		forgotPassword.setAlignment(Pos.CENTER_RIGHT);
		
		loginButton.getStyleClass().add("login");
		signUpButton.getStyleClass().add("signUp");
		forgotButton.getStyleClass().add("forgot_password");
		
		loginButton.setOnAction(event -> {
			String userEmail = mailField.getText();
			String usrPassword = passwordField.getText();
			
			if(userEmail.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please enter your Email ID!");
				return ;
			}
			
			if(EmailValidation(userEmail)==false)
			{
				showAlert(Alert.AlertType.ERROR,"Error","Invalid Email Format");
			}
			
			// goes to home page
			checkSignUpOrNot(primaryStage,userEmail,usrPassword);
			
		});
		
		
		signUpButton.setOnAction(e->{
			addNewUser(primaryStage);
		});
		
		
		forgotButton.setOnAction(e->{
			forgotPassword(primaryStage);
		});
		
		HBox hbox3 = new HBox(100,login,sign_up,forgotPassword);
		hbox3.setAlignment(Pos.CENTER);
		hbox3.setPadding(new Insets(45,0,0,0));
		
		VBox vbox3 = new VBox(400,vbox2,hbox3);
		
		VBox layout = new VBox(10,hbox,vbox1,vbox2,vbox3);
		VBox.setMargin(vbox1, new Insets(40, 0, 0, 0));  // top, right, bottom, left
		
		layout.getStyleClass().add("scene");
		
		Scene scene = new Scene(layout,700,500);
		
        String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Langbase Project");
		primaryStage.show();
		
		primaryStage.centerOnScreen();
	}
	
	private void checkSignUpOrNot(Stage primaryStage,String userEmail,String userPassword)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "select userName,userPassword from userInformation where Email=?";
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,userEmail);
			
			ResultSet rst = pst.executeQuery();
			
			if(rst.next())
			{
				String name = rst.getString("userName");
				String password = rst.getString("userPassword");
				
				if(password.equals(userPassword))
				{
					ShowWelcomeScreen(primaryStage,name,userEmail);
				}
				else
				{
					showAlert(Alert.AlertType.ERROR,"Error","Incorrect Password!");
					return ;
				}
			}
			else
			{
				showAlert(Alert.AlertType.ERROR,"Error","You have not signed up. Please Sign up first!");
				
				return ;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void addNewUser(Stage primaryStage)
	{
		ImageView userView = smallSizeGetIcon("/images/user.png");
		ImageView mailView = smallSizeGetIcon("/images/mail.png");
		ImageView passwordView = smallSizeGetIcon("/images/security.png");
		ImageView confirmPasswordView = smallSizeGetIcon("/images/security.png");
		ImageView langView = smallSizeGetIcon("/images/internet.png");
		
		Label nameLabel = new Label("Enter your name ");
		Label mailLabel = new Label("Enter your Email ");
		Label passwordLabel = new Label("Enter Password ");
		Label confirmPasswordLabel = new Label("Enter Confirm Password ");
		Label language = new Label("Select Preferred Language ");
		
		HBox hbox1 = new HBox(15,userView,nameLabel);
		HBox hbox2 = new HBox(15,mailView,mailLabel);
		HBox hbox3 = new HBox(15,passwordView,passwordLabel);
		HBox hbox4 = new HBox(15,confirmPasswordView,confirmPasswordLabel);
		HBox hbox5 = new HBox(15,langView,language);
		
		TextField nameField = new TextField();
		TextField mailField = new TextField();
		PasswordField passwordField = new PasswordField();
		PasswordField cPasswordField = new PasswordField();
		ComboBox<String> languageCombo = new ComboBox<>();
		
		nameField.setPromptText("Enter Name");
		mailField.setPromptText("Enter Email");
		passwordField.setPromptText("Enter Password");
		cPasswordField.setPromptText("Enter Confirm Password");
		
		passwordField.setPrefHeight(38);
		TextField textField =new TextField();
		textField.setManaged(false);
		textField.setVisible(false);
		textField.setPrefHeight(38);
		textField.setPromptText("Enter Password");
		AnchorPane pane = eyeSetting(passwordField,textField);
		
		cPasswordField.setPrefHeight(38);
		TextField textField1 =new TextField();
		textField1.setManaged(false);
		textField1.setVisible(false);
		textField1.setPrefHeight(38);
		textField1.setPromptText("Enter Password");
		AnchorPane pane1 = eyeSetting(cPasswordField,textField1);
		
		
		passwordField.getStyleClass().add("paassword");
		cPasswordField.getStyleClass().add("ConfPassword");
		
		languageCombo.getItems().addAll(
				"English","Tamil","Hindi","French","German","Bengali","Spanish",
				"Japanese","Chinese","Telugu","Kannada"
				);
		
		
		nameLabel.getStyleClass().add("name");
		mailLabel.getStyleClass().add("mailId");
		passwordLabel.getStyleClass().add("password");
		confirmPasswordLabel.getStyleClass().add("password");
		language.getStyleClass().add("language");
		
		Label welcome = new Label("Welcome to Langbase!");
		welcome.getStyleClass().add("welcome_Label");
		welcome.setAlignment(Pos.CENTER);
		
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);
		
		grid.add(hbox1, 0, 0);
		grid.add(nameField, 1, 0);
		
		grid.add(hbox2, 0, 1);
		grid.add(mailField, 1, 1);
		
		grid.add(hbox3, 0, 2);
		grid.add(pane, 1, 2);
		
		grid.add(hbox4, 0, 3);
		grid.add(pane1, 1, 3);
		
		grid.add(hbox5, 0, 4);
		grid.add(languageCombo, 1, 4);
		
		Button signup = new Button("Sign Up");
		signup.setAlignment(Pos.CENTER);
		signup.getStyleClass().add("signUp");
		
		signup.setOnAction(e->{
			String name = nameField.getText();
			String mail = mailField.getText();
			String password = passwordField.getText();
			String confirmPassword = cPasswordField.getText();
			String lang = languageCombo.getValue();
			
			if(name.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please enter your Name!");
				return ;
			}
			
			if(mail.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please enter your Mail ID!");
				return ;
			}
			
			if(password.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please enter your Password!");
				return ;
			}
			
			if(confirmPassword.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please enter your Confirm Password!");
				return ;
			}
			
			if(!password.equals(confirmPassword))
			{
				showAlert(Alert.AlertType.ERROR,"Error","The Password and Confirm Password not match. please Try Again!");
			}
			
			if(lang.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"Warning","Please choose your preferred Language");
				return ;
			}
			
			if(NameValidation(name)==false)
			{
				showAlert(Alert.AlertType.ERROR,"Error","Invalid Name! \nName should contain only Alphabets. ");
				return ;
			}
			
			if(EmailValidation(mail)==false)
			{
				showAlert(Alert.AlertType.ERROR,"Error","Invalid Email Format");
				return ;
			}
			
			
			boolean addedNewUser = SignUpUser(name,mail,password,confirmPassword,lang);
			if(addedNewUser)
			{
				ShowWelcomeScreen(primaryStage,name,mail);
				// to wish the user for use our app 
				MailSent(name,mail,lang);
			}
		});
		
		VBox layout = new VBox(20,welcome,grid,signup);
		layout.setAlignment(Pos.CENTER);
		layout.setPadding(new Insets(40));
		
		
		layout.getStyleClass().add("add_user");
		
		Scene scene = new Scene(layout,1270,650);
		
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("New User");
		primaryStage.show();		
		
		primaryStage.centerOnScreen();
	}
	
	private boolean SignUpUser(String name,String mail,String password,String confPassword,String lang)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "insert into userInformation(Email,userName,userPassword,confirmPassword,Lang) values (?,?,?,?,?) ";
			PreparedStatement pst = con.prepareStatement(query);
			
			pst.setString(1,mail);
			pst.setString(2,name);
			pst.setString(3,password);
			pst.setString(4,confPassword);
			pst.setString(5,lang);
			
			int rowAffected = pst.executeUpdate();
			
			if(rowAffected >0) return true ;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private void ShowWelcomeScreen(Stage primaryStage ,String userName, String userEmail)
	{
		ImageView welcomeView = getIcon("/images/house.png");
		
		ImageView userView = smallSizeGetIcon("/images/user.png");
		
		Label welcomeLabel = new Label("Welcome, "+userName+"!");
		welcomeLabel.getStyleClass().add("welcome_Label");
		
		Region spacer = new Region();
		HBox.setHgrow(spacer,Priority.ALWAYS);  // spacer
		
		// menu button for profile and layout
		MenuButton userMenu = new MenuButton();
		userMenu.setGraphic(userView);
		MenuItem viewProfile = new MenuItem("My Profile");
		MenuItem logout = new MenuItem("Logout");
		 
		
		userMenu.getItems().addAll(viewProfile,logout);
		
		logout.setOnAction(e->{
			primaryStage.close();
		});  // if a logout occurs then comes out
		
		viewProfile.setOnAction(e1->{
			viewUserDetail(primaryStage,userEmail);
		});
		
		
		HBox topBar = new HBox(20,welcomeView,welcomeLabel,spacer,userMenu);
		topBar.setPadding(new Insets(10));
		topBar.setAlignment(Pos.CENTER);
		topBar.setStyle("-fx-background-color: #f0f0f0;");
		
		BorderPane root = new BorderPane();
		root.setTop(topBar);
		
		root.getStyleClass().add("welcome-page");
		
		ImageView translationView = getBigIcon("/images/internet.png");
		ImageView historyView = getBigIcon("/images/history.png");
		ImageView feedbackView = getBigIcon("/images/fdback.png");
		
		Button translation = new Button("Translation");
		Button history = new Button("History");
		Button feedback = new Button("Feedback");
		
		HBox hbox1 = new HBox(17,translationView,translation); hbox1.setAlignment(Pos.CENTER);
		HBox hbox2 = new HBox(17,historyView,history);  hbox2.setAlignment(Pos.CENTER);
		HBox hbox3 = new HBox(17,feedbackView,feedback); hbox3.setAlignment(Pos.CENTER);
		
		translation.getStyleClass().add("translation");
		history.getStyleClass().add("history");
		feedback.getStyleClass().add("feedback");
		
		translation.setOnAction(e->{
			translate(primaryStage,userName,userEmail);
		});
		
		history.setOnAction(e->{
			HistoryTranslationDetails(primaryStage,userEmail);
		});
		
		feedback.setOnAction(e->{
			userFeedBack(primaryStage,userName,userEmail);
		});
		translation.setPrefSize(150, 70);
		history.setPrefSize(150, 70);
		feedback.setPrefSize(150, 70);
		// Arrange in HBox
		HBox view = new HBox(120,hbox1, hbox2,hbox3);
		
		view.setAlignment(Pos.CENTER);
		view.setPadding(new Insets(20));
		view.setPrefWidth(700);
		view.setPrefHeight(700);

		Label option = new Label("Start translating instantly or view your history!");
		option.setMaxWidth(Double.MAX_VALUE);
		option.setAlignment(Pos.CENTER);
        option.getStyleClass().add("options");
        
        VBox vbox = new VBox(10,option,view);
        vbox.setPadding(new Insets(80, 0, 0, 0)); // top, right, bottom, left
        vbox.setAlignment(Pos.CENTER);
		
		root.setCenter(vbox);
		
		Scene scene = new Scene(root,1270,650);
		
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome to Langbase");
		primaryStage.show();
		
		// this helps to display the scene in the center screen 
		primaryStage.centerOnScreen();
	}
	
	
	private void viewUserDetail(Stage primaryStage,String userEmail)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "select userName,Lang from userInformation where Email=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,userEmail);
			
			ResultSet rst = pst.executeQuery();
			
			if(rst.next())
			{
				String name = rst.getString("userName");
				String lang = rst.getString("Lang");
				
				
				viewProfile(primaryStage,name,userEmail,lang);
			}
			else
			{
				System.out.println("No user found with this email.");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	private void viewProfile(Stage primaryStage,String name,String email,String lang)
	{
		ImageView profilePic = getIcon("/images/user.png");
		profilePic.setFitHeight(150);
		profilePic.setFitWidth(150);
		
		Label nameLabel = new Label("Name: ");
		Label emailLabel = new Label("Email: ");
		Label langLabel = new Label("Preferred Language: ");
		
		Label nameValue = new Label(name);
		Label emailValue = new Label(email);
		Label langValue = new Label(lang);
		
		nameLabel.setFont(Font.font("Arial", FontWeight.MEDIUM, 22));
		emailLabel.setFont(Font.font("Arial",FontWeight.MEDIUM,22));
		langLabel.setFont(Font.font("Arial",FontWeight.MEDIUM,22));
		
		nameValue.setFont(Font.font("Arial",FontWeight.MEDIUM,22));
		emailValue.setFont(Font.font("Arial",FontWeight.MEDIUM,22));
		langValue.setFont(Font.font("Arial",FontWeight.MEDIUM,22));
		
		GridPane grid = new GridPane();
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setPadding(new Insets(20));
		grid.setAlignment(Pos.CENTER);
		
		grid.add(nameLabel, 0, 0);
		grid.add(nameValue, 1, 0);
		
		grid.add(emailLabel, 0, 1);
		grid.add(emailValue, 1, 1);
		
		grid.add(langLabel, 0, 2);
		grid.add(langValue, 1, 2);
		
		Button editProfile = new Button("Edit Profile");
		Button logout = new Button("Logout");
		Button backButton = new Button("Back");  // -> <-
		backButton.getStyleClass().add("backButton");
		
		logout.setOnAction(e->{
			primaryStage.close();
		});
		
		editProfile.setOnAction(e1->{
			editUserProfile(primaryStage,name,email,lang);
		});
		
		backButton.setOnAction(e->{
			ShowWelcomeScreen(primaryStage,name,email);
		});
		
		logout.getStyleClass().add("log-out");
		editProfile.getStyleClass().add("edit_prof");
		
		HBox buttonBox = new HBox(60, editProfile, logout);
		buttonBox.setAlignment(Pos.CENTER);
		
		VBox vbox = new VBox(20,profilePic,grid,buttonBox);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));
		
		VBox vbox1 = new VBox(40,vbox,backButton);
		VBox.setMargin(backButton, new Insets(20, 0, 10, 20));  // 20px from left
		
		Scene scene = new Scene(vbox1,1270,650);
		
		vbox.getStyleClass().add("view_profile");
		
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("View Profile");
		primaryStage.show();
		
		primaryStage.centerOnScreen();
		
	}
	
	private void editUserProfile(Stage primaryStage,String name,String email,String lang)
	{
		Label nameLabel = new Label("Name: ");
		Label mailLabel = new Label("Mail: ");
		Label langLabel = new Label("Preferred Language: ");
		
		TextField nameField = new TextField(name);
		TextField mailField = new TextField(email);
		ComboBox<String> langCombo = new ComboBox<>();
		langCombo.getItems().addAll("English","Tamil","Hindi","French","German","Bengali","Spanish",
				"Japanese","Chinese","Telugu","Kannada");
		langCombo.setValue(lang);
		
		nameField.setPromptText("Enter your Name");
		mailField.setPromptText("Enter your Email");
		langCombo.setPromptText("Enter your Preferred Language");
		
		nameField.setMinSize(12,28);
		mailField.setMinSize(12,28);
		langCombo.setMinSize(12,28);
		
		nameLabel.getStyleClass().add("name");
		mailLabel.getStyleClass().add("mailId");
		langLabel.getStyleClass().add("language");
		
		VBox vbox1 = new VBox(10,nameLabel,nameField); vbox1.setAlignment(Pos.CENTER);
		VBox vbox2 = new VBox(10,mailLabel,mailField); vbox2.setAlignment(Pos.CENTER);
		VBox vbox3 = new VBox(10,langLabel,langCombo); vbox3.setAlignment(Pos.CENTER);
		
		vbox1.setPadding(new Insets(0,180,0,180));  // top,right,bottom,left
		vbox2.setPadding(new Insets(0,180,0,180));
		vbox3.setPadding(new Insets(0,180,0,180));
		
	    Button updateProfile = new Button("Update Profile");
	    updateProfile.getStyleClass().add("update_profile");
	    updateProfile.setAlignment(Pos.CENTER);
	    
	    
	    Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");
	    backButton.setOnAction(e->{
	    	viewProfile(primaryStage,name,email,lang);
	    });
		
	    
	    updateProfile.setOnAction(e->{
	    	String updateName = nameField.getText();
	    	String updateMail = mailField.getText();
	    	String updateLang = langCombo.getValue();
	    	
	    	// update name
	    	if(!updateName.equals(name))
	    	{
	    		try(Connection con = DBConnection.getConnection())
	    		{
	    			String query = "update userInformation set userName=? where Email=?";
	    			PreparedStatement pst = con.prepareStatement(query);
	    			
	    			pst.setString(1,updateName);
	    			pst.setString(2,email);
	    			
	    			pst.executeUpdate();
	    		}
	    		catch(SQLException e1)
	    		{
	    			e1.printStackTrace();
	    		}
	   		}
	    	
	    	// update language
	    	if(!updateLang.equals(lang))
	    	{
	    		try(Connection con = DBConnection.getConnection())
	    		{
	    			String query = "update userInformation set Lang=? where Email=?";
	    			PreparedStatement pst = con.prepareStatement(query);
	    			
	    			pst.setString(1,updateLang);
	    			pst.setString(2,email);
	    			
	    			pst.executeUpdate();
	    		}
	    		catch(SQLException e2)
	    		{
	    			e2.printStackTrace();
	    		}
	    	}
	    	
	    	// update email
	    	if(!updateMail.equals(email))
	    	{
	    		try(Connection con = DBConnection.getConnection())
	    		{
	    			String query = "update userInformation set Email=? where Email=?";
	    			PreparedStatement pst = con.prepareStatement(query);
	    			
	    			pst.setString(1,updateMail);
	    			pst.setString(2,email);
	    			
	    			pst.executeUpdate();
	    		}
	    		catch(SQLException e3)
	    		{
	    			e3.printStackTrace();
	    		}
	    	}
	    	
	    	
	    	if(updateName.equals(name) && updateMail.equals(email) && updateLang.equals(lang))
	    	{
	    		showAlert(Alert.AlertType.INFORMATION,"No Changes Deducted","No changes were made to your profile.");
	    	}
	    	else 
	    	{
	    		showAlert(Alert.AlertType.INFORMATION,"Update Successful","Your profile has been updated successfully");
	    	}
	    });
	    
	    BorderPane pane = new BorderPane();
	    
	    VBox vbox = new VBox(30,vbox1,vbox2,vbox3,updateProfile);
	    vbox.setAlignment(Pos.CENTER); // to align labels from the same X position (left)
	    
	    HBox hbox = new HBox(backButton);
	    hbox.setAlignment(Pos.CENTER_LEFT);
	    hbox.setPadding(new Insets(10,10,30,20));
	    
	    pane.setCenter(vbox);
	    pane.setBottom(hbox);
		
	    pane.getStyleClass().add("edit_profile");
	    
		Scene scene = new Scene(pane,580,480);
		
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Edit Profile");
		primaryStage.show();
		
		// this helps to display the scene in the screen display
		primaryStage.centerOnScreen();
	}
	
	private void translate (Stage primaryStage,String name,String email) {
		
		ImageView welcomeView = getIcon("/images/internet.png");
		Label welcome = new Label("Select your languages and let's get those words moving!");
		welcome.getStyleClass().add("welcome");
		
		HBox hbox = new HBox(13,welcomeView,welcome);
		hbox.setAlignment(Pos.TOP_CENTER);
		hbox.setPadding(new Insets(10,0,0,0));  // set the space top in the scene
		
		Label textLabel = new Label("Enter Text : ");
		Label sourceLang = new Label("source Language");
		Label destinLang = new Label("Destination Language");
		Label resultLabel = new Label("Result Text :");
		
		TextField textField = new TextField();
		textField.setPrefHeight(38);
		textField.setPrefWidth(400);
		textField.setPromptText("Enter Text");
		
		HBox hboxx = new HBox(15,textLabel,textField);
		hboxx.setAlignment(Pos.TOP_CENTER);
		hboxx.setPadding(new Insets(10,0,0,0));
		
		ComboBox<String> sourceCombo = new ComboBox<>(); 
		sourceCombo.getItems().addAll(
				"English","Tamil","Hindi","French","German","Bengali","Spanish",
				"Japanese","Chinese","Telugu","Kannada"
				);
		
		ComboBox<String> destinationCombo = new ComboBox<>(); 
		destinationCombo.getItems().addAll(
				"English","Tamil","Hindi","French","German","Bengali","Spanish",
				"Japanese","Chinese","Telugu","Kannada"
				);
		
		TextField resultField = new TextField();
		resultField.setPrefHeight(38);
		resultField.setPrefWidth(400);
		resultField.setPromptText("Your result Text Here!");
		resultField.setAlignment(Pos.CENTER);
		
		Button translate = new Button("Translate");
		translate.setAlignment(Pos.CENTER);
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");
		backButton.setOnAction(e->{
			ShowWelcomeScreen(primaryStage,name,email);
		});
		
		translate.setOnAction(e->{
			
			if(sourceCombo.getValue()==null)
			{
				showAlert(Alert.AlertType.WARNING,"translation","Select any one Source Language");
			}
			else if(destinationCombo.getValue()==null)
			{
				showAlert(Alert.AlertType.WARNING,"translation","Select any one Destination Language");
			}
			
			String text = textField.getText();
			if(text.isEmpty())
			{
				showAlert(Alert.AlertType.WARNING,"","");
			}
			
			String sourceLanguage = sourceCombo.getValue();
			String destinationLanguage = destinationCombo.getValue();
			
			String sourceLangCode = LanguageMap.getLanguages().get(sourceLanguage);
			String targetLangCode = LanguageMap.getLanguages().get(destinationLanguage);
			
			String translatedText = Translator.translateText(text, sourceLangCode,targetLangCode);
			
			int userId = getId(email);
			userDetails(userId,name,email,sourceLanguage,destinationLanguage,text,translatedText);
				
			resultField.setText(translatedText);
			
			if(!translationLanguages(email,sourceLanguage,destinationLanguage))
			{
				showAlert(Alert.AlertType.ERROR,"Translation","Error occurred while translating");
			}
			else
			{
				showAlert(Alert.AlertType.INFORMATION,"Translation","Language Converted Successfully!!");
			}
		});
		
		sourceLang.getStyleClass().add("source");
		destinLang.getStyleClass().add("destination");
		sourceCombo.getStyleClass().add("SourceLang");
		destinationCombo.getStyleClass().add("DestinationLang");
		translate.getStyleClass().add("translate");
		textLabel.getStyleClass().add("enterText");
		resultLabel.getStyleClass().add("resText");
		
		sourceCombo.setPrefWidth(200);
		destinationCombo.setPrefWidth(200);
		
		HBox hbox1 = new HBox(60,sourceLang,destinLang);
		hbox1.setAlignment(Pos.TOP_CENTER);
		hbox1.setPadding(new Insets(10,0,0,0));
		
		HBox hbox2 = new HBox(60,sourceCombo,destinationCombo);
		hbox2.setAlignment(Pos.CENTER);
		hbox2.setPadding(new Insets(10,0,0,0));
		
		VBox vbox1 = new VBox(80,hbox2,translate);
        vbox1.setAlignment(Pos.CENTER);
		
		VBox vbox2 = new VBox(30,hbox,hboxx,hbox1);
		vbox1.setAlignment(Pos.CENTER);
		
		BorderPane pane = new BorderPane();  // it helps to create back button in bottom
		
		VBox vbox3 = new VBox(30,vbox2,vbox1);
		vbox3.setAlignment(Pos.CENTER);
		
		HBox hbox3 = new HBox(backButton);
		hbox3.setAlignment(Pos.BOTTOM_LEFT);
	    hbox3.setPadding(new Insets(0,0,2,20));  // top,right,bottom,left
	    
	    HBox hbox4 = new HBox(15,resultLabel,resultField);
	    resultLabel.setStyle("-fx-padding: 5 0 0 0;");
	    hbox4.setAlignment(Pos.BASELINE_CENTER);
	    hbox4.setPadding(new Insets(10,10,30,20));

		pane.setCenter(vbox3);
		pane.setLeft(hbox3);
		pane.setBottom(hbox4);
	    
		pane.getStyleClass().add("LanguageTranslation");
		
		Scene scene = new Scene(pane,1270,650);
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Translate");
		primaryStage.show();
	}
	
	
	private boolean translationLanguages(String email,String sourceLanguage,String destinationLanguage)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "insert into Languages (Email,sourceLang,destinationLang) values (?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,email);
			pst.setString(2,sourceLanguage);
			pst.setString(3,destinationLanguage);
			
			int rowsAffected = pst.executeUpdate();
			
			if(rowsAffected > 0) return true;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private void userFeedBack(Stage primaryStage,String userName,String userEmail)
	{
		Label label = new Label("Enter your Feedback:");
		label.getStyleClass().add("enterText");
		TextArea feedbackArea = new TextArea();
		feedbackArea.setPrefRowCount(4);
		
		Button submitButton = new Button("Submit");
		submitButton.getStyleClass().add("translate");
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");
		
		backButton.setOnAction(e->{
			ShowWelcomeScreen(primaryStage,userName,userEmail);
		});
		
		submitButton.setOnAction(e->{
			saveFeedback(userEmail,feedbackArea.getText());
		});
		
		BorderPane pane = new BorderPane();
		
		VBox layout = new VBox(10);
		layout.setPadding(new Insets(20));
		layout.getChildren().addAll(label,feedbackArea,submitButton);
		
		pane.setCenter(layout);
		
		HBox hbox = new HBox(backButton);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
		pane.setBottom(hbox);
		hbox.setPadding(new Insets(0,0,2,20));
		pane.getStyleClass().add("edit_profile");
		
		Scene scene = new Scene(pane,500,400);
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.centerOnScreen();
	}

	private void saveFeedback(String userEmail,String feedBack)
	{
		
	}
	
	private void forgotPassword(Stage primaryStage)
	{
		ImageView userMail = getIcon("/images/mail.png");
		Label email = new Label("Email");
		TextField emailField = new TextField();
		
		email.getStyleClass().add("mailId");
		emailField.setPromptText("Enter your Email");
		
		HBox hbox = new HBox(20,userMail,email);
		hbox.setAlignment(Pos.CENTER);
		
		Button sendOtpBtn = new Button("Send OTP");
		sendOtpBtn.setAlignment(Pos.CENTER);
		sendOtpBtn.getStyleClass().add("OTP_Button");
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");
		backButton.setOnAction(e->{
			start(primaryStage);
		});
		
		sendOtpBtn.setOnAction(e->{
			String generatedOtp = generateOtp();
			sendMail(emailField.getText(),generatedOtp);
			showOtpWindow(primaryStage,emailField.getText(),generatedOtp);
		});
		
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox(25,hbox,emailField,sendOtpBtn);
		vbox.setAlignment(Pos.CENTER);
		
		HBox hbox1 = new HBox(backButton);
		hbox1.setAlignment(Pos.BOTTOM_LEFT);
	    hbox1.setPadding(new Insets(10,10,30,20));
	    
	    pane.setCenter(vbox);
	    pane.setBottom(hbox1);
		pane.getStyleClass().add("forgot_PassWord");

	    
		Scene scene = new Scene(pane,600,480);
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Recovery Password");
		primaryStage.show();
	}
	
	private String generateOtp()
	{
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		String strOtp = Integer.toString(otp);
		return strOtp;
	}
	
	private void sendMail(String toMail,String otp)
	{
		String subject = "Password Recovery OTP";
		String content = "Hi,\n\nYour OTP for password recovery is: "+ otp + "\n\nPlease dont't share it.\n\n\nThank you,\nThe LangBase Team";
		EmailSender.sendEmailtoRecoveryPassword(toMail,subject,content);
	}
	
	private void showOtpWindow(Stage primaryStage,String email,String otp)
	{
		Label otpLabel = new Label("Enter the OTP sent to your Email");
		TextField otpField = new TextField();
		otpField.setPromptText("OTP");
		otpLabel.getStyleClass().add("enter_otp");
		
		Button verifyBtn = new Button("Verify");
		verifyBtn.setAlignment(Pos.CENTER);
		verifyBtn.getStyleClass().add("verify_otp");
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");
		backButton.setOnAction(e->{
			forgotPassword(primaryStage);
		});
		
		verifyBtn.setOnAction(e->{
			if(otpField.getText().equals(otp))
			{
				showNewPasswordWindow(primaryStage,email,otp);
			}
			else
			{
				showAlert(Alert.AlertType.ERROR,"Recovery Password","Invalid OTP. Try again.");
			}
		});
		
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox(20,otpLabel,otpField,verifyBtn);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(70));
		
		HBox hbox = new HBox(backButton);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
	    hbox.setPadding(new Insets(10,10,30,20));
	    
	    pane.setCenter(vbox);
		pane.setBottom(hbox);
		
		pane.getStyleClass().add("otp_send");
		
		Scene scene = new Scene(pane,600,480);
		
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sending OTP");
		primaryStage.show();
	}
	
	private void showNewPasswordWindow(Stage primaryStage,String email,String otp)
	{
		Label passwordLabel = new Label("New Password");
		Label confirmPasswordLabel = new Label("Confirm New Password");
		
		ImageView passwordImage1 = getIcon("/images/security.png");
		ImageView passwordImage2 = getIcon("/images/security.png");

		
		HBox hbox1 = new HBox(15,passwordImage1,passwordLabel);
		HBox hbox2 = new HBox(15,passwordImage2,confirmPasswordLabel);
		hbox1.setAlignment(Pos.CENTER);
		hbox2.setAlignment(Pos.CENTER);
		
		PasswordField passwordField = new PasswordField();
		passwordField.setPromptText("Enter Password");
		passwordField.setPrefHeight(38);
		passwordField.getStyleClass().add("pass-field");
		
		TextField textField =new TextField();
		textField.setManaged(false);
		textField.setVisible(false);
		textField.setPrefHeight(38);
		textField.setPromptText("Enter Password");
		
		AnchorPane pane1 = eyeSetting(passwordField,textField);
		
		PasswordField cPasswordField = new PasswordField();
		cPasswordField.setPromptText("Enter Password");
		cPasswordField.setPrefHeight(38);
		cPasswordField.getStyleClass().add("pass-field");
		
		TextField textField1 =new TextField();
		textField1.setManaged(false);
		textField1.setVisible(false);
		textField1.setPrefHeight(38);
		textField1.setPromptText("Enter Password");
		
		AnchorPane pane2 = eyeSetting(cPasswordField,textField1);
		
		cPasswordField.setPromptText("confirm password");
		passwordLabel.getStyleClass().add("password");
		confirmPasswordLabel.getStyleClass().add("password");
		
		Button updateBtn = new Button("Update Password");
		updateBtn.setAlignment(Pos.CENTER);
		updateBtn.getStyleClass().add("update_password");
		
		Button backButton = new Button("Back");
		backButton.getStyleClass().add("backButton");  // moving back - one scene to another scene
		backButton.setOnAction(e->{
			showOtpWindow(primaryStage,email,otp);
		});
		
		updateBtn.setOnAction(e->{
			boolean success = UpdatePassword(email,passwordField.getText());
			if(success)
			{
				String subject = "Your Password has been updated";
				String content = "Hello,\n\nyour password was successfully updated.\nIf this wasn't you, please contact Support.\n\nThanks,\nThe LangBase Team";
				EmailSender.sendEmail(email, subject, content);
				
				showAlert(Alert.AlertType.INFORMATION,"Recovery Password","Password Updated Successfully");
			}
		});
		
		BorderPane pane = new BorderPane();
		
		VBox vbox = new VBox(20,hbox1,pane1,hbox2,pane2,updateBtn);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(20));
		
		HBox hbox = new HBox(backButton);
		hbox.setAlignment(Pos.BOTTOM_LEFT);
	    hbox.setPadding(new Insets(10,10,30,20));
	    
	    pane.setCenter(vbox);
	    pane.setBottom(hbox);
		pane.getStyleClass().add("update-password");

		Scene scene = new Scene(pane,600,480);
		String cssPath = getClass().getResource("/css/style.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Update Password");
		primaryStage.show();
	}
	
	private boolean UpdatePassword(String email,String newPassword)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "update userInformation set userPassword=?, confirmPassword=? where Email=?" ;
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,newPassword);
			pst.setString(2,newPassword);
			pst.setString(3,email);
			
			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	private void showAlert(Alert.AlertType type,String title , String message)
	{
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private boolean NameValidation(String name)
	{
		String userName = name.trim();
		
		for(char c : userName.toCharArray())
		{
			if(!((c>='a' && c<='z')||(c>='A' && c<='Z')))
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean EmailValidation(String email)
	{
		String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
		return email.matches(emailRegex);
	}
	
	private ImageView getIcon(String path)
	{
		Image iconImage = new Image(getClass().getResourceAsStream(path));
		ImageView iconView = new ImageView(iconImage);
		iconView.setFitHeight(49);
		iconView.setFitWidth(49);
		
		return iconView;
	}
	
	private ImageView smallSizeGetIcon(String path)
	{
		Image iconImage = new Image(getClass().getResourceAsStream(path));
		ImageView iconView = new ImageView(iconImage);
		iconView.setFitHeight(30);
		iconView.setFitWidth(30);
		
		return iconView;
	}
	
	private ImageView getBigIcon(String path)
	{
		Image iconImage = new Image(getClass().getResourceAsStream(path));
		ImageView iconView = new ImageView(iconImage);
		iconView.setFitHeight(55);
		iconView.setFitWidth(55);
		
		return iconView;
	}
	
	private void MailSent(String name,String mail,String lang)
	{
		String welcome = "Welcome to Langbase!";
		String content = "Hi "+ name+",\n\n"
		                      + "Welcome to Langbase!\n"
				              + "We're thrilled to have you join our community.\n\n"
		                      + "Your preferred language: "+ lang +"\n\n" 
		                      + "Start exploring and enjoy your learning journey!\n\n"
		                      + "Cheers, \nLangbase Team";
		EmailSender.sendEmail(mail, welcome, content);
		                      
	}
	
	private AnchorPane eyeSetting(PasswordField passwordField,TextField textField)
	{
		textField.textProperty().bindBidirectional(passwordField.textProperty());
		
		Image eyeImage = new Image("/images/eye.png");
		ImageView eyeView = new ImageView(eyeImage);
		eyeView.setFitHeight(28);
		eyeView.setFitWidth(28);
		
		Button eyeButton = new Button();
		eyeButton.setGraphic(eyeView);
		eyeButton.setStyle("-fx-background-color: transparent;");
		eyeButton.setFocusTraversable(false);
		
		eyeButton.setOnAction(e->{
			if(passwordField.isVisible())
			{
				passwordField.setVisible(false);
				passwordField.setManaged(false);
				textField.setVisible(true);
				textField.setManaged(true);
			}
			else
			{
				passwordField.setVisible(true);
				passwordField.setManaged(true);
				textField.setVisible(false);
				textField.setManaged(false);
			}
		});
		
		AnchorPane pane = new AnchorPane();
		pane.setPrefWidth(250);
		pane.setPrefHeight(38);
		
		// positioning
		AnchorPane.setLeftAnchor(passwordField,0.0);
		AnchorPane.setRightAnchor(passwordField,0.0);
		AnchorPane.setLeftAnchor(textField,0.0);
		AnchorPane.setRightAnchor(textField,0.0);
		
		AnchorPane.setRightAnchor(eyeButton,10.0);
		AnchorPane.setTopAnchor(eyeButton,2.0);
		pane.getChildren().addAll(passwordField,textField,eyeButton);
		
		return pane;
	}
	
	private int getId(String userEmail)
	{
		int userId = -1 ;
		try(Connection con = DBConnection.getConnection())
		{
			String query =  "SELECT userId FROM userInformation WHERE Email = ?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,userEmail);
			
			ResultSet rst = pst.executeQuery();
			if(rst.next())
			{
				userId = rst.getInt("userId");
				return userId ;
			}
			
			rst.close();
			pst.close();
			con.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userId ;
	}
	
	private void userDetails(int userId,String name,String email,String sourceLanguage,String destinationLanguage,String text,String translatedText)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "insert into translation_history (user_id,email,source_language,destination_language,source_text,translated_text) values (?,?,?,?,?,?)";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,userId);
			pst.setString(2,email);
			pst.setString(3,sourceLanguage);
			pst.setString(4,destinationLanguage);
			pst.setString(5,  text);
			pst.setString(6, translatedText);
			
			int rowsAffected = pst.executeUpdate();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void HistoryTranslationDetails(Stage primaryStage, String userEmail) {
	    List<TranslationHistoryModel> historyList = ViewTranslationDetails(userEmail);

	    TableView<TranslationHistoryModel> tableView = new TableView<>();

	    TableColumn<TranslationHistoryModel, Integer> colId = new TableColumn<>("User ID");
	    colId.setCellValueFactory(new PropertyValueFactory<>("userId"));

	    TableColumn<TranslationHistoryModel, String> colEmail = new TableColumn<>("Email");
	    colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

	    TableColumn<TranslationHistoryModel, String> colSourceLang = new TableColumn<>("Source Language");
	    colSourceLang.setCellValueFactory(new PropertyValueFactory<>("sourceLang"));

	    TableColumn<TranslationHistoryModel, String> colDestLang = new TableColumn<>("Destination Language");
	    colDestLang.setCellValueFactory(new PropertyValueFactory<>("destLang"));

	    TableColumn<TranslationHistoryModel, String> colSourceText = new TableColumn<>("Source Text");
	    colSourceText.setCellValueFactory(new PropertyValueFactory<>("sourceText"));

	    TableColumn<TranslationHistoryModel, String> colTranslatedText = new TableColumn<>("Translated Text");
	    colTranslatedText.setCellValueFactory(new PropertyValueFactory<>("translatedText"));

	    TableColumn<TranslationHistoryModel, String> colTranslationDate = new TableColumn<>("Date");
	    colTranslationDate.setCellValueFactory(new PropertyValueFactory<>("translationDate"));

	    tableView.getColumns().addAll(colId, colEmail, colSourceLang, colDestLang, colSourceText, colTranslatedText, colTranslationDate);

	    tableView.getItems().addAll(historyList);

	    VBox vbox = new VBox(tableView);
	    Scene scene = new Scene(vbox, 1100, 400);
	    Stage historyStage = new Stage();
	    historyStage.setTitle("Translation History");
	    historyStage.setScene(scene);
	    historyStage.show();
	}

	
	private List<TranslationHistoryModel> ViewTranslationDetails(String userEmail) {
	    List<TranslationHistoryModel> details = new ArrayList<>();
	    try (Connection con = DBConnection.getConnection()) {
	        int user_id = getId(userEmail);
	        String query = "select source_language, destination_language, source_text, translated_text, translation_date from translation_history where user_id=?";
	        PreparedStatement pst = con.prepareStatement(query);
	        pst.setInt(1, user_id);

	        ResultSet rst = pst.executeQuery();

	        while (rst.next()) {
	            TranslationHistoryModel record = new TranslationHistoryModel(
	                    user_id,
	                    userEmail,
	                    rst.getString("source_language"),
	                    rst.getString("destination_language"),
	                    rst.getString("source_text"),
	                    rst.getString("translated_text"),
	                    rst.getString("translation_date")
	            );
	            details.add(record);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return details;
	}

	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
