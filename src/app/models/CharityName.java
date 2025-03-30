package app.models;

public class CharityName {
	 private int id;
     private String name;

     public CharityName() {
     }

     public CharityName(int id, String name) {
         this.id = id;
         this.name = name;
     }

     public int getId() {
         return id;
     }

     public String getName() {
         return name;
     }
 }

