package core.utilities.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Cards {

    public String accEntity;
    public List<Object> debitCardId = new ArrayList<>();
    public List<Object> debitCardIdQaOnly = new ArrayList<>();
    public HashMap<Object,Object> debitCardMaskNum = new HashMap<>();
    public HashMap<Object,Object> debitCardName= new HashMap<>();
    public HashMap<Object,Object> debitCardStatus = new HashMap<>();
    public HashMap<Object,Object> dChannelSms = new HashMap<>();
    public HashMap<Object,Object> dChannelEmail = new HashMap<>();



    public int getRandomAccount(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) - min;
    }







}
