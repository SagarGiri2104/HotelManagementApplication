package com.project.hotelmanagementproject.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import static com.project.hotelmanagementproject.utilities.ConstantUtils.APP_TAG;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_AVAILABILITY_STATUS;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CARD_TYPE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CHECKIN_DATE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CHECKOUT_DATE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CITY;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CREDIT_CARD_EXP;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_CREDIT_CARD_NUM;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_EMAIL;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_FIRST_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_FLOOR_NUM;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_GUEST_FIRST_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_GUEST_LAST_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_GUEST_USER_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_HOTEL_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_HOTEL_ROOM_ID;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_HOTEL_ROOM_TYPE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_LAST_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_NUM_OF_ADULTS_AND_CHILDREN;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_NUM_OF_NIGHTS;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_NUM_OF_ROOMS;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_OCCUPIED_STATUS;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_PASSWORD;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_PHONE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_PRICE_WEEKDAY;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_PRICE_WEEKEND;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_RESERV_HOTEL_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_RESERV_ID;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_ROOM_NUM;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_ROOM_TYPE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_START_TIME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_STATE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_STREET_ADDRESS;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_TAX;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_TOTAL_PRICE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_USER_NAME;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_USER_ROLE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.COL_ZIP_CODE;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.TABLE_HOTEL_DATA;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.TABLE_RESERV_DATA;
import static com.project.hotelmanagementproject.utilities.ConstantUtils.TABLE_USER_DATA;

public class DbMgr extends SQLiteOpenHelper {

    private static final String DB_NAME = "HotelManagementProject.db";
    private static DbMgr mInstance = null;
    Context context;

    /**
     * constructor should be private to prevent direct instantiation.
     * make call to static factory method "getInstance()" instead.
     */
    private DbMgr(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public static synchronized DbMgr getInstance(Context ctx) {
        /**
         * use the application context as suggested by CommonsWare.
         * this will ensure that you dont accidentally leak an Activitys
         * context (see this article for more information:
         * http://android-developers.blogspot.nl/2009/01/avoiding-memory-leaks.html)
         */
        if (mInstance == null) {
            mInstance = new DbMgr(ctx.getApplicationContext());
        }
        return mInstance;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTblQry = "create table " + TABLE_USER_DATA + "(" + COL_FIRST_NAME + " text, " +
                COL_LAST_NAME + " text, " + COL_USER_NAME + " text primary key," + COL_PASSWORD + " text,"
                + COL_USER_ROLE + " text," + COL_EMAIL + " text," + COL_PHONE + " text," + COL_STREET_ADDRESS + " text,"
                + COL_CITY + " text," + COL_STATE + " text," + COL_ZIP_CODE + " text," + COL_CREDIT_CARD_NUM + " text, "
                + COL_CREDIT_CARD_EXP + " text," + COL_CARD_TYPE + " text)";
        db.execSQL(userTblQry);

        String reservationTblQry = "create table " + TABLE_RESERV_DATA + "(" + COL_GUEST_FIRST_NAME + " text, " +
                COL_GUEST_USER_NAME + " text, " + COL_RESERV_ID + " text primary key," + COL_GUEST_LAST_NAME + " text,"
                + COL_RESERV_HOTEL_NAME + " text," + COL_ROOM_TYPE + " text," + COL_NUM_OF_ROOMS + " text,"
                + COL_NUM_OF_ADULTS_AND_CHILDREN + " text," + COL_NUM_OF_NIGHTS + " text," + COL_CHECKIN_DATE + " text,"
                + COL_CHECKOUT_DATE + " text, " + COL_START_TIME + " text," + COL_TOTAL_PRICE + " text)";
        db.execSQL(reservationTblQry);

        String hotelTableQry = "create table " + TABLE_HOTEL_DATA + "(" + COL_HOTEL_NAME + " text, " +
                COL_ROOM_NUM + " text, " + COL_HOTEL_ROOM_ID + " text primary key," + COL_PRICE_WEEKDAY + " text,"
                + COL_PRICE_WEEKEND + " text," + COL_HOTEL_ROOM_TYPE + " text," + COL_AVAILABILITY_STATUS + " text,"
                + COL_OCCUPIED_STATUS + " text," + COL_FLOOR_NUM + " text," + COL_TAX + " text)";
        db.execSQL(hotelTableQry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERV_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOTEL_DATA);
        onCreate(db);
    }

    public boolean addNewGuest(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_USER_NAME, user.getUserName());
        cv.put(COL_PASSWORD, user.getPassword());
        cv.put(COL_FIRST_NAME, user.getFirstName());
        cv.put(COL_LAST_NAME, user.getLastName());
        cv.put(COL_EMAIL, user.getEmail());
        cv.put(COL_PHONE, user.getPhone());
        cv.put(COL_STREET_ADDRESS, user.getStreetAddress());
        cv.put(COL_CITY, user.getCity());
        cv.put(COL_STATE, user.getState());
        cv.put(COL_ZIP_CODE, user.getZipCode());
        cv.put(COL_CREDIT_CARD_NUM, user.getCreditCardNum());
        cv.put(COL_CREDIT_CARD_EXP, user.getCreditCardExp());
        cv.put(COL_CARD_TYPE, user.getCreditCardtype());
        cv.put(COL_USER_ROLE, "Guest");

        long res = db.insert(TABLE_USER_DATA, null, cv);
//        if(res== -1)
////            return false;
////        else
////            return true;
        return res != -1;
    }

