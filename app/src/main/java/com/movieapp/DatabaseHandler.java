package com.movieapp;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;


@Table(name = "favs")
public class DatabaseHandler extends  Model
{





    // This is the unique id given by the server
    @Column(name = "_id", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    public String id;



    @Column(name = "name")
    public String name ;

    @Column(name = "img")
    public String img ;


    @Column(name = "rate")
    public String rate ;
  @Column(name = "date")
    public String date ;
  @Column(name = "details")
    public String details ;
  @Column(name = "cover")
    public String cover ;




    // Make sure to have a default constructor for every ActiveAndroid model
    public DatabaseHandler(){
        super();
    }


    // Used to return items from another table based on the foreign key
    public List<DatabaseHandler> data() {
          return new Select()
                .from(DatabaseHandler.class)
                .execute();
    }

    public boolean check(String ID) {
       List list =   new Select()
                .from(DatabaseHandler.class)
                .where("_id = ?", ID)
                .execute();
            if (list.size() >0){
                return true;
            }else return false ;

    }

    public  void  deletefav(String ID ){
        new Delete().from(DatabaseHandler.class).where("_id = ?", ID).execute();
    }

}