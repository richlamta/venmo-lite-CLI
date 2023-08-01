package com.techelevator.tenmo.model;

import com.techelevator.tenmo.App;

public class AuthenticatedUser {
	
	private String token;
	private User user;


	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public void processChoiceApproveDeny(int selectedChoice, double amountToSend, User recipientUser,Transfer selectedTransfer, App app){
		switch(selectedChoice){
			case 1:
				if(user.getAccount().hasSufficientFunds(amountToSend)){
					app.sendBucks(amountToSend,recipientUser,true);
					//change the transfer type from request to send when it gets approved
					selectedTransfer.setTransferType(1);
					selectedTransfer.setTransferStatus(2);
					selectedTransfer.setAccountFrom(user.getAccount().getAccount_id());
					selectedTransfer.setAccountTo(recipientUser.getId() + 1000);
					boolean status = app.getTransferService().updateTransfer(selectedTransfer);
					if (status) {
						System.out.println("Transfer approved.");
					}else {
						System.out.println("Transfer unsuccessful");
					}
				}else{
					System.out.println("You have insufficient funds.");
					selectedTransfer.setTransferStatus(3);
					app.getTransferService().updateTransfer(selectedTransfer);
				}
				break;
			case 2:
				selectedTransfer.setTransferStatus(3);
				boolean status = app.getTransferService().updateTransfer(selectedTransfer);
				if (status) {
					System.out.println("Transfer rejected");
				} else {
					System.out.println("Something went wrong. Contact your bank.");
				}
		}
	}
}