    public User getUserDetails(String username) {
        String userName = null, password = null, role = null, lastName = null, firstName = null, phone = null, email = null;
        String streetAddress = null, city = null, state = null, zipcode = null, ccn = null, ccexp = null, cctype = null;

        SQLiteDatabase sqldb = this.getReadableDatabase();
        String queryForCheckingPassword = "Select * from " + TABLE_USER_DATA + " where " + COL_USER_NAME + " = '" + username + "'";
        Cursor c = sqldb.rawQuery(queryForCheckingPassword, null);

        while (c.moveToNext()) {
            role = c.getString(c.getColumnIndex(COL_USER_ROLE));
            password = c.getString(c.getColumnIndex(COL_PASSWORD));
            userName = c.getString(c.getColumnIndex(COL_USER_NAME));
            email = c.getString(c.getColumnIndex(COL_EMAIL));
            phone = c.getString(c.getColumnIndex(COL_PHONE));
            lastName = c.getString(c.getColumnIndex(COL_LAST_NAME));
            firstName = c.getString(c.getColumnIndex(COL_FIRST_NAME));
            streetAddress = c.getString(c.getColumnIndex(COL_STREET_ADDRESS));
            city = c.getString(c.getColumnIndex(COL_CITY));
            state = c.getString(c.getColumnIndex(COL_STATE));
            zipcode = c.getString(c.getColumnIndex(COL_ZIP_CODE));
            if (role.equalsIgnoreCase("Guest")) {
                ccn = c.getString(c.getColumnIndex(COL_CREDIT_CARD_NUM));
                ccexp = c.getString(c.getColumnIndex(COL_CREDIT_CARD_EXP));
                cctype = c.getString(c.getColumnIndex(COL_CARD_TYPE));
            }
        }
        c.close();
        return new User(userName, firstName, lastName, password, role, email, phone, streetAddress, city, state, zipcode, ccn, ccexp, cctype);
    }

