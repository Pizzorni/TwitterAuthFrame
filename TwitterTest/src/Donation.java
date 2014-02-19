



public class Donation {


	private String donor, message, email, phone, quantity;

	
	public Donation(String donor, String quantity, String message, String email, String phone){
		this.quantity = quantity;
		this.donor = donor;
		this.message = message;
		this.email = email;
		this.phone = phone;
		if(donor == null) this.donor = "Anonymous";
		if(message ==null) this.message = "";
	}
	

	
	
	public String toString(){
		String print = "$" + this.quantity + " from " + this.donor + ". " + this.message + " email is " + this.email + " phone # is " + this.phone;
		return print;
	}
	
}
