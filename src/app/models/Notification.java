package app.models;

public class Notification {
	
		private int id;
	    private String senderId;
	    private String receiverId;
	    private String title;
	    private String content;
	    private String createdAt; 
	    private String type;
	    private boolean read;
	    private boolean validNotification;
	    private boolean important;
        
	    public Notification() {
	    	
	    }
	    
	    public Notification(int id, String senderId, String receiverId, String title, String content,
	                        String createdAt, String type, boolean read, boolean validNotification) {
	        this.id = id;
	        this.senderId = senderId;
	        this.receiverId = receiverId;
	        this.title = title;
	        this.content = content;
	        this.createdAt = createdAt;
	        this.type = type;
	        this.read = read;
	        this.validNotification = validNotification;
	    }
	    
	    public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getSenderId() {
			return senderId;
		}

		public void setSenderId(String senderId) {
			this.senderId = senderId;
		}

		public String getReceiverId() {
			return receiverId;
		}

		public void setReceiverId(String receiverId) {
			this.receiverId = receiverId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getCreatedAt() {
			return createdAt;
		}

		public void setCreatedAt(String createdAt) {
			this.createdAt = createdAt;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isRead() {
			return read;
		}

		public void setRead(boolean read) {
			this.read = read;
		}

		public boolean isValidNotification() {
			return validNotification;
		}

		public void setValidNotification(boolean validNotification) {
			this.validNotification = validNotification;
		}
		
		public void setImportant(boolean important) {
			this.important = important;
		}
		
		public boolean isImportant() {
			return important;
		}
}