    public boolean updateProfile(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_FIRST_NAME, user.getFirstName());
        cv.put(COL_PASSWORD, user.getPassword());
        cv.put(COL_LAST_NAME, user.getLastName());
        cv.put(COL_EMAIL, user.getEmail());
        cv.put(COL_PHONE, user.getPhone());
        cv.put(COL_STREET_ADDRESS, user.getStreetAddress());
        cv.put(COL_CITY, user.getCity());
        cv.put(COL_STATE, user.getState());
        cv.put(COL_ZIP_CODE, user.getZipCode());
        cv.put(COL_CREDIT_CARD_NUM, user.getCreditCardNum());
        cv.put(COL_CREDIT_CARD_EXP, user.getCreditCardExp());
        cv.put(COL_CARD_TYPE, user.getCreditCardtype());
        int update = db.update(TABLE_USER_DATA, cv, COL_USER_NAME + " = '" + user.getUserName() + "'", null);
        return update == 1;
    }

    public boolean checkPassword(String username, String password) {
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String queryForCheckingPassword = "Select * from " + TABLE_USER_DATA + " where " + COL_USER_NAME + " = '" + username + "' and " + COL_PASSWORD + " = '" + password + "'";
        Cursor c = sqldb.rawQuery(queryForCheckingPassword, null);
        return c.getCount() > 0;
    }

    public String userRole(String username) {
        String role = null;
        SQLiteDatabase sqldb = this.getReadableDatabase();
        String queryForCheckingPassword = "Select * from " + TABLE_USER_DATA + " where " + COL_USER_NAME + " = '" + username + "'";
        Cursor c = sqldb.rawQuery(queryForCheckingPassword, null);
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                role = c.getString(c.getColumnIndex(COL_USER_ROLE));
                c.moveToNext();
            }
        } else {
            Log.e(APP_TAG, "db not reached");
        }
        c.close();
        return role;
    }

    public boolean changePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_PASSWORD, newPassword);
        int update = db.update(TABLE_USER_DATA, cv, COL_USER_NAME + " = '" + username + "'", null);
        Log.i(APP_TAG, String.valueOf(update));
        return update == 1;
    }

    public boolean addNewReserv(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_RESERV_ID, reservation.getReservationId());
        cv.put(COL_GUEST_USER_NAME, reservation.getResvUserName());
        cv.put(COL_GUEST_FIRST_NAME, reservation.getResvFirstName());
        cv.put(COL_GUEST_LAST_NAME, reservation.getResvLastName());
        cv.put(COL_RESERV_HOTEL_NAME, reservation.getResvHotelName());
        cv.put(COL_ROOM_TYPE, reservation.getResvRoomType());
        cv.put(COL_NUM_OF_ROOMS, reservation.getResvNumRooms());
        cv.put(COL_NUM_OF_ADULTS_AND_CHILDREN, reservation.getResvNumAdultsChildren());
        cv.put(COL_NUM_OF_NIGHTS, reservation.getResvNumNights());
        cv.put(COL_CHECKIN_DATE, reservation.getResvCheckInDate());
        cv.put(COL_CHECKOUT_DATE, reservation.getResevCheckOutDate());
        cv.put(COL_START_TIME, reservation.getResvStartTime());
        cv.put(COL_TOTAL_PRICE, reservation.getTotalPrice());
        long res = db.insert(TABLE_RESERV_DATA, null, cv);

        return res != -1;
    }

    public ArrayList<HotelRoom> getSearchRoomList(String roomNum, String startDate, String hotelNameIp) {
        ArrayList<HotelRoom> roomList = new ArrayList<>();

        SQLiteDatabase sqldb = this.getReadableDatabase();
        String searchRoom1 = "Select * from " + TABLE_HOTEL_DATA + " where " + COL_ROOM_NUM + " like '%" + roomNum + "%'";
        String searchRoom2 = "Select * from " + TABLE_HOTEL_DATA + " where " + COL_HOTEL_NAME + " in ('" + hotelNameIp + "' ,'RANGER','WILLIAMS','SHARD','LIBERTY') and " + COL_ROOM_NUM + " like '%" + roomNum + "%'";
        String searchRoom = "Select * from " + TABLE_HOTEL_DATA + " where " + COL_HOTEL_NAME + " = '" + hotelNameIp + "' and " + COL_ROOM_NUM + " like '%" + roomNum + "%'";
        try {
            Cursor c = sqldb.rawQuery(searchRoom, null);
            if (c.getCount() == 0) {
                Log.e(APP_TAG, "search room Query Err: No data");
            } else {
                while (c.moveToNext()) {
                    String roomId = c.getString(c.getColumnIndex(COL_HOTEL_ROOM_ID));
                    String roomNumber = c.getString(c.getColumnIndex(COL_ROOM_NUM));
                    String roomType = c.getString(c.getColumnIndex(COL_ROOM_TYPE));
                    String hotelName = c.getString(c.getColumnIndex(COL_HOTEL_NAME));
                    String floorNum = c.getString(c.getColumnIndex(COL_FLOOR_NUM));
                    String price = c.getString(c.getColumnIndex(COL_PRICE_WEEKDAY));

                    HotelRoom hotelRoom = new HotelRoom(roomId, hotelName, roomNumber, roomType, floorNum, price,
                            null, null, null
                            , null, startDate, null, null);
                    roomList.add(hotelRoom);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return roomList;
    }

}